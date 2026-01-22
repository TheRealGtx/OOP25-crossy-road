package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.*;

/**
 * A class representing a train obstacle in the game.
 */
public class Train extends AbstractActiveObstacle {

    /**
     * It creates a new active obstacle (train) with the initial position, dimension and direction.
     *
     * @param position the initial position of the train.
     * @param dimension the initial dimension of the train.
     * @param direction the direction of the movement of the train.
     */
    public Train(final Position position, final Dimension dimension, final Direction direction) {
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
        return EntityType.TRAIN;
    }
}
