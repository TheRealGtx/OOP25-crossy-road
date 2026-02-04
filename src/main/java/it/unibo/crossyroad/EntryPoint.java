package it.unibo.crossyroad;

import it.unibo.crossyroad.view.api.MenuView;
import it.unibo.crossyroad.view.impl.MenuViewImpl;
import javafx.application.Application;
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

        final MenuView menuView = new MenuViewImpl(root);
        menuView.show(); // todo: it will be menuController
    }
}
