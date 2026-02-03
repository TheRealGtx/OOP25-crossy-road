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
        removeOutOfBoundObstacles();
        spawnIfNeeded(deltaTime);
    }

    /**
     * Spawns new obstacles if needed.
     * 
     * @param deltaTime time since last update.
     */
    protected abstract void spawnIfNeeded(long deltaTime);

    /**
     * Removes out of bound obstacles from the chunk.
     */
    private void removeOutOfBoundObstacles() {
        this.getObstacles().removeIf(obs -> obs.getPosition().x() < this.getPosition().x() - (obs.getDimension().width() + 2)
        || obs.getPosition().x() > this.getPosition().x() + this.getDimension().width() + (obs.getDimension().width() + 2));
    }
}
