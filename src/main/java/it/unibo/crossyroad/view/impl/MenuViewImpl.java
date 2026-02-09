package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.view.api.MenuView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * An implementation of the MenuView interface, that allows the user to interact with the menuPane of the game.
 */
public final class MenuViewImpl implements MenuView {
    private static final String TITLE = "Crossy Road";
    private static final double TITLE_FONT_SIZE = 50.0;

    private static final String DEFAULT_SKIN_IMAGE_PATH = "/skins/default_front.png";
    private static final double SKIN_IMAGE_SIZE = 200.0;
    private static final double SHOW_IMAGE_THRESHOLD = 500.0;

    private static final double BUTTON_FONT_SIZE = 20.0;
    private static final double BUTTON_SPACING = 10.0;
    private static final double BUTTON_WIDTH = 300.0;
    private static final double BUTTON_HEIGHT = 60.0;
    private static final double CORNER_RADIUS = 10.0;
    private static final double BORDER_WIDTH = 2.0;
    private static final Color TEXT_COLOR = Color.WHITE;

    private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), "crossyroad");
    private static final Logger LOGGER = Logger.getLogger(MenuViewImpl.class.getName());

    private final StackPane root;
    private final Pane menuPane;
    private final ImageView skinImage = new ImageView();
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
        this.skinImage.setImage(this.getSkinImage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.skinImage.setImage(this.getSkinImage());
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
        final VBox menu = new VBox(BUTTON_SPACING * 2);
        menu.setAlignment(Pos.CENTER);

        final Background background = new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, null));
        final Label title = this.createTitle();
        final VBox menuItems = this.createMenuItems();

        this.skinImage.setFitWidth(SKIN_IMAGE_SIZE);
        this.skinImage.setFitHeight(SKIN_IMAGE_SIZE);
        if (this.root.getWidth() < SHOW_IMAGE_THRESHOLD) {
            this.skinImage.setVisible(false);
            this.skinImage.setManaged(false);
        }

        menu.setBackground(background);
        menu.getChildren().addAll(title, this.skinImage, menuItems);
        return menu;
    }

    private Label createTitle() {
        final Label title = new Label(TITLE);
        title.setFont(Font.font(null, FontWeight.BOLD, TITLE_FONT_SIZE));
        title.setTextFill(TEXT_COLOR);
        return title;
    }

    private Image getSkinImage() {
        final String defaultImagePath = Objects.requireNonNull(getClass().getResource(DEFAULT_SKIN_IMAGE_PATH)).toExternalForm();
        if (Objects.isNull(this.controller)) {
            LOGGER.warning("Controller is not set, using default skin image");
            return new Image(defaultImagePath);
        }

        final var skin = this.controller.getActiveSkin();
        final var imagePath = skin.getFrontImage().toString().replace("\\", "/");

        final URL resource = getClass().getResource(imagePath);
        if (Objects.isNull(resource)) {
            LOGGER.warning("Skin image not found, using default skin image");
            return new Image(defaultImagePath);
        }
        return new Image(resource.toExternalForm());
    }

    private VBox createMenuItems() {
        final VBox menuItems = new VBox(BUTTON_SPACING);
        menuItems.setAlignment(Pos.CENTER);
        menuItems.getChildren().addAll(this.createButtons());
        return menuItems;
    }

    private List<Button> createButtons() {
        return List.of(
            createButton("PLAY", Color.GREEN, e -> {
                if (!Objects.isNull(this.controller)) {
                    this.controller.showGame();
                }
            }),
            createButton("SHOP", Color.ORANGE, e -> {
                if (!Objects.isNull(this.controller)) {
                    this.controller.showShop();
                }
            }),
            createButton("EXIT", Color.CRIMSON, e -> {
                if (!Objects.isNull(this.controller)) {
                    // todo: remove try-catch
                    try {
                        this.controller.save();
                    } catch (final IOException ex) {
                        LOGGER.severe("Failed to save game state");
                    }
                }
                Platform.exit();
            })
        );
    }

    private Button createButton(final String text, final Color bgColor, final EventHandler<ActionEvent> handler) {
        final Button button = new Button(text);
        button.setOnAction(handler);
        button.setPrefWidth(BUTTON_WIDTH);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setFont(Font.font(null, FontWeight.BOLD, BUTTON_FONT_SIZE));
        button.setTextFill(TEXT_COLOR);

        final Background background = new Background(new BackgroundFill(bgColor, new CornerRadii(CORNER_RADIUS), null));
        button.setBackground(background);

        final Border buttonBorder = new Border(
            new BorderStroke(TEXT_COLOR, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADIUS), new BorderWidths(BORDER_WIDTH))
        );
        button.setBorder(buttonBorder);

        return button;
    }
}
