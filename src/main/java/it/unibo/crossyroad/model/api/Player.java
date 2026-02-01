package it.unibo.crossyroad.model.api;

/**
 * An interface representing the player (chicken) entity in the game.
 */
public interface Player extends Positionable {
    /**
     * Moves the player in the given direction.
     *
     * @param direction the direction to move the player
     * @param steps the number of steps to move the player in the specified direction (> 0)
     */
    void move(Direction direction, double steps);
}
