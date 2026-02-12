package it.unibo.crossyroad;

import it.unibo.crossyroad.controller.api.AppController;
import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.controller.api.ShopController;
import it.unibo.crossyroad.controller.impl.AppControllerImpl;
import it.unibo.crossyroad.controller.impl.GameControllerImpl;
import it.unibo.crossyroad.controller.impl.MenuControllerImpl;
import it.unibo.crossyroad.controller.impl.ShopControllerImpl;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.SkinManager;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.SkinManagerImpl;
import it.unibo.crossyroad.model.impl.StateManagerImpl;
import it.unibo.crossyroad.view.api.GameView;
import it.unibo.crossyroad.view.api.MenuView;
import it.unibo.crossyroad.view.api.ShopView;
import it.unibo.crossyroad.view.impl.GameViewImpl;
import it.unibo.crossyroad.view.impl.MenuViewImpl;
import it.unibo.crossyroad.view.impl.ShopViewImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Entry point of the application. It initializes the MVC components and starts the JavaFX application.
 */
public class EntryPoint extends Application {
    private static final String TITLE = "Crossy Road";
    private static final double WIDTH = 10;
    private static final double HEIGHT = 9;
    private static final double ASPECT_RATIO = WIDTH / HEIGHT;
    private static final double SCALE = 0.9;
    private static final int INITIAL_BALANCE = 200;

    private GameParameters gameParameters;
    private StateManager stateManager;

    /**
     * It initializes the MVC components.
     *
     * @throws Exception if any error occurs.
     */
    @Override
    public void init() throws Exception {
        this.gameParameters = new GameParametersImpl();
        gameParameters.setCoinCount(INITIAL_BALANCE);
        final SkinManager skinManager = new SkinManagerImpl();
        skinManager.loadFromResources();
        this.stateManager = new StateManagerImpl(gameParameters, skinManager);
    }

    /**
     * It links the MVC components and starts the JavaFX application.
     *
     * @param stage Stage of the application.
     */
    @Override
    public void start(final Stage stage) {
        final StackPane root = new StackPane();
        final Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        final Scene scene = new Scene(root, screenBounds.getHeight() * ASPECT_RATIO * SCALE, screenBounds.getHeight() * SCALE);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.getIcons().add(new Image("skins/default_front.png"));

        final MenuView menuView = new MenuViewImpl(root);
        final GameView gameView = new GameViewImpl(root);
        final ShopView shopView = new ShopViewImpl(root);

        final AppController appController = new AppControllerImpl(
            ac -> new GameControllerImpl(ac, gameView, this.gameParameters),
            ac -> new MenuControllerImpl(ac, menuView, this.stateManager),
            ac -> new ShopControllerImpl(ac, this.stateManager, shopView)
        );

        final GameController gameController = appController.getGameController();
        final MenuController menuController = appController.getMenuController();
        final ShopController shopController = appController.getShopController();

        menuController.load();
        gameView.setController(gameController);
        menuView.setController(menuController);
        shopView.setController(shopController);
        appController.showMenu();
        stage.setOnCloseRequest(e -> {
            gameController.endGame();
            menuController.save();
            Platform.exit();
        });
    }
}
