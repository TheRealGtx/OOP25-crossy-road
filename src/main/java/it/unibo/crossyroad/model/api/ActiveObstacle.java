package it.unibo.crossyroad.model.api;

/**
 * An active obstacle that can move.
 */
public interface ActiveObstacle extends Obstacle {
    /**
     * Updates the position of the active obstacle base on the given speed.
     *
     * @param deltaTime time since last update.
     * @param parameters the speed of the obstacle.
     */
    void update(long deltaTime, GameParameters parameters);
}
