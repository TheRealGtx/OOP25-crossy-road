package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.AbstractActiveObstacle;

/**
 * A class representing a train obstacle in the game.
 */
public class Train extends AbstractActiveObstacle {

    /**
     * It creates a new active obstacle (train) with the initial position, speed and direction.
     *
     * @param position the initial position of the train.
     * @param speed the static speed of the train.
     * @param direction the direction of the movement of the train.
     */
    public Train(final Position position, final double speed, final Direction direction) {
        super(position, new Dimension(8, 1), speed, direction);
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getSpeedMultiplier(final GameParameters parameters) {
        return parameters.getTrainSpeedMultiplier();
    }
}
