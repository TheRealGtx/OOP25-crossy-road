package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * Represents a chunk with active obstacles on top of it.
 */
public abstract class AbstractActiveChunk extends AbstractChunk {
    /**
     * Initializes the Chunk.
     *
     * @param initialPosition the Chunk's initial position.
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
        Objects.requireNonNull(params, "Game parameters cannot be null");
        this.getObstacles().stream()
                           .filter(o -> o instanceof ActiveObstacle)
                           .map(o -> (ActiveObstacle) o)
                           .forEach(ao -> ao.update(deltaTime, params));
        //Updates pickables
        super.update(params, deltaTime);
        this.removeOutOfBoundObstacles();

        if (this.shouldGenerateNewObstacles(deltaTime)) {
            this.generateObstacles();
        }
    }

    /**
     * A method to determine if new obstacles should be generated.
     *
     * @param deltaTime the time elapsed since the last update.
     * @return true if new obstacles should be generated, false otherwise.
     */
    protected abstract boolean shouldGenerateNewObstacles(long deltaTime);

    /**
     * Removes out of bound obstacles from the chunk.
     */
    private void removeOutOfBoundObstacles() {
        this.getObstacles().stream()
            .filter(obs -> obs instanceof ActiveObstacle
                    && (obs.getPosition().x() < this.getPosition().x() - (obs.getDimension().width() + 3)
                    || obs.getPosition().x() > this.getPosition().x() + this.getDimension().width()
                                                                        + (obs.getDimension().width() + 3)))
            .forEach(this::removeObstacle);
    }
}
