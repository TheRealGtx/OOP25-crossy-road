package it.unibo.crossyroad.model.api;

/**
 * A power-up in the game.
 */
public interface PowerUp extends Pickable {
    /**
     * Reload the remaining time of the power-up and deactive it when expired.
     * 
     * @param deltaTime the elapsed time since the last update.
     * @param gameParameters the game parameters affected by this power up.
     */
    void update(long deltaTime, GameParameters gameParameters);

    /**
     * Returns the remaining time of the power-up.
     * 
     * @return the remaining time.
     */
    long getRemaining();
}
