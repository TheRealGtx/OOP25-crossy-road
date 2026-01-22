package it.unibo.crossyroad.model.api;

/**
 * An obstacle in the game.
 */
public interface Obstacle extends Positionable {
    /**
     * @return the type of collision of this obstacle, indicating how it interacts with the player.
     */
    CollisionType getCollisionType();
}
