package it.unibo.crossyroad.model.api;

public abstract class AbstractActiveChunk extends AbstractChunk implements ActiveChunk {
    private long timeSinceLastGeneration;

    public AbstractActiveChunk(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(GameParameters params, long deltaTime) {
        for (Obstacle obs : this.getObstacles()) {
            if (obs instanceof ActiveObstacle) {
                ((ActiveObstacle)obs).update(deltaTime, params);
            }

            if (this.shouldGenerateNewObstacles(deltaTime)) {
                this.generateObstacles();
            }
        }
    }

    /**
     * A method to determine if new obstacles should be generated.
     *
     * @param deltaTime the time elapsed since the last update
     * @return true if new obstacles should be generated, false otherwise
     */
    private boolean shouldGenerateNewObstacles(final long deltaTime) {
        this.timeSinceLastGeneration += deltaTime;

        if (this.timeSinceLastGeneration >= this.getGenerationInterval()) {
            this.timeSinceLastGeneration = 0;
            return true;
        }
        return false;
    }

    /**
     * Returns the interval between obstacle generations (in ms).
     *
     * @return The ms that must pass between two generations
     */
    protected abstract long getGenerationInterval();
}
