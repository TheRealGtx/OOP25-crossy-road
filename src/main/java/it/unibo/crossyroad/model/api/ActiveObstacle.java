package it.unibo.crossyroad.model.api;

/**
 * An active obstacle that can move.
 */
public interface ActiveObstacle extends  Obstacle {
    /**
     * Updates the position of the active obstacle base on the given speed.
     *
     * @param deltaTime time since last update.
     * @param speed the speed of the obstacle.
     */
    void update(final long deltaTime, final double speed);
}
