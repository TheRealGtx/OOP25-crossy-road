package it.unibo.crossyroad.model.api;

import com.google.common.collect.Range;

/**
 * A record representing the dimensions of an entity.
 *
 * @param width The width of the entity
 * @param height The height of the entity
 */
public record Dimension(double width, double height) {
    /**
     * Constructs a Dimension object with the specified width and height.
     *
     * @param width the width of the dimension (> 0)
     * @param height the height of the dimension (> 0)
     * @throws IllegalArgumentException if width or height is less than or equal to 0
     */
    public Dimension {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
    }

    /**
     * Factory method to create a Dimension instance.
     *
     * @param width width of the dimension
     * @param height height of the dimension
     * @return a new Dimension instance
     */
    public static Dimension of(final double width, final double height) {
        return new Dimension(width, height);
    }

    /**
     * Factory method to create a square Dimension instance.
     *
     * @param side length of each side of the square
     * @return a new square Dimension instance
     */
    public static Dimension square(final double side) {
        return new Dimension(side, side);
    }

    /**
     * Factory method to create a unit Dimension instance (1x1).
     *
     * @return a new unit Dimension instance
     */
    public static Dimension unit() {
        return square(1);
    }

    /**
     * Checks if a given point is contained within the area defined by this dimension, given an origin position.
     *
     * @param origin origin position of the area defined by this dimension
     * @param point point to check for containment
     * @return true if the point is contained within the area
     */
    public boolean containsPoint(final Position origin, final Position point) {
        final var xRange = Range.closedOpen(origin.x(), origin.x() + this.width);
        final var yRange = Range.closedOpen(origin.y(), origin.y() + this.height);
        return xRange.contains(point.x()) && yRange.contains(point.y());
    }

    /**
     * Checks if this dimension overlaps with another dimension, given their respective origins.
     *
     * @param origin1 the origin position of this dimension
     * @param otherOrigin the origin position of the other dimension
     * @param otherDimension the other dimension to check for overlap
     * @return true if the dimensions overlap
     */
    public boolean overlaps(final Position origin1, final Position otherOrigin, final Dimension otherDimension) {
        final var xRange1 = Range.closedOpen(origin1.x(), origin1.x() + this.width);
        final var yRange1 = Range.closedOpen(origin1.y(), origin1.y() + this.height);
        final var xRange2 = Range.closedOpen(otherOrigin.x(), otherOrigin.x() + otherDimension.width);
        final var yRange2 = Range.closedOpen(otherOrigin.y(), otherOrigin.y() + otherDimension.height);
        return xRange1.isConnected(xRange2) && yRange1.isConnected(yRange2)
            && !xRange1.intersection(xRange2).isEmpty()
            && !yRange1.intersection(yRange2).isEmpty();
    }
}
