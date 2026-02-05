package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.GameManager;

/**
 * Implementation of the GameController.
 *
 * @see GameController
 */
final class GameControllerImpl implements GameController {

    private final AppController appController;
    private final GameManager gameManager;

    public GameControllerImpl(final AppController appController, final GameManager gameManager) {
        this.appController = appController;
        this.gameManager = gameManager;
    }

    @Override
    public void showGame() {

    }

    @Override
    public void hideGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hideGame'");
    }

    @Override
    public void showMenu() {
        this.appController.showMenu();
    }

    @Override
    public void startLoop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startLoop'");
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void processInput() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processInput'");
    }

    private String getActiveSkin() {
        return this.appController.getActiveSkinId();
    }

    private int getCoinCount() {
        return this.appController.getCoinCount();
    }
}
