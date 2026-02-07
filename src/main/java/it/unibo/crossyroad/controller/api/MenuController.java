package it.unibo.crossyroad.controller.api;

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
     * Show the game view.
     */
    void showGame();

    /**
     * Save application state.
     *
     * @param p the file path
     */
    void save(Path p);

    /**
     * Load application state.
     *
     * @param p the file path
     */
    void load(Path p);
}
