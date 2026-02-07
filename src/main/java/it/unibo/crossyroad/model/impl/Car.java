package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.AbstractActiveObstacle;

/**
 * A class representing a car obstacle in the game.
 */
public class Car extends AbstractActiveObstacle {
    private final Direction direction;
    /**
     * It creates a new active obstacle (car) with the initial position, speed and direction.
     *
     * @param position the initial position of the car.
     * @param speed the static speed of the car.
     * @param direction the direction of the movement of the car.
     */
    public Car(final Position position, final double speed, final Direction direction) {
        super(position, new Dimension(2, 1), speed, direction);
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionType getCollisionType() {
        return CollisionType.DEADLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        if (this.direction == Direction.LEFT) {
            return EntityType.CAR_LEFT;
        } else {
            return EntityType.CAR_RIGHT;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getSpeedMultiplier(final GameParameters parameters) {
        return parameters.getCarSpeedMultiplier();
    }
}
