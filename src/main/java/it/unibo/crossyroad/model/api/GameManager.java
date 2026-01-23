package it.unibo.crossyroad.model.api;

import java.util.List;
import java.util.Map;

public interface GameManager {

    /**
     * Returns a list of all the Positionable elements in game.
     * 
     * @return a list of all the Positionable elements in game.
     */
    List<Positionable> getPositionables();

    /**
     * Returns a map of the active power ups in game along with their remaining life time.
     * 
     * @return a map of the active power ups and their remaining life time.
     */
    Map<EntityType, Long> getActivePowerUps();

    /**
     * Updates the map.
     * 
     * @param deltaTime time since the last update.
     */
    void update(long deltaTime);

    /**
     * Updates the player position.
     * 
     * @param d the direction the player is moving.
     */
    void movePlayer(Direction d);

    /**
     * Wether the game ended or not.
     * 
     * @return true if the game ended, false otherwise.
     */
    boolean isGameOver();

    /**
     * Resets the game for a new match.
     */
    void reset();
}
