package it.unibo.crossyroad.model.api;

import java.util.List;
import java.util.Map;

/**
 * GameManager interface.
 * The Game Manager purpose is to manage the game, by generating/updating and disposing the objects that occupy the game terrain.
 */
public interface GameManager {

    /**
     * Returns a list of all the Positionable elements in game.
     * 
     * @return a list of all the Positionable elements in game.
     * @see Positionable
     */
    List<Positionable> getPositionables();

    /**
     * Returns a map of the active power ups in game along with their remaining lifetime.
     * 
     * @return a map of the active power ups and their remaining lifetime.
     * @see EntityType
     */
    Map<EntityType, Long> getActivePowerUps();

    /**
     * Updates the map by calling the update() function of every item on the map.
     * 
     * @param deltaTime time since the last update.
     */
    void update(long deltaTime);

    /**
     * Updates the player position.
     * 
     * @param d the direction the player is moving.
     * @see Direction
     */
    void movePlayer(Direction d);

    /**
     * Whether the game ended or not.
     * 
     * @return true if the game ended, false otherwise.
     */
    boolean isGameOver();

    /**
     * Resets the game for a new match.
     */
    void reset();

    /**
     * Stops the game.
     */
    void endGame();
}
