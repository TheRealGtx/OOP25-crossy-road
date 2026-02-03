package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;

/**
 * Implementation of the AppController interface.
 */
public class AppControllerImpl implements AppController {
    private final GameController gameController;
    // private final MenuController menuController;
    // private final ShopController shopController;

    /**
     * Constructor of AppControllerImpl.
     *
     * @param gameController the game controller
     */
    public AppControllerImpl(final GameController gameController) { // todo: add menuController and shopController
        this.gameController = gameController;
        // this.menuController = menuController;
        // this.shopController = shopController;
    }

    private void hideAllViews() {
        this.gameController.hideGame();
        // this.menuController.hideMenu();
        // this.shopController.hideShop();
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
        // this.menuController.showMenu();
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
    @Override
    public String getActiveSkinId() {
        // return this.shopController.getActiveSkinId();
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCount() {
        // this.shopController.getCoinCount();
        return 0;
    }
}
