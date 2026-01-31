package it.unibo.crossyroad.model.api;

import java.util.List;

public interface Chunk extends Positionable {
    
    /**
     * Initializes the Chunk.
     */
    void init();

    /**
     * Returns the Obstacles present on the Chunk.
     * 
     * @return a list of the Obstacles present on the Chunk
     * 
     * @see Obstacle
     */
    List<Obstacle> getObstacles();
}
