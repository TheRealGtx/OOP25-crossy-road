package it.unibo.crossyroad.model.api;

import java.util.Objects;

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

    /**
     * Applies the direction to the given position and returns the new position.
     * Gets the delta position associated with the direction.
     *
     * @param position the original position
     * @return the new position after applying the direction
     * @throws NullPointerException if position is null
     */
    public Position apply(final Position position) {
        Objects.requireNonNull(position, "Position cannot be null");

        return switch (this) {
            case UP -> new Position(position.x(), position.y() - 1);
            case DOWN -> new Position(position.x(), position.y() + 1);
            case LEFT -> new Position(position.x() - 1, position.y());
            case RIGHT -> new Position(position.x() + 1, position.y());
        };
    }
}
