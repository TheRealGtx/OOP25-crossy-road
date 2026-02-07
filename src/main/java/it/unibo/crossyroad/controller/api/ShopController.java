package it.unibo.crossyroad.controller.api;

import java.util.Set;

import it.unibo.crossyroad.model.api.Skin;

/**
 * Controller for managing the shop and skin selection.
 */
public interface ShopController {

    /**
     * Display the shop view.
     */
    void showShop();

    /**
     * Hide the shop view.
     */
    void hideShop();

    /**
     * Display the main menu view.
     */
    void showMenu();

    /**
     * Get all skins.
     * 
     * @return the set of all skins.
     */
    Set<Skin> getAllSkins();

    /**
     * Get all unlocked skin.
     * 
     * @return the set of unlocked skins.
     */
    Set<Skin> getUnlockedSkins();

    /**
     * Try to unlock a skin if there are enough coins.
     * 
     * @param skin the skin to unlock.
     * @return true if the skin is unlocked, false otherwise.
     */
    boolean tryUnlockSkin(Skin skin);

    /**
     * Activate a skin if it is unlocked.
     * 
     * @param skin the skin to activate.
     * @return true if the skin is set as active, false otherwise.
     */
    boolean activateSkin(Skin skin);

    /**
     * Get the currently active skins.
     * 
     * @return the active skin.
     */
    Skin getActiveSkin();

    /**
     * Get the current coin count.
     * 
     * @return the number of coins.
     */
    int getCoinCount();
}
