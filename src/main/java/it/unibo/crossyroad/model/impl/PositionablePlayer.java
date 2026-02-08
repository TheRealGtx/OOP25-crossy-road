package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPositionable;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.Player;

import java.util.Objects;

/**
 * A class representing the player (chicken) entity in the game.
 */
public class PositionablePlayer extends AbstractPositionable implements Player {
    /**
     * It creates a new positionable entity with the given initial position and a dimension of 1x1.
     *
     * @param initialPosition the initial position of the entity (not null)
     */
    public PositionablePlayer(final Position initialPosition) {
        super(initialPosition, Dimension.unit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Direction direction, final double steps) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        if (steps <= 0) {
            throw new IllegalArgumentException("Steps must be greater than 0. Received: " + steps);
        }

        this.setPosition(direction.getDelta().scale(steps).relative(this.getPosition()));
    }
}
