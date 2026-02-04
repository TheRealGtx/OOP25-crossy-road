package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPositionable;
import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

/**
 * A class representing a water obstacle in the game.
 */
public final class Water extends AbstractPositionable implements Obstacle {

    /**
     * Constructor for Water.
     *
     * @param initialPosition The initial position of the Water obstacle.
     * @param dimension The dimension of the Water obstacle.
     */
    public Water(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
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
        return EntityType.WATER;
    }
}
