package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.view.api.MenuView;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Implementation of MenuController.
 *
 * @see MenuController
 */
public class MenuControllerImpl implements MenuController {

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
    public MenuControllerImpl(AppController appController, MenuView menuView, StateManager s) {
        this.appController = Objects.requireNonNull(appController, "The application controller cannot be null");
        this.menuView = Objects.requireNonNull(menuView, "The menu view cannot be null");
        //this.stateManager = Objects.requireNonNull(s, "The state manager cannot be null");
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
    public void showGame() {
        this.appController.showGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Path p) throws IOException {
        this.stateManager.save(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(Path p) throws IOException {
        this.stateManager.load(p);
    }

}
