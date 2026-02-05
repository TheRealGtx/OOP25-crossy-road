package it.unibo.crossyroad.controller.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.Direction;
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
final class GameControllerImpl extends Thread implements GameController {

    private final AppController appController;
    private final GameView gameView;
    private volatile boolean pause;
    private final GameManager gameManager;
    private final GameParameters parameters;
    private final Loop loop;
    private final LinkedBlockingQueue<Direction> queue;

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
        this.loop = new Loop();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void showGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hideGame'");
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
    public void hideMenu() {
        this.appController.hideMenu();
    }

    @Override
    public void startLoop() {
        this.loop.start();
    }

    @Override
    public void pauseGame() {
        this.pause = true;
        this.showMenu();
        this.hideGame();
    }

    @Override
    public void resumeGame() {
        this.pause = false;
        this.showGame();
        this.hideMenu();
    }

    @Override
    public void processInput(Direction d) {
        this.queue.add(d);
    }

    private String getActiveSkin() {
        return this.appController.getActiveSkinId();
    }

    private int getCoinCount() {
        return this.appController.getCoinCount();
    }

    private final class Loop extends Thread {

        public void run() {
            gameManager.reset();
            long lastUpdate = 0;

            while (!gameManager.isGameOver()) {
                if (!pause) {
                    lastUpdate = System.currentTimeMillis();
                    gameView.render(gameManager.getPositionables());
                    gameView.updatePowerUpTime(gameManager.getActivePowerUps());
                    gameView.updateCoinCount(parameters.getCoinCount());
                    gameManager.update(System.nanoTime() - lastUpdate);

                    if (!queue.isEmpty()) {
                        gameManager.movePlayer(queue.poll());
                    }
                }
                else {
                    lastUpdate = System.currentTimeMillis();
                }
            }
        }
    }
}
