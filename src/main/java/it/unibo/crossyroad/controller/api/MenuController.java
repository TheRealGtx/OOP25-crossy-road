package it.unibo.crossyroad.controller.api;

import java.nio.file.Path;

/**
 * An interface modelling a controller for menu interactions and navigation.
 */
public interface MenuController {

    /**
     * Adds a menu view to the controller.
     *
     * @param v the view to add.
     */
    void addView(MenuView v);

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
