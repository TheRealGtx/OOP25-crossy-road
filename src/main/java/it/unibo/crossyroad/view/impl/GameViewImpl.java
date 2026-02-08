package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.Chunk;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Player;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.model.api.PowerUp;
import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.view.api.GameView;
import it.unibo.crossyroad.view.api.UserInput;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the GameView interface.
 * 
 * @see GameView
 *
 */
public final class GameViewImpl implements GameView {

    private static final int GAME_WIDTH = 10;
    private static final int GAME_HEIGHT = 9;
    private static final int OVERLAY_PADDING = 20;
    private static final int OVERLAY_WIDTH = 200;
    private static final int OVERLAY_HEIGHT = 50;
    private final StackPane root;
    private final StackPane currentPane;
    private final VBox powerUpBox = new VBox(5);
    private final VBox overlay;
    private final Label coinLabel = new Label();
    private final Canvas canvas;
    private final GraphicsContext content;
    private final Map<EntityType, Image> images = new EnumMap<>(EntityType.class);
    private GameController gameController;
    private double scale;

    /**
     * Initializes and places the various view's components.
     *
     * @param root the application's main StackPane.
     */
    public GameViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "root cannot be null");
        this.currentPane = new StackPane();
        this.canvas = new Canvas();
        this.content = this.canvas.getGraphicsContext2D();

        //Set up the overlay
        this.overlay = new VBox(10);
        this.overlay.setPadding(new Insets(OVERLAY_PADDING));
        this.overlay.setAlignment(Pos.TOP_LEFT);
        this.overlay.setPickOnBounds(false);
        this.overlay.setMaxWidth(OVERLAY_WIDTH);
        this.overlay.setMaxHeight(OVERLAY_HEIGHT);
        this.overlay.setBackground(Background.fill(Color.WHITE));

        //Bind canvas too root size
        this.canvas.widthProperty().bind(root.widthProperty());
        this.canvas.heightProperty().bind(root.heightProperty());
        this.canvas.widthProperty().addListener(c -> scale());
        this.canvas.heightProperty().addListener(c -> scale());

        //Manage key press
        this.currentPane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                case W:
                    this.gameController.processInput(UserInput.UP);
                    break;
                case LEFT:
                case A:
                    this.gameController.processInput(UserInput.LEFT);
                    break;
                case DOWN:
                case S:
                    this.gameController.processInput(UserInput.DOWN);
                    break;
                case RIGHT:
                case D:
                    this.gameController.processInput(UserInput.RIGHT);
                    break;
                case ESCAPE:
                    this.gameController.pauseGame();
                    break;
                default: //A default isn't needed in this case.
                    break;
            }
        });
        currentPane.setFocusTraversable(true);
        currentPane.requestFocus();

        this.content.setImageSmoothing(false);
        this.overlay.getChildren().addAll(this.powerUpBox, this.coinLabel);
        this.currentPane.getChildren().addAll(this.canvas, this.overlay);
        StackPane.setAlignment(this.overlay, Pos.TOP_LEFT);
        this.root.getChildren().add(this.currentPane);

        this.scale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final GameController c) {
        Objects.requireNonNull("The Game Controller cannot be null");
        this.gameController = c;
        this.loadImages();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final List<Positionable> positionables) {
        Objects.requireNonNull("The list of Positionable elements cannot be null");

        Platform.runLater(() -> {
            this.content.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

            positionables.stream()
                         .filter(p -> p instanceof Chunk)
                         .forEach(this::drawElement);
            positionables.stream()
                         .filter(p -> p instanceof Coin)
                         .forEach(this::drawElement);
            positionables.stream()
                         .filter(p -> p instanceof PowerUp)
                         .map(p -> (PowerUp) p).filter(p -> !p.isPickedUp())
                         .forEach(this::drawElement);
            positionables.stream()
                         .filter(p -> p instanceof Obstacle)
                         .forEach(this::drawElement);
            positionables.stream()
                         .filter(p -> p instanceof Player)
                         .forEach(this::drawElement);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUpTime(final Map<EntityType, Long> powerUps) {
        Platform.runLater(() -> {
            powerUpBox.getChildren().clear();
            for (final Map.Entry<EntityType, Long> entry: powerUps.entrySet()) {
                final int duration = (int) (entry.getValue() / 1000);
                powerUpBox.getChildren().add(new Label(
                        formatPowerUpText(entry.getKey(), duration)
                ));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCoinCount(final int count) {
        Platform.runLater(() -> {
            coinLabel.setText("Coins: " + count);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        Platform.runLater(() -> {
            this.currentPane.setVisible(true);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        Platform.runLater(() -> {
            this.currentPane.setVisible(false);
        });
    }

    /**
     * Computes the scale for the game.
     */
    private void scale() {
        final double scaleX = this.canvas.getWidth() / GAME_WIDTH;
        final double scaleY = this.canvas.getHeight() / GAME_HEIGHT;
        this.scale = Math.min(scaleX, scaleY);
    }

    /**
     * Draws the given element on the map.
     * 
     * @param pos the element to place on the map.
     */
    private void drawElement(final Positionable pos) {
        final Image image = this.images.get(pos.getEntityType());

        if (image != null) {
            final double x = Math.round(pos.getPosition().x() * this.scale);
            final double y = Math.round(pos.getPosition().y() * this.scale);
            final double width = Math.round(pos.getDimension().width() * this.scale);
            final double height = Math.round(pos.getDimension().height() * this.scale);
            this.content.drawImage(image, x, y, width, height);
        }
    }

    /**
     * Load the images for the various elements.
     */
    private void loadImages() {
        this.images.put(EntityType.PLAYER, new Image(this.gameController.getActiveSkin()));
        this.images.put(EntityType.GRASS, new Image("chunks/grass.png"));
        this.images.put(EntityType.ROAD, new Image("chunks/road.png"));
        this.images.put(EntityType.RIVER, new Image("chunks/river.png"));
        this.images.put(EntityType.CAR_LEFT, new Image("obstacles/car_left.png"));
        this.images.put(EntityType.CAR_RIGHT, new Image("obstacles/car_right.png"));
        this.images.put(EntityType.WOOD_LOG, new Image("obstacles/log.png"));
        this.images.put(EntityType.RAILWAY, new Image("chunks/railway.png"));
        this.images.put(EntityType.ROCK, new Image("obstacles/rock.png"));
        this.images.put(EntityType.TREE, new Image("obstacles/tree.png"));
        this.images.put(EntityType.TRAIN_LEFT, new Image("obstacles/train_left.png"));
        this.images.put(EntityType.TRAIN_RIGHT, new Image("obstacles/train_right.png"));
        this.images.put(EntityType.COIN, new Image("pickables/coin.png"));
        this.images.put(EntityType.COIN_MULTIPLIER, new Image("pickables/multiplier.png"));
        this.images.put(EntityType.INVINCIBILITY, new Image("pickables/invincible.png"));
        this.images.put(EntityType.SLOW_CARS, new Image("pickables/slow.png"));
        this.images.put(EntityType.WATER, new Image("assets/water.png"));
    }

    /**
     * Formats the power-up text for display.
     *
     * @param type the power-up type.
     * @param secondsLeft the remaining time in seconds.
     * @return the formatted text.
     */
    private String formatPowerUpText(final EntityType type, final int secondsLeft) {
        return type.getDisplayName() + ": " + secondsLeft + "s";
    }
}
