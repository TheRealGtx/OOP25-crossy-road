package it.unibo.crossyroad.model.api;

/**
 * A record representing a position in a 2D space.
 *
 * @param x x-axis coordinate
 * @param y y-axis coordinate
 */
public record Position(double x, double y) {
    /**
     * Factory method to create a new Position instance.
     *
     * @param x x-axis coordinate
     * @param y y-axis coordinate
     * @return a new Position instance
     */
    public static Position of(final double x, final double y) {
        return new Position(x, y);
    }

    /**
     * Returns a new Position that is the sum of this Position and the given Position.
     *
     * @param p the Position to add
     * @return the sum of the two Positions
     */
    public Position relative(final Position p) {
        return of(this.x + p.x(), this.y + p.y());
    }

    /**
     * Returns a new Position that is this Position scaled by the given factor.
     *
     * @param factor the scaling factor
     * @return the scaled Position
     */
    public Position scale(final double factor) {
        return of(this.x * factor, this.y * factor);
    }
}
