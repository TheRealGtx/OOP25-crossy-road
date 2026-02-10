package it.unibo.crossyroad.controller.impl;

import java.util.Objects;
import java.util.Set;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.ShopController;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.view.api.ShopView;

/**
 * Implementation of ShopController.
 * 
 * @see ShopController
 */
public class ShopControllerImpl implements ShopController{
    
    private AppController appController;
    private StateManager stateManager;
    private ShopView shopView;

    /**
     * Constructor
     * 
     * @param appController the application controller.
     * @param stateManager the state manager.
     * @param shopView the shop view.
     */
    public ShopControllerImpl(final AppController appController, final StateManager stateManager, final ShopView shopView) {
        this.appController = Objects.requireNonNull(appController, "The application controller cannot be null");
        this.stateManager = Objects.requireNonNull(stateManager, "The state manager cannot be null"); 
        this.shopView = Objects.requireNonNull(shopView, "The shop view cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showShop() {
        this.shopView.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideShop() {
        this.shopView.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        this.appController.showMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getAllSkins() {
        return this.stateManager.getAllSkins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getUnlockedSkins() {
        return  this.stateManager.getAllUnlockedSkins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryUnlockSkin(final Skin skin) {
        return this.stateManager.unlockSkin(skin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateSkin(Skin skin) {
        return this.stateManager.activateSkin(skin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skin getActiveSkin() {
        return this.stateManager.getActiveSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCount() {
        System.out.println("" + this.stateManager.getCoinCounter());
        return this.stateManager.getCoinCounter();
    }
}
