package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.view.api.MenuView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Objects;

/**
 * An implementation of the MenuView interface, that allows the user to interact with the menuPane of the game.
 */
public class MenuViewImpl implements MenuView {
    private static final double BACKGROUND_OPACITY = 0.3;
    private static final double BUTTON_SPACING = 10.0;
    private static final double MENU_RADIUS = 10.0;
    private static final double MENU_PADDING = 10.0;

    private final StackPane root;
    private final Pane menuPane;
    private MenuController controller;

    /**
     * Constructor for MenuViewImpl.
     *
     * @param root The root StackPane of the application (parent node of all views)
     */
    public MenuViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "root cannot be null");
        this.menuPane = this.createMenu();
        this.root.getChildren().add(this.menuPane);
        this.menuPane.managedProperty().bind(this.menuPane.visibleProperty());
        this.menuPane.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final MenuController controller) {
        this.controller = Objects.requireNonNull(controller, "controller cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.menuPane.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        this.menuPane.setVisible(false);
    }

    private Pane createMenu() {
        final StackPane menu = new StackPane();
        final Rectangle background = this.createTransparentBackground();
        final VBox menuItems = this.createMenuItems();

        menu.getChildren().addAll(background, menuItems);
        return menu;
    }

    private Rectangle createTransparentBackground() {
        final Rectangle background = new Rectangle();
        background.widthProperty().bind(this.root.widthProperty());
        background.heightProperty().bind(this.root.heightProperty());
        background.setFill(Color.rgb(0, 0, 0, BACKGROUND_OPACITY));
        return background;
    }

    private VBox createMenuItems() {
        final VBox menuItems = new VBox(BUTTON_SPACING);
        menuItems.maxWidthProperty().bind(this.root.widthProperty().divide(2));
        menuItems.maxHeightProperty().bind(this.root.heightProperty().divide(2));
        menuItems.setAlignment(Pos.CENTER);
        menuItems.setBackground(this.createMenuBackground());
        menuItems.getChildren().addAll(this.createButtons());
        menuItems.setFillWidth(true);
        return menuItems;
    }

    private Background createMenuBackground() {
        return new Background(
            new BackgroundFill(
                Color.WHITE,
                new CornerRadii(MENU_RADIUS),
                new Insets(MENU_PADDING)
            )
        );
    }

    private List<Button> createButtons() {
        final List<Button> buttons = List.of(
            createButton("Play", e -> {
                this.controller.showGame();
            }),
            createButton("Shop", e -> {
                this.controller.showGame();
            }),
            createButton("Load", e -> {
                //this.controller.load();
            }),
            createButton("Save", e -> {
                //this.controller.save();
            }),
            createButton("Save & exit", e -> {
                //this.controller.save();
                Platform.exit();
            })
        );

        this.adjustButtonSizes(buttons);
        return buttons;
    }

    private Button createButton(final String text, final EventHandler<ActionEvent> handler) {
        final Button button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    /**
     * Sets the widths of all buttons to the width of the largest button.
     *
     * @param buttons the list of buttons to adjust
     */
    private void adjustButtonSizes(final List<Button> buttons) {
        Platform.runLater(() -> {
            buttons.forEach(Node::applyCss);
            Platform.runLater(() -> {
                final double maxWidth = buttons.stream()
                    .mapToDouble(Region::getWidth)
                    .max()
                    .orElse(0);
                buttons.forEach(b -> {
                    b.setPrefWidth(maxWidth);
                    b.setMinWidth(Region.USE_PREF_SIZE);
                });
            });
        });
    }
}
