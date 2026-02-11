package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * An abstract class representing a power-up that has a position in a 2D space.
 */
public abstract class AbstractPowerUp extends AbstractPickable implements PowerUp {

    /**
     * Indicates the remaining duration of the power-up.
     */
    private long remainingTime;
    private boolean isDone;

    /**
     * It creates a new power-up with the given position and duration.
     * 
     * @param position the initial position of the power-up.
     * @param remainingTime the duration of the power-up.
     */
    public AbstractPowerUp(final Position position, final long remainingTime) {
        super(position);
        this.remainingTime = remainingTime;
        this.isDone = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final GameParameters gameParameters) {
        this.remainingTime -= deltaTime;
        if (this.remainingTime <= 0) {
            this.isDone = true;
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
     * {@inheritDoc}
     */
    @Override
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Deactives the power-up and reverts its effect on the game parameters.
     * 
     * @param gameParameters the game parameters affected by this power-up.
     */
    protected abstract void deactivate(GameParameters gameParameters);

    /**
     * Checks whether this power up is equal to another object.
     *
     * @param o the object to compare with.
     * @return true if the given object is considered equal to this power up, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractPowerUp other = (AbstractPowerUp) o;

        return Objects.equals(this.getDimension(), other.getDimension())
                && Objects.equals(this.getPosition(), other.getPosition())
                && this.getEntityType() == other.getEntityType()
                && this.getRemaining() == other.getRemaining();
    }

    /**
     * Computes the hash code for this power up.
     *
     * @return an integer hash code consistent with equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEntityType(), getPosition(), getDimension(), getRemaining());
    }
}
