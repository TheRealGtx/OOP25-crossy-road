package it.unibo.crossyroad.model.api;

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
}
