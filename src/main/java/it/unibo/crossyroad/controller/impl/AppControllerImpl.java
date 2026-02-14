package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.controller.api.ShopController;
import it.unibo.crossyroad.model.api.Skin;
import javafx.application.Platform;

import java.util.function.Function;

/**
 * Implementation of the AppController interface.
 */
public class AppControllerImpl implements AppController {
    private final GameController gameController;
    private final MenuController menuController;
    private final ShopController shopController;
    private boolean isGameOver;

    /**
     * Constructor for AppControllerImpl.
     *
     * @param gameControllerFactory factory function to create the GameController
     * @param menuControllerFactory factory function to create the MenuController
     * @param shopControllerFactory factory function to create the ShopController
     */
    public AppControllerImpl(
        final Function<AppController, GameController> gameControllerFactory,
        final Function<AppController, MenuController> menuControllerFactory,
        final Function<AppController, ShopController> shopControllerFactory
    ) {
        this.gameController = gameControllerFactory.apply(this);
        this.menuController = menuControllerFactory.apply(this);
        this.shopController = shopControllerFactory.apply(this);
        this.isGameOver = true;
    }

    private void hideAllViews() {
        this.gameController.hideGame();
        this.menuController.hideMenu();
        this.shopController.hideShop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameController getGameController() {
        return this.gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuController getMenuController() {
        return this.menuController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopController getShopController() {
        return this.shopController;
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
        } else {
            this.gameController.resumeGame();
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
        this.shopController.showShop();
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
    public void exitGame() {
        this.gameController.endGame();
        Platform.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skin getActiveSkin() {
        return this.shopController.getActiveSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCount() {
        return this.shopController.getCoinCount();
    }
}
