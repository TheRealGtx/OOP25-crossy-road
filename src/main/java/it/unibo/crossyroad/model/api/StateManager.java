package it.unibo.crossyroad.model.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * Manages player skins.
 */
public interface StateManager {

    /**
     * Returns the player's coin count.
     * 
     * @return the coin account.
     */
    int getCoinCounter();

    /**
     * Reset the coin counter and locks all skins except the default one.
     */
    void reset();

    /**
     * Return all skins.
     * 
     * @return the set of skin.
     */
    Set<Skin> getAllSkins();

    /**
     * Returns all unlocked skins.
     * 
     * @return the set of skin.
     */
    Set<Skin> getAllUnlockedSkins();

    /**
     * Tries to unlock the skin if there are enough coins.
     * 
     * @param skin the skin.
     * @return true if the skin has been unlocked, false otherwise.
     */
    boolean unlockSkin(Skin skin);

    /**
     * Tries to set the selected skin as active.
     * 
     * @param skin the skin you want to set as active.
     * @return true if the selected skin has been set as active, false otherwise.
     */
    boolean activateSkin(Skin skin);

    /**
     * Returns the skin set as active.
     * 
     * @return the active skin.
     */
    Skin getActiveSkin();

    /**
     * Extracts previously saved data.
     * 
     * @param path the path to the directory where the data are stored.
     * @throws IOException if the file doesn't exist or is not readable.
     */
    void load(Path path) throws IOException;

    /**
     * Save the current state of the game.
     * 
     * @param path the path to the directory where we want to save the game state.
     * @throws IOException if the file cannot be found or written.
     */
    void save(Path path) throws IOException;
} 
