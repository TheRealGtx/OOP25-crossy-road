package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * An abstract class representing an active obstacle that has a position in a 2D space.
 */
public abstract class AbstractActiveObstacle extends AbstractPositionable implements ActiveObstacle {
    private final Direction direction;
    private final double speed;

    /**
     * It creates a new active obstacle with the given initial position, dimension, speed and direction.
     *
     * @param position the initial position of the active obstacle.
     * @param dimension the dimension of the active obstacle.
     * @param speed the static speed of the active obstacle.
     * @param direction the direction of the movement of the active obstacle.
     */
    public AbstractActiveObstacle(final Position position, final Dimension dimension,
                                  final double speed, final Direction direction) {
        super(position, dimension);
        if (direction == Direction.UP || direction == Direction.DOWN) {
            throw new IllegalArgumentException("ActiveObstacle can only move LEFT or RIGHT");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be positive");
        }
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final GameParameters parameters) {
        final double speedMultiplier = getSpeedMultiplier(parameters);
        if (speedMultiplier <= 0) {
            throw new IllegalArgumentException("Speed multiplier must be positive");
        }
        final double deltaX = this.speed * speedMultiplier * deltaTime / 1000.0 * (this.direction == Direction.LEFT ? -1 : 1);
        super.setPosition(new Position(super.getPosition().x() + deltaX, super.getPosition().y()));
    }

    /**
     * Get the speed multiplier based on the type of active obstacle.
     *
     * @param parameters the game parameters.
     * @return the speed multiplier.
     */
    protected abstract double getSpeedMultiplier(GameParameters parameters);

    /**
     * Checks whether this active obstacle is equal to another object.
     *
     * @param o the object to compare with.
     * @return true if the given object is considered equal to this obstacle, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractActiveObstacle other = (AbstractActiveObstacle) o;

        return Objects.equals(this.getDimension(), other.getDimension())
                && Objects.equals(this.getPosition(), other.getPosition())
                && this.getEntityType() == other.getEntityType();
    }

    /**
     * Computes the hash code for this active obstacle.
     *
     * @return an integer hash code consistent with equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEntityType(), getPosition(), getDimension(), speed, direction);
    }
}
