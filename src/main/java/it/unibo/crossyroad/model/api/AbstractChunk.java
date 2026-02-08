package it.unibo.crossyroad.model.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Objects;

import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.model.impl.CoinMultiplier;
import it.unibo.crossyroad.model.impl.Invincibility;
import it.unibo.crossyroad.model.impl.SlowCars;

/**
 * Represents a Chunk.
 * 
 * @see Chunk
 */
public abstract class AbstractChunk extends AbstractPositionable implements Chunk {

    private static final double FIRST_PROBABILITY = 0.70;
    private static final double SECOND_PROBABILITY = 0.80;
    private static final double THIRD_PROBABILITY = 0.90;
    private static final Random RND = new Random();
    private final List<Obstacle> obstacles;
    private final List<Pickable> pickables;

    /**
     * Initializes the Chunk.
     * 
     * @param initialPosition the Chunk's initial position.
     * 
     * @param dimension the Chunk's dimension.
     */
    public AbstractChunk(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
        this.obstacles = new LinkedList<>();
        this.pickables = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.clearObstacles();
        this.clearPickables();
        this.generateObstacles();
        this.generatePickables();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Obstacle> getObstacles() { 
        return List.copyOf(this.obstacles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pickable> getPickables() {
        return List.copyOf(this.pickables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Positionable> getPositionables() {
        final List<Positionable> positionables = new LinkedList<>();
        positionables.addAll(this.getObstacles());
        positionables.addAll(this.getPickables());
        return List.copyOf(positionables);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PowerUp> getActivePowerUp() {
        return List.copyOf(this.pickables.stream()
                                         .filter(p -> p instanceof PowerUp)
                                         .map(p -> (PowerUp) p)
                                         .filter(p -> !p.isDone() && p.isPickedUp())
                                         .toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePickable(final Pickable pick) {
        Objects.requireNonNull(pick, "Pickable cannot be null");
        this.pickables.remove(pick);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameParameters params, final long deltaTime) {
        Objects.requireNonNull(params, "Game parameters cannot be null");
        this.pickables.removeIf(p -> p instanceof AbstractPowerUp powerUp && powerUp.isDone());
        this.pickables.stream()
                      .filter(p -> p instanceof AbstractPowerUp powerUp && powerUp.isPickedUp())
                      .map(p -> (AbstractPowerUp) p)
                      .forEach(p -> p.update(deltaTime, params));
    }

    /**
     * Generates random Obstacles on the Chunk.
     */
    protected abstract void generateObstacles();

    /**
     * Adds a new obstacle to the internal list.
     * 
     * @param obs the obstacle to add to the internal list.
     * 
     * @see Obstacle
     */
    protected void addObstacle(final Obstacle obs) {
        Objects.requireNonNull(obs, "Obstacle cannot be null");
        this.obstacles.add(obs);
    }

    /**
     * Removes an obstacle from the internal list.
     * 
     * @param obs the obstacle to remove from the internal list.
     * 
     * @see Obstacle
     */
    protected void removeObstacle(final Obstacle obs) {
        Objects.requireNonNull(obs, "Obstacle cannot be null");
        this.obstacles.remove(obs);
    }

    /**
     * Deletes all the obstacles present on the Chunk.
     */
    protected final void clearObstacles() {
        this.obstacles.clear();
    }

    /**
     * Deletes all the pickables present on the Chunk.
     */
    protected final void clearPickables() {
        this.pickables.clear();
    }

    /**
     * Generates random Pickables objects on the Chunk, each one with a different probability.
     */
    private void generatePickables() {
        for (int i = 0; i < RND.nextInt(3); i++) {
            final int relativeX = RND.nextInt((int) this.getDimension().width());
            final int relativeY = RND.nextInt((int) this.getDimension().height());
            final Position randomPosition = new Position(this.getPosition().x() + relativeX, this.getPosition().y() + relativeY);
            if (!this.getPositionables().stream().anyMatch(p -> p.getPosition().equals(randomPosition))) {
                final double number = RND.nextDouble();
                if (number <= FIRST_PROBABILITY) {
                    this.addPickable(new Coin(randomPosition));
                } else if (this.getActivePowerUp().isEmpty() && number > FIRST_PROBABILITY && number <= SECOND_PROBABILITY) {
                    this.addPickable(new Invincibility(randomPosition));
                } else if (this.getActivePowerUp().isEmpty() && number > SECOND_PROBABILITY && number <= THIRD_PROBABILITY) {
                    this.addPickable(new SlowCars(randomPosition));
                } else if (this.getActivePowerUp().isEmpty() && number > THIRD_PROBABILITY && number <= 1) {
                    this.addPickable(new CoinMultiplier(randomPosition));
                }
            }
        }
    }

    /**
     * Adds a new pickable to the internal list.
     * 
     * @param pick the pickable to add to the internal list.
     * 
     * @see Pickable
     */
    private void addPickable(final Pickable pick) {
        Objects.requireNonNull(pick, "Pickable cannot be null");
        this.pickables.add(pick);
    }
}
