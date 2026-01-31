package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractActiveObstacle;
import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;

/**
 * A class representing a wood log transporting obstacle in the game.
 */
public class WoodLog extends AbstractActiveObstacle {

    /**
     * Constructor for WoodLog.
     *
     * @param speed Speed of the log.
     * @param direction Direction of the log.
     * @param position Initial position of the log
     * @param length Length of the log.
     */
    public WoodLog(final double speed, final Direction direction, final Position position, final double length) {
        super(position, new Dimension(length, 1), speed, direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionType getCollisionType() {
        return CollisionType.TRANSPORT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.WOOD_LOG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getSpeedMultiplier(final GameParameters parameters) {
        return 1.0;  // TODO
    }
}
