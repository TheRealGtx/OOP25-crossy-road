package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.MenuController;
import it.unibo.crossyroad.view.api.MenuView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * An implementation of the MenuView interface, that allows the user to interact with the menuPane of the game.
 */
public final class MenuViewImpl implements MenuView {
    private static final String TITLE_IMAGE_PATH = "/assets/logo.png";
    private static final double TITLE_IMAGE_RATIO = 0.3;

    private static final String DEFAULT_SKIN_IMAGE_PATH = "/skins/default_front.png";
    private static final double SKIN_IMAGE_RATIO = 0.2;
    private static final double SHOW_SKIN_THRESHOLD = 500.0;

    private static final double BUTTON_WIDTH_RATIO = 0.4;
    private static final double BUTTON_HEIGHT_RATIO = 0.08;
    private static final double BUTTON_FONT_RATIO = 0.025;
    private static final double MIN_BUTTON_FONT_SIZE = 8.0;
    private static final double BUTTON_SPACING = 10.0;
    private static final double BORDER_WIDTH = 2.0;
    private static final double CORNER_RADIUS = 10.0;
    private static final Color TEXT_COLOR = Color.WHITE;

    private static final Logger LOGGER = Logger.getLogger(MenuViewImpl.class.getName());

    private final StackPane root;
    private final Pane menuPane;
    private final ImageView title = new ImageView();
    private final ImageView skinImageView = new ImageView();
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
        this.skinImageView.setImage(this.getSkinImage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.skinImageView.setImage(this.getSkinImage());
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

        this.initTitle();
        this.setupSkinImage();
        final VBox menuItems = this.initMenuItems();

        final Background background = new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, null));
        menu.setBackground(background);

        menu.getChildren().addAll(title, this.skinImageView, menuItems);
        return menu;
    }

    private void initTitle() {
        this.title.setAccessibleText("Crossy Road");
        final Image titleImage = new Image(TITLE_IMAGE_PATH);
        this.title.setImage(titleImage);
        this.title.setPreserveRatio(true);
        this.title.fitWidthProperty().bind(this.root.widthProperty().multiply(TITLE_IMAGE_RATIO));
    }

    private void setupSkinImage() {
        skinImageView.managedProperty().bind(skinImageView.visibleProperty());

        final var widthProperty = this.root.widthProperty();
        skinImageView.setPreserveRatio(true);
        skinImageView.fitWidthProperty().bind(widthProperty.multiply(SKIN_IMAGE_RATIO));
        skinImageView.visibleProperty().bind(widthProperty.greaterThan(SHOW_SKIN_THRESHOLD));
    }

    private Image getSkinImage() {
        final Image defaultImage = new Image(DEFAULT_SKIN_IMAGE_PATH);
        if (Objects.isNull(this.controller)) {
            LOGGER.warning("Controller is not set, using default skin image");
            return defaultImage;
        }

        final var skin = this.controller.getActiveSkin();
        final var imagePath = skin.getFrontImage().toString().replace("\\", "/");

        return new Image(imagePath);
    }

    private VBox initMenuItems() {
        final VBox menuItems = new VBox(BUTTON_SPACING);
        menuItems.setAlignment(Pos.CENTER);

        menuItems.getChildren().addAll(this.initButtons());
        return menuItems;
    }

    private List<Button> initButtons() {
        return List.of(
            initButton("PLAY", Color.GREEN, e -> {
                if (!Objects.isNull(this.controller)) {
                    this.controller.showGame();
                }
            }),
            initButton("SHOP", Color.ORANGE, e -> {
                if (!Objects.isNull(this.controller)) {
                    this.controller.showShop();
                }
            }),
            initButton("EXIT", Color.CRIMSON, e -> {
                if (!Objects.isNull(this.controller)) {
                    this.controller.save();
                }
                Platform.exit();
            })
        );
    }

    private Button initButton(final String text, final Color bgColor, final EventHandler<ActionEvent> handler) {
        final Button button = new Button(text);
        button.setOnAction(handler);
        button.setTextFill(TEXT_COLOR);

        final var widthProperty = this.root.widthProperty();
        button.prefWidthProperty().bind(widthProperty.multiply(BUTTON_WIDTH_RATIO));
        button.prefHeightProperty().bind(widthProperty.multiply(BUTTON_HEIGHT_RATIO));
        button.fontProperty().bind(widthProperty.map(w ->
            Font.font(null, FontWeight.BOLD, Math.max(MIN_BUTTON_FONT_SIZE, w.doubleValue() * BUTTON_FONT_RATIO))
        ));

        final Background background = new Background(new BackgroundFill(bgColor, new CornerRadii(CORNER_RADIUS), null));
        button.setBackground(background);
        final Border buttonBorder = new Border(
            new BorderStroke(TEXT_COLOR, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADIUS), new BorderWidths(BORDER_WIDTH))
        );
        button.setBorder(buttonBorder);

        return button;
    }
}