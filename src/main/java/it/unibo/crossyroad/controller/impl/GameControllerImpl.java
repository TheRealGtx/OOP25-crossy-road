package it.unibo.crossyroad.controller.impl;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import it.unibo.crossyroad.EntryPoint;
import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameManagerImpl;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.view.api.GameView;
import it.unibo.crossyroad.view.api.UserInput;

/**
 * Implementation of the GameController.
 *
 * @see GameController
 */
public final class GameControllerImpl implements GameController {

    private final AppController appController;
    private final GameView gameView;
    private volatile boolean pause;
    private final GameManager gameManager;
    private final GameParameters parameters;
    private final Loop loop;
    private final Queue<Direction> queue;

    /**
     * Initializes the Game controller.
     * 
     * @param appController the app controller.
     * 
     * @param gameView the game view.
     * 
     * @see Appcontroller
     * 
     * @see GameView
     */
    public GameControllerImpl(final AppController appController, final GameView gameView) {
        this.appController = appController;
        this.gameView = gameView;
        this.pause = false;

        this.parameters = new GameParametersImpl();
        this.gameManager = new GameManagerImpl(this.parameters);
        this.loop = new Loop();
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void showGame() {
        this.gameView.show();
    }

    @Override
    public void hideGame() {
        this.gameView.hide();
    }

    @Override
    public void startLoop() {
        this.loop.start();
    }

    @Override
    public void pauseGame() {
        this.pause = true;
        this.appController.showMenu();
        this.hideGame();
    }

    @Override
    public void resumeGame() {
        this.pause = false;
        this.showGame();
        this.appController.showGame();
    }

    @Override
    public void processInput(final UserInput input) {
        switch (input) {
            case UP:
                this.queue.add(Direction.UP);
                break;
            case DOWN:
                this.queue.add(Direction.DOWN);
                break;
            case LEFT:
                this.queue.add(Direction.LEFT);
                break;
            case RIGHT:
                this.queue.add(Direction.RIGHT);
                break;
        }
    }

    @Override
    public void endGame() {
        this.gameManager.endGame();
    }

    // private String getActiveSkin() {
    //     return this.appController.getActiveSkinId();
    // }

    private int getCoinCount() {
        return this.appController.getCoinCount();
    }

    private final class Loop extends Thread {
        private static final Logger LOGGER = Logger.getLogger(EntryPoint.class.getName());

        @Override
        public void run() {
            gameManager.reset();
            long lastUpdate = 0;
            long currentTime;
            long deltaTime;

            while (!gameManager.isGameOver()) {
                if (!pause) {
                    currentTime = System.currentTimeMillis();
                    deltaTime = currentTime - lastUpdate;
                    lastUpdate = currentTime;
                    gameView.render(gameManager.getPositionables());
                    gameView.updatePowerUpTime(gameManager.getActivePowerUps());
                    gameView.updateCoinCount(getCoinCount());
                    gameManager.update(deltaTime);

                    if (!queue.isEmpty()) {
                        gameManager.movePlayer(queue.poll());
                    }
                } else {
                    lastUpdate = System.currentTimeMillis();
                }
                try {
                    sleep(10);
                } catch (final InterruptedException e) {
                    LOGGER.severe("Thread.sleep() error");
                }
            }

            //TODO togliere
            gameManager.reset();
            appController.showMenu();
        }
    }
}
