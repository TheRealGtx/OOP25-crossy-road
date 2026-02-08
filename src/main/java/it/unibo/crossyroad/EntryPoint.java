package it.unibo.crossyroad;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.controller.impl.AppControllerImpl;
import it.unibo.crossyroad.controller.impl.GameControllerImpl;
import it.unibo.crossyroad.controller.impl.MenuControllerImpl;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.SkinManager;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.SkinManagerImpl;
import it.unibo.crossyroad.model.impl.StateManagerImpl;
import it.unibo.crossyroad.view.api.GameView;
import it.unibo.crossyroad.view.api.MenuView;
import it.unibo.crossyroad.view.impl.GameViewImpl;
import it.unibo.crossyroad.view.impl.MenuViewImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Entry point of the application. It initializes the MVC components and starts the JavaFX application.
 */
public class EntryPoint extends Application {
    private static final double WIDTH = 10;
    private static final double HEIGHT = 9;
    private static final double ASPECT_RATIO = WIDTH / HEIGHT;
    private static final String TITLE = "Crossy Road";
    private static final double SCALE = 0.9;
    private static final Logger LOGGER = Logger.getLogger(EntryPoint.class.getName());
    private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "crossyroad");

    private StateManager stateManager;

    /**
     * It initializes the MVC components.
     *
     * @throws Exception if any error occurs.
     */
    @Override
    public void init() throws Exception {
        final GameParameters gameParameters = new GameParametersImpl();
        final SkinManager skinManager = new SkinManagerImpl();
        skinManager.loadFromResources();
        this.stateManager = new StateManagerImpl(gameParameters, skinManager);
    }

    /**
     * It links the MVC components and starts the JavaFX application.
     *
     * @param stage Stage of the application.
     * @throws Exception if any error occurs.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final StackPane root = new StackPane();
        final Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        final Scene scene = new Scene(root, screenBounds.getHeight() * ASPECT_RATIO * SCALE, screenBounds.getHeight() * SCALE);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

        final MenuView menuView = new MenuViewImpl(root);
        final GameView gameView = new GameViewImpl(root);
        // todo: add shop view

        final AppController appController = new AppControllerImpl(
            ac -> new GameControllerImpl(ac, gameView),
            ac -> new MenuControllerImpl(ac, menuView, this.stateManager),
            ac -> null // todo
        );
        final GameController gameController = appController.getGameController();
        final MenuController menuController = appController.getMenuController();
        // todo: add shop controller

        gameView.setController(gameController);
        menuView.setController(menuController);
        appController.showMenu();

        this.loadSave(menuController);
        stage.setOnCloseRequest(e -> this.onClose(gameController, menuController));
    }

    private void loadSave(final MenuController menuController) {
        if (SAVE_PATH.toFile().exists()) {
            try {
                menuController.load(SAVE_PATH);
            } catch (final IOException e) {
                LOGGER.info("Failed to load past game state");
            }
        }
    }

    private void onClose(final GameController gameController, final MenuController menuController) {
        gameController.endGame();
        try {
            menuController.save(Paths.get(System.getProperty("user.home"), "crossyroad"));
        } catch (final IOException ex) {
            LOGGER.severe("Failed to save the game state");
        }
        Platform.exit();
    }
}
