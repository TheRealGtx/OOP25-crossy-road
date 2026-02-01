package it.unibo.crossyroad.model.api;

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
}
