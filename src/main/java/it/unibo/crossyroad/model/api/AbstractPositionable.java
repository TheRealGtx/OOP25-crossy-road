package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * An abstract class representing an entity that has a position in a 2D space.
 */
public abstract class AbstractPositionable implements Positionable {
    private Position position;
    private final Dimension dimension;

    /**
     * It creates a new positionable entity with the given initial position and dimension.
     *
     * @param initialPosition the initial position of the entity
     * @param dimension the dimension of the entity
     * @throws NullPointerException if initialPosition or dimension is null
     */
    public AbstractPositionable(final Position initialPosition, final Dimension dimension) {
        this.position = Objects.requireNonNull(initialPosition, "Initial position cannot be null");
        this.dimension = Objects.requireNonNull(dimension, "Dimension cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Set a new position to the entity.
     *
     * @param position the new position
     * @throws NullPointerException if position is null
     */
    protected void setPosition(final Position position) {
        this.position = Objects.requireNonNull(position, "Position cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseY(final double delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Delta must be positive");
        }

        position = new Position(position.x(), position.y() + delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getDimension() {
        return dimension;
    }
}
