package it.unibo.crossyroad.model.api;

import java.util.Objects;

import com.google.common.collect.Range;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Position p) {
        final Position pos = Objects.requireNonNull(p, "Position cannot be null");

        final var xRange = Range.closedOpen(this.position.x(), this.position.x() + this.dimension.width());
        final var yRange = Range.closedOpen(this.position.y(), this.position.y() + this.dimension.height());
        return xRange.contains(pos.x()) && yRange.contains(pos.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean overlaps(final Positionable second) {
        final var firstRangeX = Range.closedOpen(this.getPosition().x(), this.getPosition().x() + this.getDimension().width());
        final var firstRangeY = Range.closedOpen(this.getPosition().y(), this.getPosition().y() + this.getDimension().height());
        final var secondRangeX = Range.closedOpen(second.getPosition().x(),
                                                  second.getPosition().x() + second.getDimension().width()
                                                );
        final var secondRangeY = Range.closedOpen(second.getPosition().y(),
                                                  second.getPosition().y() + second.getDimension().height()
                                                );
        return firstRangeX.isConnected(secondRangeX) && firstRangeY.isConnected(secondRangeY)
            && !firstRangeX.intersection(secondRangeX).isEmpty()
            && !firstRangeY.intersection(secondRangeY).isEmpty();
    }
}
