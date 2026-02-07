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
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Entry point of the application. It initializes the MVC components and starts the JavaFX application.
 */
public class EntryPoint extends Application {
    private static final double WIDTH = 1000;
    private static final double HEIGHT = 900;

    /**
     * It initializes the MVC components.
     *
     * @throws Exception if any error occurs.
     */
    @Override
    public void init() throws Exception {
        // todo: Create instances and assign them to private fields
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
        final Scene scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle("Crossy Road");
        stage.setScene(scene);
        stage.show();

        final GameParameters gameParameters = new GameParametersImpl();
        final SkinManager skinManager = new SkinManagerImpl();
        final MenuView menuView = new MenuViewImpl(root);
        final GameView gameView = new GameViewImpl(root);
        final StateManager stateManager = new StateManagerImpl(gameParameters, skinManager);

        final AppController appController = new AppControllerImpl();
        final GameController gameController = new GameControllerImpl(appController, gameView);
        final MenuController menuController = new MenuControllerImpl(appController, menuView, stateManager);

        stage.setOnCloseRequest(e -> {
            gameController.endGame();
            Platform.exit();
        });

        appController.setGameController(gameController);
        appController.setMenuController(menuController);

        gameView.setController(gameController);
        menuView.setController(menuController);
        gameController.startLoop();
        appController.showMenu();
    }
}
