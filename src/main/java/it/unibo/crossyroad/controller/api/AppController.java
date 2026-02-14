package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.model.api.Skin;

/**
 * Main controller, that links Menu, Shop and Game controllers.
 */
public interface AppController {
    /**
     * Gets the GameController.
     *
     * @return the GameController created by the AppController
     */
    GameController getGameController();

    /**
     * Gets the MenuController.
     *
     * @return the MenuController created by the AppController
     */
    MenuController getMenuController();

    /**
     * Gets the ShopController.
     *
     * @return the ShopController created by the AppController
     */
    ShopController getShopController();

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
     * Method called by GameController when the game is over.
     */
    void gameOver();

    /**
     * Closes the game and ends all the threads.
     */
    void exitGame();

    /**
     * Gets the active skin.
     *
     * @return the active skin
     */
    Skin getActiveSkin();

    /**
     * Gets the actual coin count.
     *
     * @return the coin count
     */
    int getCoinCount();
}
