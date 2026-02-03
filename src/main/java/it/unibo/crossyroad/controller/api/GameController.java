package it.unibo.crossyroad.controller.api;

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
     * Starts the gameLoop.
     */
    void startLoop();

    /**
     * Pauses the game.
     */
    void pause();

    /**
     * Resumes the game.
     */
    void resume();

    /**
     * Process the users's input.
     */
    void processInput();
}
