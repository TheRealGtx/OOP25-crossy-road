package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPositionable;
import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

/**
 * Passive obstacle, spawns on Grass.
 * 
 * @see Grass
 */
public final class Rock extends AbstractPositionable implements Obstacle {

    /**
     * Initializes the Obstacle.
     * 
     * @param initialPosition the Obstacle's initial position.
     * 
     * @param dimension the Obstacle's dimension.
     */
    public Rock(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ROCK;
    }

    @Override
    public CollisionType getCollisionType() {
        return CollisionType.SOLID;
    }
}
