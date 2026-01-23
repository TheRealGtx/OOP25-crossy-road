package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPositionable;
import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

public class Rock extends AbstractPositionable implements Obstacle {

    public Rock(Position initialPosition, Dimension dimension) {
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
