package it.unibo.crossyroad.model.api;

/**
 * An abstract class representing a power-up that has a position in a 2D space.
 */
public abstract class AbstractPowerUp extends AbstractPickable implements PowerUp {

    /**
     * Indicates the remaining duration of the power-up.
     */
    private long remainingTime;

    /**
     * It creates a new power-up with the given position and duration.
     * 
     * @param position the initial position of the power-up.
     * @param remainingTime the duration of the power-up.
     */
    public AbstractPowerUp(final Position position, final long remainingTime) {
        super(position);
        this.remainingTime = remainingTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final GameParameters gameParameters) {
        this.remainingTime -= deltaTime;
        if (this.remainingTime <= 0) {
            this.deactivate(gameParameters);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getRemaining() {
        return this.remainingTime;
    }

    /**
     * Deactives the power-up and reverts its effect on the game parameters.
     * 
     * @param gameParameters the game parameters affected by this power-up.
     */
    protected abstract void deactivate(GameParameters gameParameters);
}
