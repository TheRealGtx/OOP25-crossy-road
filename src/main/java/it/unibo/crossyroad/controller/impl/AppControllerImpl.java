package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.controller.api.MenuController;

/**
 * Implementation of the AppController interface.
 */
public class AppControllerImpl implements AppController {
    private GameController gameController;
    private MenuController menuController;
    // private ShopController shopController;

    private void hideAllViews() {
        this.gameController.hideGame();
        this.menuController.hideMenu();
        // this.shopController.hideShop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameController(final GameController gc) {
        this.gameController = gc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMenuController(final MenuController mc) {
        this.menuController = mc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGame() {
        this.hideAllViews();
        this.gameController.showGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        this.hideAllViews();
        this.menuController.showMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showShop() {
        this.hideAllViews();
        // this.shopController.showShop();
    }

    /**
     * {@inheritDoc}
     */
    /*@Override
    public String getActiveSkinId() {
        // return this.shopController.getActiveSkinId();
        return "";
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCount() {
        // this.shopController.getCoinCount();
        return 0;
    }
}
