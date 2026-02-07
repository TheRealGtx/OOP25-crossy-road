package it.unibo.crossyroad.model.api;

import java.io.IOException;
import java.util.Set;

/**
 * Manages all the skins in the game.
 */
public interface SkinManager {

    /**
     * Returns all skins.
     * 
     * @return the set of skin.
     */
    Set<Skin> getSkins();

    /**
     * Tries to unlock the skin if the balance is enough. 
     * 
     * @param skin the skin to unlock.
     * @param balance the balance of the player.
     * @return the game's updated number of coins.
     */
    int tryUnlock(Skin skin, int balance);

    /**
     * Returns all unlocked skins.
     * 
     * @return the set of skin.
     */
    Set<Skin> getUnlockedSkins();

    /**
     * Locks all skins except the default one.
     */
    void lockSkins();

    /**
     * Save skins from the file at the given path.
     * 
     * @param path the file path.
     */
    void loadFromFile(String path) throws IOException;

    /**
     * Updates the status of skins by unlocking those whose is present in the provided set.
     * 
     * @param skins set of skins to unlock.
     */
    void loadUnlockedSkins(Set<Skin> skins);
}
