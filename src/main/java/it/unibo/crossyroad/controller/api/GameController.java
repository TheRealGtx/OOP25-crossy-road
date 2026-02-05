package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.model.api.Direction;

/**
 * Controller that manages the Game.
 */
public interface GameController {

    /**
     * Shows the game view.
     */
    void showGame();

    /**
     * Hides the game view.
     */
    void hideGame();

    /**
     * Shows the menu view.
     */
    void showMenu();

    /**
     * Hides the menu view.
     */
    void hideMenu();

    /**
     * Starts the game loop.
     */
    void startLoop();

    /**
     * Pauses the game.
     */
    void pauseGame();

    /**
     * Resumes the game.
     */
    void resumeGame();

    /**
     * Process the users's input.
     */
    void processInput(Direction d);
}
