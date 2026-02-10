package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.model.api.Skin;

/**
 * An interface modelling a controller for menu interactions and navigation.
 */
public interface MenuController {

    /**
     * Show the menu view.
     */
    void showMenu();

    /**
     * Hide the menu view.
     */
    void hideMenu();

    /**
     * Show the shop view.
     */
    void showShop();

    /**
     * Show the game view.
     */
    void showGame();

    /**
     * Return the active skin.
     *
     * @return the active skin
     */
    Skin getActiveSkin();

    /**
     * Save application state.
     */
    void save();

    /**
     * Load application state.
     */
    void load();
}