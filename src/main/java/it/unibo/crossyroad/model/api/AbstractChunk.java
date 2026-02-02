package it.unibo.crossyroad.model.api;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Chunk.
 */
public abstract class AbstractChunk extends AbstractPositionable implements Chunk {

    private final List<Obstacle> obstacles;

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.clearObstacles();
        this.generateObstacles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Obstacle> getObstacles() { 
        return List.copyOf(this.obstacles);
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
        this.obstacles.add(obs);
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
    private void generatePickables() { }

    /**
     * Updates the positions of the Obstacles on the Chunk.
     * 
     * @param g the game parameters.
     * 
     * @param deltaTime time since last update.
     */
    private void moveObstacles(final GameParameters g, final long deltaTime) {
        for (final Obstacle obs : this.obstacles) {
            if (obs instanceof ActiveObstacle) {
                ((ActiveObstacle) obs).update(deltaTime, g);
            }
        }
    }

    /**
     * Updates the positions of the Pickables on the Chunk.
     */
    private void movePickables() { }
}
