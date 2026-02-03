package it.unibo.crossyroad.model.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Objects;

import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.model.impl.Invincibility;
import it.unibo.crossyroad.model.impl.SlowCars;

/**
 * Represents a Chunk.
 */
public abstract class AbstractChunk extends AbstractPositionable implements Chunk {

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
                                         .filter(Pickable::isPickedUp)
                                         .toList());
    }

    /**
     * Generates random Obstacles objects on the Chunk.
     */
    protected abstract void generateObstacles();

    /**
     * Adds a new obstacle to the list.
     * 
     * @param obs the obstacle to add to the list.
     * 
     * @see Obstacle.
     */
    protected void addObstacle(final Obstacle obs) {
        Objects.requireNonNull(obs, "Obstacle cannot be null");
        this.obstacles.add(obs);
    }

    /**
     * Removes an obstacle from the list.
     * 
     * @param obs the obstacle to remove from the list.
     * 
     * @see Obstacle.
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
     * Generates random Pickables objects on the Chunk.
     */
    private void generatePickables() {
        final int xLimit = (int) Math.round(this.getPosition().x() + this.getDimension().height());
        final int yLlimit = (int) Math.round(this.getPosition().y() + this.getDimension().width());

        for (int i = 0; i < RND.nextInt(3); i++) {
            final Position randomPosition = new Position(RND.nextInt(xLimit), RND.nextInt(yLlimit));

            if (!this.getPositionables().stream().anyMatch(p -> p.getPosition().equals(randomPosition))) {
                switch (RND.nextInt(3)) {
                    case 0:
                        this.addPickable(new Invincibility(randomPosition));
                        break;
                    case 1:
                        this.addPickable(new SlowCars(randomPosition));
                    default:
                        this.addPickable(new Coin(randomPosition));
                        break;
                }
            }
        }
    }

    /**
     * Adds a new pickable to the list.
     * 
     * @param pick the pickable to add to the list.
     * 
     * @see Pickable.
     */
    private void addPickable(final Pickable pick) {
        this.pickables.add(pick);
    }

    /**
     * Deletes all the pickables present on the Chunk.
     */
    protected final void clearPickables() {
        this.pickables.clear();
    }
}
