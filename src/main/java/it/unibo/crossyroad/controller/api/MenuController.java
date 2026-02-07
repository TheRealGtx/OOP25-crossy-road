package it.unibo.crossyroad.controller.api;

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
     * Save application state.
     *
     * @param p the file path
     * @throws IOException if an I/O error occurs.
     */
    void save(Path p) throws IOException;

    /**
     * Load application state.
     *
     * @param p the file path
     * @throws IOException if an I/O error occurs.
     */
    void load(Path p) throws IOException;
}
