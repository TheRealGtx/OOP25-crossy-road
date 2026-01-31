package it.unibo.crossyroad.model.api;

/**
 * Represents a chunk with active obstacle on top of it.
 */
public abstract class AbstractActiveChunk extends AbstractChunk implements ActiveChunk {

    /**
     * Initializes the Chunk.
     * 
     * @param initialPosition the Chunk's initial position.
     * 
     * @param dimension the Chunk's dimension.
     */
    public AbstractActiveChunk(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameParameters params, final long deltaTime) {
        for (final Obstacle obs : this.getObstacles()) {
            if (obs instanceof ActiveObstacle) {
                ((ActiveObstacle) obs).update(deltaTime, params);
            }
        }
        spawnIfNeeded(deltaTime);
        removeOutOfBoundObstacles();
    }

    /**
     * Spawns new obstacles if needed.
     */
    protected abstract void spawnIfNeeded(long deltaTime);

    /**
     * Removes out of bound obstacles from the chunk.
     */
    protected abstract void removeOutOfBoundObstacles();
}
