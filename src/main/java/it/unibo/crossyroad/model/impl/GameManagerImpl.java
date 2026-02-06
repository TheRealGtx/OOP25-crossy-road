package it.unibo.crossyroad.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.crossyroad.model.api.ActiveChunk;
import it.unibo.crossyroad.model.api.ActiveObstacle;
import it.unibo.crossyroad.model.api.Chunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Pickable;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.model.api.PowerUp;

/**
 * Implementation of the GameManager interface.
 * 
 * @see GameManager
 */
public class GameManagerImpl implements GameManager {

    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 9;
    private static final Position PLAYER_START_POSITION = new Position(5, 8);
    private static final Dimension CHUNK_DIMENSION = new Dimension(MAP_WIDTH, MAP_HEIGHT / 3); 
    private static final Position CHUNK_START_POSITION = new Position(0, -3);
    private static final Position CHUNK_FIRST_POSITION = new Position(0, 0);
    private static final Position CHUNK_SECOND_POSITION = new Position(0, 3);
    private static final Position CHUNK_THIRD_POSITION = new Position(0, 6);
    private static final Random RANDOM = new Random();
    private static final int Y_MOVE_MAP_MARK = Math.ceilDiv(MAP_HEIGHT, 2); 
    private static final int Y_DISPOSE_CHUNK_MARK = MAP_WIDTH + 2;
    private static final int Y_MAP_MOVEMENT = 1;
    private static final int Y_CREATE_CHUNK_MARK = (int) CHUNK_DIMENSION.height() - Y_MAP_MOVEMENT;
    private PositionablePlayer player;
    private final GameParameters gameParameters;
    private List<Chunk> chunks;

    /**
     * Initializes the GameManager with the GameParameters.
     * 
     * @param g the GameParameters to use in the game.
     */
    public GameManagerImpl(final GameParameters g) {
        this.gameParameters = new GameParametersImpl(g);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Positionable> getPositionables() {
        final List<Positionable> positionables = new LinkedList<>();
        positionables.add(this.player);
        positionables.addAll(this.chunks);
        positionables.addAll(this.getObstaclesOnMap());
        positionables.addAll(this.getPickablesOnMap());

        return List.copyOf(positionables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<EntityType, Long> getActivePowerUps() {
        return this.chunks.stream()
                          .flatMap(c -> c.getActivePowerUp().stream())
                          .collect(Collectors.toMap(Pickable::getEntityType, PowerUp::getRemaining));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        this.chunks.stream()
                   .filter(c -> c instanceof ActiveChunk)
                   .map(c -> (ActiveChunk) c)
                   .forEach(ac -> ac.update(this.gameParameters, deltaTime));

        this.checkCoinsCollision();
        this.checkPowerUpCollisions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePlayer(final Direction d) {
        if (this.canPlayerMove(d)) {
            this.player.move(d, 1);

            if (this.player.getPosition().y() >= Y_MOVE_MAP_MARK) {
                this.moveMap();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.checkDeadlyCollisions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void reset() {
        this.player = new PositionablePlayer(PLAYER_START_POSITION);
        this.chunks = new LinkedList<>();

        //Adds the first chunks to start the game
        this.chunks.add(new Grass(CHUNK_START_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_FIRST_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_SECOND_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_THIRD_POSITION, CHUNK_DIMENSION));

        this.chunks.forEach(c -> c.getObstacles().stream().filter(o -> o.getPosition().equals(PLAYER_START_POSITION)));
    }

    /**
     * Generates a new Chunk.
     */
    private void generateChunk() {
        switch (RANDOM.nextInt(3)) {
            case 0:
                this.chunks.add(new Grass(CHUNK_START_POSITION, CHUNK_DIMENSION));
                break;
            case 1:
                this.chunks.add(new Road(CHUNK_START_POSITION, CHUNK_DIMENSION));
                break;
            case 2:
                this.chunks.add(new Railway(CHUNK_START_POSITION, CHUNK_DIMENSION));
                break;
            default:
                break;
        }
    }

    /**
     * Wheter the player can move in the given direction.
     * 
     * @param d the direction the player wants to move upon.
     * 
     * @return true if the player can move, false otherwise.
     */
    private boolean canPlayerMove(final Direction d) {
        //Checks Passive obstacles collisions
        for (final Obstacle obs : this.getObstaclesOnMap()) {
            if ((obs instanceof Rock || obs instanceof Tree) && d.apply(this.player.getPosition()).equals(obs.getPosition())) {
                return false;
            }
        }

        //Checks map border collisions
        return !(d.apply(this.player.getPosition()).y() > MAP_WIDTH || d.apply(this.player.getPosition()).x() > MAP_HEIGHT);
    }

    /**
     * Checks if the player is colliding with an active obstacle.
     * 
     * @return true if there's a collision, false otherwise.
     */
    private boolean checkDeadlyCollisions() {
        for (final Obstacle obs : this.getObstaclesOnMap()) {
            if (obs instanceof ActiveObstacle && obs.getPosition().equals(this.player.getPosition())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the player is colliding with a Coin, if so pickup the coin.
     */
    private void checkCoinsCollision() {
        for (final Pickable pick : this.getPickablesOnMap()) {
            if (pick instanceof Coin && pick.getPosition().equals(this.player.getPosition())) {
                ((Coin) pick).applyEffect(this.gameParameters);
            }
        }
    }

    /**
     * Picks up the PowerUps the player is colliding with. 
     */
    private void checkPowerUpCollisions() {
        for (final Pickable pick : this.getPickablesOnMap()) {
            if (pick instanceof PowerUp && pick.getPosition().equals(this.player.getPosition())) {
                pick.pickUp(this.gameParameters);
            }
        }
    }

    /**
     * Gets the Obstacles currently present on the map.
     * 
     * @return a List of the Obstacles currently present on the map.
     */
    private List<Obstacle> getObstaclesOnMap() {
        final List<Obstacle> obstacles = new LinkedList<>();
        this.chunks.forEach(c -> obstacles.addAll(c.getObstacles()));
        return obstacles;
    }

    /**
     * Gets the Pickables currently present on the map.
     * 
     * @return a List of the Pickables currently present on the map.
     */
    private List<Pickable> getPickablesOnMap() {
        final List<Pickable> pickables = new LinkedList<>();
        this.chunks.forEach(c -> pickables.addAll(c.getPickables()));
        return pickables;
    }

    /**
     * Handles the map movement.
     */
    private void moveMap() {
        //Chunk movement
        this.chunks.forEach(c -> c.increaseY(Y_MAP_MOVEMENT));
        this.chunks.removeIf(c -> c.getPosition().y() >= Y_DISPOSE_CHUNK_MARK);

        //Elements movement
        for (final Chunk c : this.chunks) {
            for (final Positionable p : c.getPositionables()) {
                p.increaseY(Y_MAP_MOVEMENT);
            }
        }

        //Generate new Chunk if necessary
        if (this.chunks.stream().anyMatch(c -> c.getPosition().y() == Y_CREATE_CHUNK_MARK)) {
            this.generateChunk();
        }
    }
}
