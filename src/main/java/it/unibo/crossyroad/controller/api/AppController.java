package it.unibo.crossyroad.controller.api;

/**
 * Main controller, that links Menu and Game controllers.
 */
public interface AppController {
    /**
     * Shows the game view.
     */
    void showGame();

    /**
     * Shows the menu view.
     */
    void showMenu();

    /**
     * Hides the game view.
     */
    void hideGame();

    /**
     * Hides the menu view.
     */
    void hideMenu();

    /**
     * Shows the shop view.
     */
    void showShop();

    /**
     * Gets the active skin id.
     *
     * @return the active skin id
     */
    String getActiveSkinId();

    /**
     * Gets the actual coin count.
     *
     * @return the coin count
     */
    int getCoinCount();
}
