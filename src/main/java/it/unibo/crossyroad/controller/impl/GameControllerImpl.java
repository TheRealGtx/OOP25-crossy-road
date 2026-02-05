package it.unibo.crossyroad.controller.impl;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.GameManager;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameManagerImpl;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;
import it.unibo.crossyroad.view.api.GameView;

/**
 * Implementation of the GameController.
 *
 * @see GameController
 */
final class GameControllerImpl implements GameController {

    private final AppController appController;
    private final GameView gameView;
    private final boolean pause;
    private final GameManager gameManager;
    private final GameParameters parameters;

    public GameControllerImpl(AppController appController, GameView gameView) {
        this.appController = appController;
        this.gameView = gameView;
        this.pause = false;

        this.parameters = new GameParametersBuilder()
                            .setCarSpeedMultiplier(1)
                            .setCoinCount(0)
                            .setCoinMultiplier(0)
                            .setInvincibility(false)
                            .setLogSpeedMultiplier(1.0)               
                            .setTrainSpeedMultiplier(1)
                            .build();
        this.gameManager = new GameManagerImpl(this.parameters);
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
        this.gameManager.reset();
        long deltaTime = 0;
        long lastUpdate = 0;

        while (!this.pause && !this.gameManager.isGameOver()) {
            deltaTime = System.currentTimeMillis() - lastUpdate;
            lastUpdate = System.currentTimeMillis();
            this.gameManager.update(deltaTime);
        }
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
