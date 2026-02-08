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
     * 
     * @see Pickable
     */
    List<Pickable> getPickables();

    /**
     * Returns the Positionables present on the Chunk.
     * 
     * @return a list of the Positionables present on the Chunk.
     * 
     * @see Positionables
     */
    List<Positionable> getPositionables();

    /**
     * Returns the active PowerUps present on the Chunk.
     * 
     * @return a list of the active PowerUps present on the Chunk.
     * 
     * @see PowerUp
     */
    List<PowerUp> getActivePowerUp();

    /**
     * Removes a pickable from the internal list.
     * 
     * @param pick the pickable to remove from the internal list.
     * 
     * @see Pickable
     */
    void removePickable(Pickable pick);

    /**
     * Updates the elements present on the Chunk and the Chunk itself.
     * 
     * @param params the GameParameters.
     * 
     * @param deltaTime time since last update.
     * 
     * @see GameParameters
     */
    void update(GameParameters params, long deltaTime);
}
