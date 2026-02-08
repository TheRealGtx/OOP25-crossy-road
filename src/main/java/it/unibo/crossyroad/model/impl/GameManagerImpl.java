package it.unibo.crossyroad.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Range;

import it.unibo.crossyroad.model.api.AbstractChunk;
import it.unibo.crossyroad.model.api.Chunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Pair;
import it.unibo.crossyroad.model.api.Pickable;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.model.api.PowerUp;
import it.unibo.crossyroad.model.api.CollisionType;

/**
 * Implementation of the GameManager interface.
 * 
 * @see GameManager
 */
public final class GameManagerImpl implements GameManager {

    private static final double MAP_WIDTH = 10;
    private static final double MAP_HEIGHT = 9;
    private static final double Y_MOVE_MAP_MARK = 4; 
    private static final double Y_DISPOSE_CHUNK_MARK = MAP_WIDTH + 2;
    private static final double Y_MAP_MOVEMENT = 1;
    private static final double Y_CREATE_CHUNK_MARK = 0;
    private static final double FIRST_PROBABILITY = 0.3;
    private static final double SECOND_PROBABILITY = 0.6;
    private static final double THIRD_PROBABILITY = 0.8;
    private static final Dimension CHUNK_DIMENSION = new Dimension(MAP_WIDTH, MAP_HEIGHT / 3); 
    private static final Position PLAYER_START_POSITION = new Position(5, 8);
    private static final Position CHUNK_START_POSITION = new Position(0, -3);
    private static final Position CHUNK_FIRST_POSITION = new Position(0, 0);
    private static final Position CHUNK_SECOND_POSITION = new Position(0, 3);
    private static final Position CHUNK_THIRD_POSITION = new Position(0, 6);
    private static final Random RANDOM = new Random();
    private final GameParameters gameParameters;
    private List<Chunk> chunks;
    private Pair<EntityType, Integer> lastGenerated;
    private Optional<Obstacle> currentTransport = Optional.empty();
    private PositionablePlayer player;
    private boolean isGameOver;
    private boolean wasOnTransport;

    /**
     * Initializes the GameManager with the GameParameters.
     * 
     * @param g the GameParameters to use in the game.
     */
    public GameManagerImpl(final GameParameters g) {
        Objects.requireNonNull(g, "Game parameters cannot be null");
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
                          .collect(Collectors.toMap(Pickable::getEntityType, PowerUp::getRemaining, Math::min));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        final Optional<Obstacle> transport = this.getTransportCarryingPlayer();
        final Optional<Position> positionBefore = transport.map(Obstacle::getPosition);

        this.chunks.stream()
                   .filter(c -> c instanceof AbstractChunk)
                   .map(c -> (AbstractChunk) c)
                   .forEach(ac -> ac.update(this.gameParameters, deltaTime));

        if (transport.isPresent() && positionBefore.isPresent()) {
            movePlayerWithTransport(positionBefore.get(), transport.get().getPosition());
        }

        this.currentTransport = transport;
        this.handlePlayerOffTransport();
        this.wasOnTransport = transport.isPresent();

        this.checkCoinsCollision();
        this.checkPowerUpCollisions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePlayer(final Direction d) {
        if (this.canPlayerMove(d)) {
            if (d == Direction.UP && this.player.getPosition().y() <= Y_MOVE_MAP_MARK) {
                this.moveMap();
            } else {
                this.player.move(d, 1);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        if (!this.isGameOver) {
            this.isGameOver = this.checkDeadlyCollisions();
        }
        return this.isGameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.player = new PositionablePlayer(PLAYER_START_POSITION);
        this.chunks = new LinkedList<>();
        this.isGameOver = false;

        //Adds the first chunks to start the game
        this.chunks.add(new Grass(CHUNK_START_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_FIRST_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_SECOND_POSITION, CHUNK_DIMENSION));
        this.chunks.add(new Grass(CHUNK_THIRD_POSITION, CHUNK_DIMENSION));
        this.lastGenerated = new Pair<>(EntityType.GRASS, 4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        this.isGameOver = true;
    }

    /**
     * Handles the player getting off a transport Obstacle, aligning him horizontally to the nearest integer position.
     */
    private void handlePlayerOffTransport() {
        if (this.wasOnTransport && this.currentTransport.isEmpty()) {
            this.alignHorizontallyPlayer();
        }
    }

    /**
     * Moves the player of the deltaX between the old and new position of the transport obstacle.
     *
     * @param oldPosition old position of the transport obstacle
     * @param newPosition new position of the transport obstacle
     */
    private void movePlayerWithTransport(final Position oldPosition, final Position newPosition) {
        final double deltaX = newPosition.x() - oldPosition.x();

        if (Math.abs(deltaX) > 0) {
            this.player.move(deltaX > 0 ? Direction.RIGHT : Direction.LEFT, Math.abs(deltaX));
        }
    }

    /**
     * Gets the transport Active Obstacle the player is on.
     *
     * @return the transport Active Obstacle the player is currently on, or null if there is none.
     */
    private Optional<Obstacle> getTransportCarryingPlayer() {
        return this.getObstaclesOnMap().stream()
            .filter(o -> o.getCollisionType() == CollisionType.TRANSPORT)
            .filter(o -> o.contains(
                Position.of(player.getDimension().width() / 2, player.getDimension().height() / 2)
                    .relative(player.getPosition())
            ))
            .findFirst();
    }

    /**
     * Aligns the player horizontally after he gets off a transport Obstacle (to the nearest integer position).
     */
    private void alignHorizontallyPlayer() {
        if (this.player.getPosition().x() % 1 != 0) {
            final double nearestX = Math.round(this.player.getPosition().x());
            this.player.move(
                this.player.getPosition().x() < nearestX ? Direction.RIGHT : Direction.LEFT,
                Math.abs(nearestX - this.player.getPosition().x())
            );
        }
    }

    /**
     * Checks if the player is colliding with an active obstacle or the map border.
     * 
     * @return true if there's a collision, false otherwise.
     */
    private boolean checkDeadlyCollisions() {
        //Check if player is outside the map (deadly)
        final Range<Double> xRange = Range.closed(0.0, MAP_WIDTH);
        if (!xRange.contains(this.player.getPosition().x())) {
            return true;
        }

        boolean deadlyCollision = false;
        boolean transportCollision = false;

        //Check obstacles collisions (deadly if player is not on a transport)
        if (!this.gameParameters.isInvincible()) {
            for (final Obstacle obs : this.getObstaclesOnMap().stream().filter(o -> o.overlaps(this.player)).toList()) {
                if (obs.getCollisionType() == CollisionType.DEADLY) {
                    deadlyCollision = true;
                } else if (obs.getCollisionType() == CollisionType.TRANSPORT) {
                    transportCollision = true;
                }
            }
        }

        return deadlyCollision && !transportCollision;
    }

    /**
     * Generates a new Chunk.
     */
    private void generateChunk() {
        //Place a Grass chunk after every railway, every river and every 2 roads
        if (this.lastGenerated.e1() == EntityType.RAILWAY || this.lastGenerated.e1() == EntityType.RIVER
            || this.lastGenerated.e1() == EntityType.ROAD && this.lastGenerated.e2() >= 2) {
            this.chunks.add(new Grass(CHUNK_START_POSITION, CHUNK_DIMENSION));
            this.lastGenerated = new Pair<>(EntityType.GRASS, 1);
        } else { //Generate a random chunk, each one with different probability
            final double number = RANDOM.nextDouble();

            if (number <= FIRST_PROBABILITY) {
                this.chunks.add(new Grass(CHUNK_START_POSITION, CHUNK_DIMENSION));
                this.updateLastGenerated(EntityType.GRASS);
            } else if (number <= SECOND_PROBABILITY) {
                this.chunks.add(new Road(CHUNK_START_POSITION, CHUNK_DIMENSION));
                this.updateLastGenerated(EntityType.ROAD);

            } else if (number <= THIRD_PROBABILITY) {
                this.chunks.add(new Railway(CHUNK_START_POSITION, CHUNK_DIMENSION));
                this.updateLastGenerated(EntityType.RAILWAY);
            } else {
                final Direction riverDirection = RANDOM.nextBoolean() ? Direction.LEFT : Direction.RIGHT;
                this.chunks.add(new River(CHUNK_START_POSITION, CHUNK_DIMENSION, riverDirection));
            }
        }
    }

    /**
     * Updates the Pair that tracks the last generated type of Chunk.
     * 
     * @param type the last generated type of Chunk.
     * 
     * @see EntityType
     */
    private void updateLastGenerated(final EntityType type) {
        if (this.lastGenerated.e1() == type) {
            this.lastGenerated = new Pair<>(type, lastGenerated.e2() + 1);
        } else {
            this.lastGenerated = new Pair<>(type, 1);
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
        final Range<Double> xRange = Range.closed(0.0, MAP_WIDTH - 1);
        final Range<Double> yRange = Range.closed(0.0, MAP_HEIGHT - 1);
        return xRange.contains(d.apply(this.player.getPosition()).x()) && yRange.contains(d.apply(this.player.getPosition()).y());
    }

    /**
     * Checks if the player is colliding with a Coin, if so pickup the coin.
     */
    private void checkCoinsCollision() {
        this.getPickablesOnMap().stream()
                                .filter(p -> p instanceof Coin && p.overlaps(this.player))
                                .map(p -> (Coin) p)
                                .forEach(c -> {
                                    c.applyEffect(this.gameParameters);
                                    this.chunks.forEach(ch -> ch.removePickable(c));
                                });
    }

    /**
     * Picks up the PowerUps the player is colliding with. 
     */
    private void checkPowerUpCollisions() {
        this.getPickablesOnMap().stream()
                                .filter(p -> p instanceof PowerUp && p.overlaps(this.player)
                                        && !this.getActivePowerUps().containsKey(p.getEntityType()))
                                .forEach(p -> p.pickUp(this.gameParameters));
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
     * Handles the map movement and creates new Chunks if necessary.
     */
    private void moveMap() {
        //First move the Chunks
        this.chunks.forEach(c -> c.increaseY(Y_MAP_MOVEMENT));
        this.chunks.removeIf(c -> c.getPosition().y() >= Y_DISPOSE_CHUNK_MARK && c.getActivePowerUp().isEmpty());

        //Then move the other elements.
        this.chunks.forEach(c -> c.getPositionables().forEach(p -> p.increaseY(Y_MAP_MOVEMENT)));

        if (this.chunks.stream().anyMatch(c -> c.getPosition().y() == Y_CREATE_CHUNK_MARK)) {
            this.generateChunk();
        }
    }
}
