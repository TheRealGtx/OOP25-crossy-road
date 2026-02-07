package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.model.api.Skin;

/**
 * Implementation of the AppController interface.
 */
public class AppControllerImpl implements AppController {
    private GameController gameController;
    private MenuController menuController;
    // private ShopController shopController;
    private boolean isGameOver;

    /**
     * Constructor for AppControllerImpl.
     */
    public AppControllerImpl() {
        this.isGameOver = true;
    }

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
        if (this.isGameOver) {
            this.isGameOver = false;
            this.gameController.startLoop();
        }
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
    @Override
    public void gameOver() {
        this.hideAllViews();
        this.isGameOver = true;
        this.menuController.showMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skin getActiveSkin() {
        // return this.shopController.getActiveSkinId();
        return null;
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
