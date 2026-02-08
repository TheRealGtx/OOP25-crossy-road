package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.view.api.UserInput;

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
     * Starts the game loop.
     */
    void startLoop();

    /**
     * Stops the game.
     */
    void endGame();

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
     * 
     * @param input the user's input.
     */
    void processInput(UserInput input);

    /**
     * Get the path to the current selected skin.
     * 
     * @return the path to the current selected skin.
     */
    String getActiveSkin();
}
