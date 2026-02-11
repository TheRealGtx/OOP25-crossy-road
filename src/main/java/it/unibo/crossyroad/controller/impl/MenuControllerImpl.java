package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.view.api.MenuView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Implementation of MenuController.
 *
 * @see MenuController
 */
public class MenuControllerImpl implements MenuController {

    private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "crossyroad");
    private static final Logger LOGGER = Logger.getLogger(MenuControllerImpl.class.getName());

    private final AppController appController;
    private final StateManager stateManager;
    private final MenuView menuView;

    /**
     * Constructor.
     *
     * @param appController the application controller.
     * @param menuView the menu view.
     * @param s the state manager.
     */
    public MenuControllerImpl(final AppController appController, final MenuView menuView, final StateManager s) {
        this.appController = Objects.requireNonNull(appController, "The application controller cannot be null");
        this.menuView = Objects.requireNonNull(menuView, "The menu view cannot be null");
        this.stateManager = Objects.requireNonNull(s, "The state manager cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu() {
        this.menuView.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideMenu() {
        this.menuView.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showShop() {
        this.appController.showShop();
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
    public void showGame() {
        this.appController.showGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        try {
            this.stateManager.save(SAVE_PATH);
        } catch (final IOException ex) {
            LOGGER.severe("Failed to save game state");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        try {
            this.stateManager.load(SAVE_PATH);
        } catch (final IOException ex) {
            LOGGER.severe("Failed to load game state");
        }
    }
}
