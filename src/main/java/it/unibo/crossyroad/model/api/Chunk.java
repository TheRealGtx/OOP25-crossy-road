package it.unibo.crossyroad.model.api;

import java.util.List;

/**
 * Represents a portion of the map, each one with different obstacles on it.
 */
public interface Chunk extends Positionable {

    /**
     * Initializes the Chunk.
     */
    void init();

    /**
     * Returns the Obstacles present on the Chunk.
     * 
     * @return a list of the Obstacles present on the Chunk.
     * 
     * @see Obstacle
     */
    List<Obstacle> getObstacles();

    /**
     * Returns the Pickables present on the Chunk.
     * 
     * @return a list of the Pickables present on the Chunk.
     */
    List<Pickable> getPickables();

    /**
     * Returns the Positionables present on the Chunk.
     * 
     * @return a list of the Positionables present on the Chunk.
     */
    List<Positionable> getPositionables();

    /**
     * Returns the active PowerUps present on the Chunk.
     * 
     * @return a list of the active PowerUps present on the Chunk.
     */
    List<PowerUp> getActivePowerUp();
}
