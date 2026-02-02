package it.unibo.crossyroad.model.api;

/**
 * Enum representing a direction.
 */
public enum Direction {
    UP(Position.of(0, -1)),
    DOWN(Position.of(0, 1)),
    LEFT(Position.of(-1, 0)),
    RIGHT(Position.of(1, 0));

    private final Position delta;

    Direction(final Position delta) {
        this.delta = delta;
    }

    /**
     * Gets the delta position associated with the direction.
     *
     * @return the delta position
     */
    public Position getDelta() {
        return this.delta;
    }
}
