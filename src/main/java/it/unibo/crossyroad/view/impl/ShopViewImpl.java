package it.unibo.crossyroad.view.impl;

import java.util.Comparator;
import java.util.Objects;

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
public class ShopViewImpl implements ShopView{

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
    private static final double SMALL_SPACING_RATIO = 0.004;
    private static final double FLOW_SPACING_RATIO = 0.012;
    private static final double LARGE_SPACING = 10.0;
    private static final double MEDIUM_SPACING = 5.0;
    private static final double SMALL_SPACING = 3.0;
    private static final double MIN_FLOW_SPACING = 8.0;
    private static final double SKIN_BOX_WIDTH_RATIO = 0.15;
    private static final double IMAGE_BOX_RATIO = 0.12;
    private static final double IMAGE_VIEW_RATIO = 0.12;
    private static final double SKIN_BOX_WIDTH = 100.0;
    private static final double IMAGE_BOX_SIZE = 200.0;
    private static final double MAIN_PADDING_RATIO = 0.016;
    private static final double BOX_PADDING_RATIO = 0.012;
    private static final double BUTTON_PADDING_RATIO = 0.008;
    private static final double MAIN_PADDING = 50.0;
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
    

    public ShopViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "Root cannot be null");
        this.shopPane = new StackPane();
        this.createShop();
    }
    
    @Override
    public void setController(final ShopController shopController) {
        this.shopController = Objects.requireNonNull(shopController, "Controller cannot be null");        
    }

    @Override
    public void hide() {
        this.shopPane.setVisible(false);
    }
    
    @Override
    public void show() {
        this.updateShop();
        this.shopPane.setVisible(true);
    }

    private void showMenu(){
        this.shopController.showMenu();
    }

    private void createShop() {
        VBox mainBox = this.createMainBox();
        this.shopPane.getChildren().add(mainBox);
        this.shopPane.setVisible(false);
        this.root.getChildren().add(this.shopPane);
    }

    private void bindFont(final Labeled node, final String family, final FontWeight weight, final double size, final double ratio) {
        node.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font(family, weight, Math.max(size, w.doubleValue() * ratio))
        ));

    }

    private void bindSpacing(final VBox box, final double space, final double ratio) {
        box.spacingProperty().bind(this.root.widthProperty().map(w ->
            Math.max(space, w.doubleValue() * ratio)
        ));
    }

    private void bindPadding(final Region region, final double padding, final double ratio) {
        region.paddingProperty().bind(this.root.widthProperty().map(w ->
            new Insets(Math.max(padding, w.doubleValue() * ratio))
        ));
    }

    private void bindSize(final Region region, final double size, final double ratio) {
        final var sizeProperty = this.root.widthProperty().map(w -> 
            Math.max(size, w.doubleValue() * ratio)
        );
        region.prefWidthProperty().bind(sizeProperty);
        region.prefHeightProperty().bind(sizeProperty);
    }
    
    private VBox createMainBox() {
        VBox mainBox = new VBox();
        mainBox.spacingProperty().bind(this.root.widthProperty().map(w ->
            Math.max(LARGE_SPACING, w.doubleValue() * MAIN_PADDING_RATIO)
        ));
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

    

    private VBox createHeader () {  
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.spacingProperty().bind(this.root.widthProperty().map(w ->
            Math.max(MEDIUM_SPACING, w.doubleValue() * MEDIUM_SPACING_RATIO)
        ));
        Label title = new Label("SKIN SHOP");
        title.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font(null, FontWeight.BOLD, Math.max(MIN_TITLE_FONT_SIZE, w.doubleValue() * TITLE_FONT_RATIO))
        ));
        title.setTextFill(WHITE);
        this.coinLabel = new Label("COINS: ");
        this.coinLabel.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font(null, FontWeight.BOLD, Math.max(MIN_HEADER_FONT_SIZE, w.doubleValue() * HEADER_FONT_RATIO))
        ));
        this.coinLabel.setTextFill(GOLD);
        header.getChildren().addAll(title, this.coinLabel);
        return header;
    }

    private ScrollPane createScrollPane() {
        this.skinContainer = new FlowPane();
        this.skinContainer.setAlignment(Pos.CENTER);
        this.skinContainer.hgapProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(MIN_FLOW_SPACING, w.doubleValue() * FLOW_SPACING_RATIO)
        ));
        this.skinContainer.vgapProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(MIN_FLOW_SPACING, w.doubleValue() * FLOW_SPACING_RATIO)
        )); 
        this.skinContainer.paddingProperty().bind(this.root.widthProperty().map(w -> 
            new Insets(Math.max(MIN_PADDING, w.doubleValue() * MEDIUM_SPACING_RATIO), 0,
                    Math.max(MIN_PADDING, w.doubleValue() * MEDIUM_SPACING_RATIO), 0)
        ));
        ScrollPane scrollPane = new ScrollPane(this.skinContainer);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return scrollPane;
    }

    private Button createBackButton() {
        Button backButton = new Button("BACK TO MENU");
        backButton.paddingProperty().bind(this.root.widthProperty().map(w -> {
            double padding = Math.max(MIN_PADDING, w.doubleValue() * BUTTON_PADDING_RATIO);
            return new Insets(padding, padding * 2.5, padding, padding * 2.5);
        }));
        this.setStyleButton(backButton, ORANGE);
        backButton.setOnAction(e -> {
            this.showMenu();
            this.hide();
        });
        return backButton;
    }

    private void updateShop() {
        this.updateCoinLabel();
        this.updateSkinContainer();
    }

    private void updateCoinLabel() {
        this.coinLabel.setText("COINS: " + this.shopController.getCoinCount());
    }

    private void updateSkinContainer() {
        this.skinContainer.getChildren().clear();
        this.shopController.getAllSkins().stream()
            .sorted(Comparator.comparingInt(Skin::getPrice))
            .map(this::createSkinBoxForSkin)
            .forEach(this.skinContainer.getChildren()::add);
    }

    private VBox createSkinBoxForSkin(final Skin skin) {
        return this.createSkinBox(
            skin,
            this.isUnlocked(skin),
            this.isActive(skin)
        );
    }
    
    private boolean isUnlocked(final Skin skin) {
        return this.shopController.getUnlockedSkins().contains(skin);
    }

    private boolean isActive(final Skin skin){
        return this.shopController.getActiveSkin().equals(skin);
    }


    private VBox createSkinBox(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        VBox box = new VBox();    
        box.setAlignment(Pos.CENTER);
        box.spacingProperty().bind(this.root.widthProperty().map(w ->
            Math.max(MEDIUM_SPACING, w.doubleValue() * MEDIUM_SPACING_RATIO)
        ));
        box.paddingProperty().bind(this.root.widthProperty().map(w ->
            new Insets(Math.max(MIN_PADDING, w.doubleValue() * BOX_PADDING_RATIO))
        ));
        box.prefWidthProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(SKIN_BOX_WIDTH, w.doubleValue() * SKIN_BOX_WIDTH_RATIO)
        ));
        box.maxWidthProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(SKIN_BOX_WIDTH, w.doubleValue() * SKIN_BOX_WIDTH_RATIO)
        ));
        this.applySkinBoxStyle(box, isActive);
        box.getChildren().addAll(
            this.createImageBox(skin, isUnlocked),
            this.createInfoSkinBox(skin, isUnlocked, isActive),
            this.createActionElement(skin, isUnlocked, isActive)
        );
        return box;
    }

    private void applySkinBoxStyle(final VBox box, final boolean isActive) {
        if(isActive){
            box.setBorder(this.createBorder(BACKGROUND_COLOR, ACTIVE_BORDER_WIDTH));
            box.setBackground(this.createBackground(WHITE));
        } else {
            box.setBorder(this.createBorder(WHITE, NORMAL_BORDER_WIDTH));
        }
    }

    private Background createBackground (final Color color) {
        return new Background(new BackgroundFill(
            color,
            this.createCornerRadii(),
            Insets.EMPTY
        ));
    }

    private Font createFont(final String family, final FontWeight weight, final double RATIO) {
        return Font.font(family, weight, RATIO);
    }

    private Border createBorder(final Color color, final double width) {
        return new Border(new BorderStroke(
            color,
            BorderStrokeStyle.SOLID,
            this.createCornerRadii(),
            new BorderWidths(width)
        ));
    }

    private CornerRadii createCornerRadii() {
        return new CornerRadii(BORDER_RADIUS);
    }

    private StackPane createImageBox(final Skin skin, final boolean isUnlocked) {
        StackPane imageBox = new StackPane();
        final var size = this.root.widthProperty().map(w -> 
            Math.max(IMAGE_BOX_SIZE, w.doubleValue() * IMAGE_BOX_RATIO)
        );
        imageBox.prefWidthProperty().bind(size);
        imageBox.prefHeightProperty().bind(size);
        imageBox.maxWidthProperty().bind(size);
        imageBox.maxHeightProperty().bind(size);
        imageBox.setBackground(this.createBackground(WHITE));
        imageBox.setBorder(this.createBorder(GRAY, NORMAL_BORDER_WIDTH));
        this.loadSkinImage(imageBox, skin, isUnlocked);
        return imageBox;
    }

    private void loadSkinImage(final StackPane imageBox, final Skin skin, final boolean isUnlocked) {
         try {
            String imagePath = "/skins/" + skin.getId() + "_front.png";
            
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            final ImageView imageView = this.createImageView(image, isUnlocked);

            if (!isUnlocked) {
                imageBox.getChildren().addAll(imageView, this.createLockLabel());
            }else {
                imageBox.getChildren().add(imageView);
            }
        }catch (Exception e) {
            imageBox.getChildren().add(this.createPlaceholderLabel());
        }
    }

    private ImageView createImageView(final Image image, final boolean isUnlocked) {
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(IMAGE_BOX_SIZE, w.doubleValue() * IMAGE_VIEW_RATIO)
        ));
        imageView.fitHeightProperty().bind(this.root.widthProperty().map(w -> 
            Math.max(IMAGE_BOX_SIZE, w.doubleValue() * IMAGE_VIEW_RATIO)
        ));
        imageView.setPreserveRatio(true);
        if (!isUnlocked) {
            imageView.setOpacity(OPACITY);
        }
        return imageView;
    }

    private Label createLockLabel() {
        Label lockLabel = new Label("🔒");
        lockLabel.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font(Math.max(MIN_HEADER_FONT_SIZE, w.doubleValue() * HEADER_FONT_RATIO))
        ));
        lockLabel.setTextFill(GRAY);
        return lockLabel;
    }

    private Label createPlaceholderLabel() {
        final Label placeholder = new Label("?");
        placeholder.setTextFill(Color.GRAY);
        return placeholder;
    }

    private VBox createInfoSkinBox(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        Label statusLabel = new Label(this.getStatusText(isUnlocked, isActive));
        statusLabel.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font("Verdana", FontWeight.BOLD, Math.max(MIN_SMALL_FONT_SIZE, w.doubleValue() * SMALL_FONT_RATIO))
        ));
        statusLabel.setTextFill(this.getStatusColor(isUnlocked, isActive));
        Label nameLabel = new Label(skin.getName());
        nameLabel.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font("Arial", FontWeight.BOLD, Math.max(MIN_NORMAL_FONT_SIZE, w.doubleValue() * NORMAL_FONT_RATIO))
        ));
        infoBox.getChildren().addAll(nameLabel, statusLabel);
        return infoBox;
    }

    private String getStatusText(final boolean isUnlocked, final boolean isActive) {
        if(isActive) {
            return "Equipped";
        }
        return isUnlocked ? "Unlocked" : "Locked";
    }

    private Color getStatusColor(final boolean isUnlocked, final boolean isActive) {
        if (isActive) {
            return BLUE;
        }
        return isUnlocked ? GREEN : RED;
    }

    private Node createActionElement(final Skin skin, final boolean isUnlocked, final boolean isActive) {
        if (isActive) {
            return this.createActiveLabel();
        } else if (isUnlocked) {
            return this.createEquipButton(skin);
        } else {
            return this.createUnlockButton(skin);
        }
    }

    private Label createActiveLabel() {
        Label activeLabel = new Label("ACTIVE");
        activeLabel.setTextFill(BLUE);
        return activeLabel;
    }

    private Button createEquipButton(final Skin skin) {
        Button equipButton = new Button("Equip");
        equipButton.setOnAction(e -> {
            this.shopController.activateSkin(skin);
            this.updateShop();
        });
        this.setStyleButton(equipButton, BLUE);
        return equipButton;
    }

    private Button createUnlockButton(final Skin skin) {
        Button unlockButton = new Button("Buy: " + skin.getPrice() + " Coins");
        unlockButton.setOnAction(e -> {
            if (this.shopController.tryUnlockSkin(skin)) {
                this.updateShop();
            }
        });
        unlockButton.setDisable(this.shopController.getCoinCount() < skin.getPrice());
        this.setStyleButton(unlockButton, GREEN);
        return unlockButton;
    }

    private void setStyleButton(final Button button, final Color color){
        button.fontProperty().bind(this.root.widthProperty().map(w -> 
            Font.font("Arial", FontWeight.BOLD, Math.max(MIN_SMALL_FONT_SIZE, w.doubleValue() * SMALL_FONT_RATIO))
        ));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(
            color,
            new CornerRadii(5),
            Insets.EMPTY
        )));
    }

}
