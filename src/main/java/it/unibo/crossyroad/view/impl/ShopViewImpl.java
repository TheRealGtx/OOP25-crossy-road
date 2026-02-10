package it.unibo.crossyroad.view.impl;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import it.unibo.crossyroad.controller.api.ShopController;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.view.api.ShopView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Implementation of ShopView interface.
 */
public class ShopViewImpl implements ShopView {

    private static final double TITLE_FONT_RATIO = 0.04;
    private static final double HEADER_FONT_RATIO = 0.025;
    private static final double NORMAL_FONT_RATIO = 0.018;
    private static final double SMALL_FONT_RATIO = 0.013;

    private static final double MIN_TITLE_FONT_SIZE = 20.0;
    private static final double MIN_HEADER_FONT_SIZE = 15.0;
    private static final double MIN_NORMAL_FONT_SIZE = 10.0;
    private static final double MIN_SMALL_FONT_SIZE = 8.0;

    private static final double LARGE_SPACING_RATIO = 0.016;
    private static final double MEDIUM_SPACING_RATIO = 0.008;
    private static final double FLOW_SPACING_RATIO = 0.012;

    private static final double LARGE_SPACING = 10.0;
    private static final double MEDIUM_SPACING = 5.0;
    private static final double MIN_FLOW_SPACING = 8.0;

    private static final double SKIN_BOX_WIDTH_RATIO = 0.15;
    private static final double IMAGE_BOX_RATIO = 0.12;

    private static final double SKIN_BOX_WIDTH = 100.0;
    private static final double IMAGE_BOX_SIZE = 200.0;

    private static final double MAIN_PADDING_RATIO = 0.016;
    private static final double BOX_PADDING_RATIO = 0.012;
    private static final double BUTTON_PADDING_RATIO = 0.008;
    private static final double MIN_PADDING = 5.0;

    private static final double BORDER_RADIUS = 5.0;
    private static final double ACTIVE_BORDER_WIDTH = 2.0;
    private static final double NORMAL_BORDER_WIDTH = 1.0;
    private static final double OPACITY = 0.3;

    private static final Color WHITE = Color.WHITE;
    private static final Color RED = Color.RED;
    private static final Color GREEN = Color.GREEN;
    private static final Color BLUE = Color.BLUE;
    private static final Color GOLD = Color.GOLD;
    private static final Color GRAY = Color.GRAY;
    private static final Color ORANGE = Color.ORANGE;
    private static final Color BACKGROUND_COLOR = Color.DARKOLIVEGREEN;

    private final StackPane root;
    private final Pane shopPane;
    private ShopController shopController;
    private Label coinLabel;
    private FlowPane skinContainer;

    /**
     * ShopViewImpl constructor.
     * 
     * @param root the application's root StackPane.
     */
    public ShopViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "Root cannot be null");
        this.shopPane = new StackPane();
        this.createShop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final ShopController mainShopController) {
        this.shopController = Objects.requireNonNull(mainShopController, "Controller cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void hide() {
        this.shopPane.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        this.updateShop();
        this.shopPane.setVisible(true);
    }

    /**
     * Show the main menu view.
     */
    private void showMenu() {
        this.shopController.showMenu();
    }

    /**
     * Create the main structure of the shop view.
     */
    private void createShop() {
        final VBox mainBox = this.createMainBox();
        this.shopPane.getChildren().add(mainBox);
        this.shopPane.setVisible(false);
        this.root.getChildren().add(this.shopPane);
    }

    /**
     * Bind the font of a Labeled element to the width of the window. 
     * The font scales proportionally while mantaining a minimum size.
     * 
     * @param node element from wich to bind the font.
     * @param family font family.
     * @param weight font weight.
     * @param size minimim font size.
     * @param ratio ratio to window width.
     */
    private void bindFont(final Labeled node,
                    final String family,
                    final FontWeight weight,
                    final double size,
                    final double ratio) {
        node.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font(family, weight, Math.max(size, w.doubleValue() * ratio))
        ));
    }

    /**
     * Bind a VBox's spacing to the window width.
     * 
     * @param box the Vbox from which to bind the spacing.
     * @param space minimal spacing.
     * @param ratio ratio to window width.
     */
    private void bindSpacing(final VBox box, final double space, final double ratio) {
        box.spacingProperty().bind(this.root.widthProperty().map(w ->
            Math.max(space, w.doubleValue() * ratio)
        ));
    }

    /**
     * Bind a Region's padding to the window width.
     * 
     * @param region the region from which to bind the padding.
     * @param padding the minimal padding.
     * @param ratio the ratio to window width.
     */
    private void bindPadding(final Region region, final double padding, final double ratio) {
        region.paddingProperty().bind(this.root.widthProperty().map(w ->
            new Insets(Math.max(padding, w.doubleValue() * ratio))
        ));
    }

    /**
     * Bind the width and height of a Region.
     * 
     * @param region the region to be sized.
     * @param size the minimum size.
     * @param ratio the ratio to window width.
     */
    private void bindSize(final Region region, final double size, final double ratio) {
        final var sizeProperty = this.root.widthProperty().map(w -> 
            Math.max(size, w.doubleValue() * ratio)
        );
        region.prefWidthProperty().bind(sizeProperty);
        region.prefHeightProperty().bind(sizeProperty);
    }

    /**
     * Bind all size preorities (pref and max, width and height) for square elements.
     * 
     * @param region the region to be sized.
     * @param size the minimum size.
     * @param ratio the ratio to window width.
     */
    private void bindSquareSize(final Region region, final double size, final double ratio) {
        final var sizeProperty = this.root.widthProperty().map(w -> 
            Math.max(size, w.doubleValue() * ratio)
        );
        region.prefWidthProperty().bind(sizeProperty);
        region.prefHeightProperty().bind(sizeProperty);
        region.maxHeightProperty().bind(sizeProperty);
        region.maxWidthProperty().bind(sizeProperty);
    }

    /**
     * Bind the fit dimensions of an ImageView to scale images.
     * 
     * @param imageView the image view to scale.
     * @param size the minimum size.
     * @param ratio the ratio to window width.
     */
    private void bindImageSize(final ImageView imageView, final double size, final double ratio) {
        imageView.fitWidthProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(size, w.doubleValue() * ratio)
        ));
        imageView.fitHeightProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(size, w.doubleValue() * ratio)
        ));
    }

    /**
     * Binding the horizontal and vertical gaps of a FlowPane.
     * 
     * @param flowPane the flowpane from wich to bind the gaps.
     * @param space the minimum gap.
     * @param ratio the ratio to window width.
     */
    private void bindFlowPaneGaps(final FlowPane flowPane, final double space, final double ratio) {
        flowPane.hgapProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(space, w.doubleValue() * ratio)
        ));
        flowPane.vgapProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(space, w.doubleValue() * ratio)
        ));
    }

    /**
     * Create the main shop box containing header, skin grid and back button.
     * 
     * @return the main VBox of the shop view.
     */
    private VBox createMainBox() {
        final VBox mainBox = new VBox();
        this.bindSpacing(mainBox, LARGE_SPACING, LARGE_SPACING_RATIO);
        this.bindPadding(mainBox, MEDIUM_SPACING_RATIO, MEDIUM_SPACING_RATIO);
        mainBox.paddingProperty().bind(this.root.widthProperty().map(w ->
            new Insets(Math.max(MIN_PADDING, w.doubleValue() * MAIN_PADDING_RATIO))
        ));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        mainBox.maxWidthProperty().bind(this.root.widthProperty());
        mainBox.maxHeightProperty().bind(this.root.heightProperty());
        mainBox.getChildren().addAll(
            this.createHeader(),
            this.createScrollPane(),
            this.createBackButton()
        );
        return mainBox;
    }

    /**
     * Create shop header with title and coint counter.
     * 
     * @return the header VBox.
     */
    private VBox createHeader() {
        final VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        this.bindSpacing(header, MEDIUM_SPACING_RATIO, MEDIUM_SPACING_RATIO);
        final Label title = new Label("SKIN SHOP");
        this.bindFont(title, null, FontWeight.BOLD, MIN_TITLE_FONT_SIZE, TITLE_FONT_RATIO);
        title.setTextFill(WHITE);
        this.coinLabel = new Label("COINS: ");
        this.bindFont(this.coinLabel, null, FontWeight.BOLD, MIN_HEADER_FONT_SIZE, HEADER_FONT_RATIO);
        this.coinLabel.setTextFill(GOLD);
        header.getChildren().addAll(title, this.coinLabel);
        return header;
    }

    /**
     * Create the scroll pane containing the skin grid.
     * 
     * @return the configured scroll pane.
     */
    private ScrollPane createScrollPane() {
        this.skinContainer = new FlowPane();
        this.skinContainer.setAlignment(Pos.CENTER);
        this.bindFlowPaneGaps(this.skinContainer, MIN_FLOW_SPACING, FLOW_SPACING_RATIO);
        this.skinContainer.paddingProperty().bind(this.root.widthProperty().map(w -> 
            new Insets(Math.max(MIN_PADDING, w.doubleValue() * MEDIUM_SPACING_RATIO), 0,
                    Math.max(MIN_PADDING, w.doubleValue() * MEDIUM_SPACING_RATIO), 0)
        ));
        final ScrollPane scrollPane = new ScrollPane(this.skinContainer);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return scrollPane;
    }

    /**
     * Create the button to return to the main menu.
     * 
     * @return the configured back button.
     */
    private Button createBackButton() {
        final Button backButton = new Button("BACK TO MENU");
        backButton.paddingProperty().bind(this.root.widthProperty().map(w -> {
            final double padding = Math.max(MIN_PADDING, w.doubleValue() * BUTTON_PADDING_RATIO);
            return new Insets(padding, padding, padding, padding);
        }));
        this.setStyleButton(backButton, ORANGE);
        backButton.setOnAction(e -> {
            this.showMenu();
            this.hide();
        });
        return backButton;
    }

    /**
     * Update all data displayed int the shop view.
     */
    private void updateShop() {
        this.updateCoinLabel();
        this.updateSkinContainer();
    }

    /**
     * Update the label showing the current coin balance.
     */
    private void updateCoinLabel() {
        this.coinLabel.setText("COINS: " + this.shopController.getCoinCount());
    }

    /**
     * Refresh the skin container by realoading all skins from the controller. Skins are sorted by price.
     */
    private void updateSkinContainer() {
        this.skinContainer.getChildren().clear();
        this.shopController.getAllSkins().stream()
            .sorted(Comparator.comparingInt(Skin::getPrice))
            .map(this::createSkinBoxForSkin)
            .forEach(this.skinContainer.getChildren()::add);
    }

    /**
     * Wrapper to create a skin box by passing the skin.
     * 
     * @param skin the skin to display.
     * @return the skin card vbox.
     */
    private VBox createSkinBoxForSkin(final Skin skin) {
        return this.createSkinBox(
            skin,
            this.isUnlocked(skin),
            this.isActive(skin)
        );
    }

    /**
     * Check if a skin has been unlocked.
     * 
     * @param skin the skin to check.
     * @return true if unlocked, false otherwise.
     */
    private boolean isUnlocked(final Skin skin) {
        return this.shopController.getUnlockedSkins().contains(skin);
    }

    /**
     * Check if a skin is currently active.
     * 
     * @param skin the skin to check.
     * @return true if active, false otherwise.
     */
    private boolean isActive(final Skin skin) {
        return this.shopController.getActiveSkin().equals(skin);
    }

    /**
     * Create a card for a single skin.
     * 
     * @param skin the skin to display.
     * @param isUnlocked if the skin is unlocked.
     * @param isActive if the skin is equipped.
     * @return the skin card vbox.
     */
    private VBox createSkinBox(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        final VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        this.bindSpacing(box, MEDIUM_SPACING, MEDIUM_SPACING_RATIO);
        this.bindPadding(box, MIN_PADDING, BOX_PADDING_RATIO);
        this.bindSize(box, SKIN_BOX_WIDTH, SKIN_BOX_WIDTH_RATIO);
        this.applySkinBoxStyle(box, isActive);
        box.getChildren().addAll(
            this.createImageBox(skin, isUnlocked),
            this.createInfoSkinBox(skin, isUnlocked, isActive),
            this.createActionElement(skin, isUnlocked, isActive)
        );
        return box;
    }

    /**
     * Apply a visual style to the skin box.
     * 
     * @param box the vbox to style.
     * @param isActive if the skin is active.
     */
    private void applySkinBoxStyle(final VBox box, final boolean isActive) {
        if (isActive) {
            box.setBorder(this.createBorder(BACKGROUND_COLOR, ACTIVE_BORDER_WIDTH));
            box.setBackground(this.createBackground(WHITE));
        } else {
            box.setBorder(this.createBorder(WHITE, NORMAL_BORDER_WIDTH));
        }
    }

    /**
     * Creates a background with the specified color.
     * 
     * @param color the background color.
     * @return the background object.
     */
    private Background createBackground(final Color color) {
        return new Background(new BackgroundFill(
            color,
            this.createCornerRadii(),
            Insets.EMPTY
        ));
    }

    /**
     * Creates a border with the specified color and width.
     * 
     * @param color the border color.
     * @param width the border width.
     * @return the border object.
     */
    private Border createBorder(final Color color, final double width) {
        return new Border(new BorderStroke(
            color,
            BorderStrokeStyle.SOLID,
            this.createCornerRadii(),
            new BorderWidths(width)
        ));
    }

    /**
     * Create a corner radii with the predefined border radius.
     * 
     * @return the corner radii object.
     */
    private CornerRadii createCornerRadii() {
        return new CornerRadii(BORDER_RADIUS);
    }

    /**
     * Create the container for the skin manager.
     * 
     * @param skin the skin to display.
     * @param isUnlocked if the skin is unlocked.
     * @return the stackpane containing the skin image.
     */
    private StackPane createImageBox(final Skin skin, final boolean isUnlocked) {
        final StackPane imageBox = new StackPane();
        this.bindSquareSize(imageBox, IMAGE_BOX_SIZE, IMAGE_BOX_RATIO);
        imageBox.setBackground(this.createBackground(WHITE));
        imageBox.setBorder(this.createBorder(GRAY, NORMAL_BORDER_WIDTH));
        this.loadSkinImage(imageBox, skin, isUnlocked);
        return imageBox;
    }

    /**
     * Loads and displays a skin image in the container. 
     * if the skin is locked, the image is displayed with a lock overlay and reduced opacity.
     * 
     * @param imageBox the container in wich to display the iamge.
     * @param skin the skin to display.
     * @param isUnlocked if the skin is unlocked or not.
     */
    private void loadSkinImage(final StackPane imageBox, final Skin skin, final boolean isUnlocked) {
        final String imagePath = "/skins/" + skin.getId() + "_front.png";
        this.loadImage(imagePath).ifPresentOrElse(
            image -> {
                final ImageView imageView = this.createImageView(image, isUnlocked);
                if (!isUnlocked) {
                    imageBox.getChildren().addAll(imageView, this.createLockLabel());
                } else {
                    imageBox.getChildren().add(imageView);
                }
            },
            () -> imageBox.getChildren().add(this.createPlaceholderLabel())
        );
    }

    /**
     * load an image from the resources folder.
     * 
     * @param path the path to the image resource.
     * @return the optional containing the loaded image or empty.
     */
    private Optional<Image> loadImage(final String path) {
        return Optional
            .ofNullable(ShopViewImpl.class.getResource(path))
            .map(resource -> new Image(resource.toExternalForm()));
    }

    /**
     * Crate an image view for a skin image.
     * 
     * @param image the image to display.
     * @param isUnlocked if the skin is unlocked.
     * @return the configured image view.
     */
    private ImageView createImageView(final Image image, final boolean isUnlocked) {
        final ImageView imageView = new ImageView(image);
        this.bindImageSize(imageView, IMAGE_BOX_SIZE, IMAGE_BOX_RATIO);
        imageView.setPreserveRatio(true);
        if (!isUnlocked) {
            imageView.setOpacity(OPACITY);
        }
        return imageView;
    }

    /**
     * Create a lock icon label for locked skins.
     * 
     * @return the label with lock icon.
     */
    private Label createLockLabel() {
        final Label lockLabel = new Label("🔒");
        this.bindFont(lockLabel, null, FontWeight.BOLD, MIN_HEADER_FONT_SIZE, HEADER_FONT_RATIO);
        lockLabel.setTextFill(GRAY);
        return lockLabel;
    }

    /**
     * Create a placeholed label when the image cannot be loaded.
     * 
     * @return the label with placeholder text.
     */
    private Label createPlaceholderLabel() {
        final Label placeholder = new Label("?");
        placeholder.setTextFill(Color.GRAY);
        return placeholder;
    }

    /**
     * Creates the information box containing the skin's name and status.
     * 
     * @param skin the skin to display.
     * @param isUnlocked if the skin is unlocked.
     * @param isActive if the skin is active.
     * @return the vbox containing the skin information.
     */
    private VBox createInfoSkinBox(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        final VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        final Label statusLabel = new Label(this.getStatusText(isUnlocked, isActive));
        this.bindFont(statusLabel, "Verdana", FontWeight.BOLD, MIN_NORMAL_FONT_SIZE, NORMAL_FONT_RATIO);
        statusLabel.setTextFill(this.getStatusColor(isUnlocked, isActive));
        final Label nameLabel = new Label(skin.getName());
        this.bindFont(nameLabel, "Arial", FontWeight.BOLD, MIN_SMALL_FONT_SIZE, SMALL_FONT_RATIO);
        infoBox.getChildren().addAll(nameLabel, statusLabel);
        return infoBox;
    }

    /**
     * Determine the status text to display based on the skin's state.
     * 
     * @param isUnlocked if the skin is unlocked.
     * @param isActive if the skin is active.
     * @return the status text.
     */
    private String getStatusText(final boolean isUnlocked, final boolean isActive) {
        if (isActive) {
            return "Equipped";
        }
        return isUnlocked ? "Unlocked" : "Locked";
    }

    /**
     * Determines the text color of the status text.
     * 
     * @param isUnlocked if the skin is unlocked.
     * @param isActive if the skin is active.
     * @return the status text color.
     */
    private Color getStatusColor(final boolean isUnlocked, final boolean isActive) {
        if (isActive) {
            return BLUE;
        }
        return isUnlocked ? GREEN : RED;
    }

    /**
     * Creates the appropriate action element based on the skin's state.
     * 
     * @param skin the skin.
     * @param isUnlocked if the skin is unlocked.
     * @param isActive if the skin is active.
     * @return the appropriate node.
     */
    private Node createActionElement(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        if (isActive) {
            return this.createActiveLabel();
        } else if (isUnlocked) {
            return this.createEquipButton(skin);
        } else {
            return this.createUnlockButton(skin);
        }
    }

    /**
     * Creates the active label for already equipped skin.
     * 
     * @return the active label.
     */
    private Label createActiveLabel() {
        final Label activeLabel = new Label("ACTIVE");
        activeLabel.setTextFill(BLUE);
        return activeLabel;
    }

    /**
     * Creates the equip button to equip an unlocked skin.
     * 
     * @param skin the skin to equip.
     * @return the equip button.
     */
    private Button createEquipButton(final Skin skin) {
        final Button equipButton = new Button("Equip");
        equipButton.setOnAction(e -> {
            this.shopController.activateSkin(skin);
            this.updateShop();
        });
        this.setStyleButton(equipButton, BLUE);
        return equipButton;
    }

    /**
     * Creates a unlock button to unlock a locked skin.
     * The button is disabled if the player doesn't have enough coins.
     * 
     * @param skin the skin to unlock.
     * @return the unlock button.
     */
    private Button createUnlockButton(final Skin skin) {
        final Button unlockButton = new Button("Buy: " + skin.getPrice() + " Coins");
        unlockButton.setOnAction(e -> {
            if (this.shopController.tryUnlockSkin(skin)) {
                this.updateShop();
            }
        });
        unlockButton.setDisable(this.shopController.getCoinCount() < skin.getPrice());
        this.setStyleButton(unlockButton, GREEN);
        return unlockButton;
    }

    /**
     * Apply the style to a button.
     * 
     * @param button the button to style.
     * @param color the background color of the button.
     */
    private void setStyleButton(final Button button, final Color color) {
        this.bindFont(button, "Arial", FontWeight.BOLD, MIN_SMALL_FONT_SIZE, SMALL_FONT_RATIO);
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(
            color,
            new CornerRadii(BORDER_RADIUS),
            Insets.EMPTY
        )));
    }
}
