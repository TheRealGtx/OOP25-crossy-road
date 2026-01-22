package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.*;

/**
 * A class representing a car obstacle in the game.
 */
public class Car extends AbstractActiveObstacle {

    /**
     * It creates a new active obstacle (car) with the initial position, dimension and direction.
     *
     * @param position the initial position of the car.
     * @param dimension the initial dimension of the car.
     * @param direction the direction of the movement of the car.
     */
    public  Car(final Position position, final Dimension dimension, final Direction direction) {
        super(position, dimension, direction);
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
        return EntityType.CAR;
    }
}
