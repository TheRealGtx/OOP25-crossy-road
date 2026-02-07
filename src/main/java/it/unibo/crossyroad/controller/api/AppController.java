package it.unibo.crossyroad.controller.api;

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
     * Gets the active skin id.
     *
     * @return the active skin id
     */
    //String getActiveSkinId();

    /**
     * Gets the actual coin count.
     *
     * @return the coin count
     */
    int getCoinCount();
}
