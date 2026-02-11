package it.unibo.crossyroad.controller.impl;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameManagerImpl;
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
    private final Queue<Direction> queue;

    /**
     * Initializes the Game controller.
     * 
     * @param appController the app controller.
     * 
     * @param gameView the game view.
     * 
     * @param parameters the game parameters.
     * 
     * @see AppController
     * 
     * @see GameView
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "AppController and GameView references are intentionally shared. "
            + "These components need to interact with the same instances as per MVC pattern."
    )
    public GameControllerImpl(final AppController appController, final GameView gameView, final GameParameters parameters) {
        this.appController = appController;
        this.gameView = gameView;
        this.pause = false;
        this.parameters = parameters;
        this.gameManager = new GameManagerImpl(this.parameters);
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGame() {
        this.gameView.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideGame() {
        this.gameView.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startLoop() {
        new Loop().start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseGame() {
        this.pause = true;
        this.appController.showMenu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeGame() {
        this.pause = false;
        this.gameView.show();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void endGame() {
        this.gameManager.endGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActiveSkin() {
        return this.appController.getActiveSkin().getOverheadImage().toString();
    }

    private final class Loop extends Thread {
        private static final Logger LOGGER = Logger.getLogger(Loop.class.getName());

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
                    if (!queue.isEmpty()) {
                        gameManager.movePlayer(queue.poll());
                    }
                    gameManager.update(deltaTime);
                    gameView.render(gameManager.getPositionables());
                    gameView.updatePowerUpTime(gameManager.getActivePowerUps());
                    gameView.updateCoinCount(parameters.getCoinCount());
                    gameView.updateScore(parameters.getScore());
                } else {
                    lastUpdate = System.currentTimeMillis();
                }
                try {
                    sleep(10);
                } catch (final InterruptedException e) {
                    LOGGER.severe("Thread.sleep() error");
                }
            }

            gameManager.reset();
            appController.gameOver();
        }
    }
}
