package it.unibo.crossyroad.controller.api;

import it.unibo.crossyroad.model.api.Skin;

/**
 * Main controller, that links Menu, Shop and Game controllers.
 */
public interface AppController {
    /**
     * Sets the game controller.
     *
     * @param gc the game controller
     */
    void setGameController(GameController gc);

    /**
     * Sets the menu controller.
     *
     * @param mc the menu controller
     */
    void setMenuController(MenuController mc);

    // void setShopController(ShopController sc);

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
