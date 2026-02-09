package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.model.api.Skin;

import java.io.IOException;
import java.nio.file.Path;

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
     *
     * @throws IOException if an I/O error occurs.
     */
    void save() throws IOException;

    /**
     * Load application state.
     *
     * @throws IOException if an I/O error occurs.
     */
    void load() throws IOException;
}
