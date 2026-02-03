package it.unibo.crossyroad.controller.api;

/**
 * Main controller, that links Menu, Shop and Game controllers.
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
