package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * Enum representing a direction.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Applies the direction to the given position and returns the new position.
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
