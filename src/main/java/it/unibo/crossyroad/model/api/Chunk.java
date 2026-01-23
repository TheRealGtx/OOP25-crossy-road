package it.unibo.crossyroad.model.api;

import java.util.List;

public interface Chunk extends Positionable {
    
    /**
     * Initializes the Chunk.
     */
    void init();

    List<Obstacle> getObstacles();

    //TODO update

    //TODO getPickables

    //TODO geActivePowerUps
}
