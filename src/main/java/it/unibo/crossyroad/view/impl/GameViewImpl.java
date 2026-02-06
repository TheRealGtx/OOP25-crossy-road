package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.Chunk;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Player;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.model.api.PowerUp;
import it.unibo.crossyroad.model.impl.Car;
import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.model.impl.CoinMultiplier;
import it.unibo.crossyroad.model.impl.Grass;
import it.unibo.crossyroad.model.impl.Invincibility;
import it.unibo.crossyroad.model.impl.Railway;
import it.unibo.crossyroad.model.impl.River;
import it.unibo.crossyroad.model.impl.Road;
import it.unibo.crossyroad.model.impl.Rock;
import it.unibo.crossyroad.model.impl.SlowCars;
import it.unibo.crossyroad.model.impl.Train;
import it.unibo.crossyroad.model.impl.Tree;
import it.unibo.crossyroad.model.impl.Water;
import it.unibo.crossyroad.model.impl.WoodLog;
import it.unibo.crossyroad.view.api.GameView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the GameView interface.
 *
 */
public class GameViewImpl implements GameView {

    private static final int GAME_WIDTH = 10;
    private static final int GAME_HEIGHT = 9;
    private final StackPane root;
    private final StackPane currentPane;
    private final VBox powerUpBox = new VBox(5);
    private final Label coinLabel = new Label();
    private GameController gameController;
    private final Map<EntityType, Image> images = new HashMap<>();
    private final Canvas canvas;
    private final GraphicsContext content;
    private double scale;

    /**
     * Constructor.
     *
     * @param root the stack pane.
     */
    public GameViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "root cannot be null");
        this.currentPane = new StackPane();
        this.canvas = new Canvas();
        this.content = this.canvas.getGraphicsContext2D();

        //CurrentPane on the canvas
        VBox overlay = new VBox(10);
        overlay.setAlignment(Pos.TOP_LEFT);
        overlay.setPadding(new Insets(20));
        overlay.setPickOnBounds(false);

        //Bind canvas too root size
        this.canvas.widthProperty().bind(root.widthProperty());
        this.canvas.heightProperty().bind(root.heightProperty());
        this.canvas.widthProperty().addListener(c -> scale());
        this.canvas.heightProperty().addListener(c -> scale());

        //Movement
        this.currentPane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    this.gameController.processInput(Direction.UP);
                    break;
                case A:
                    this.gameController.processInput(Direction.LEFT);
                    break;
                case S:
                this.gameController.processInput(Direction.DOWN);
                    break;
                case D:
                this.gameController.processInput(Direction.RIGHT);
                break;
                default:
                    break;
            }
        });
        currentPane.setFocusTraversable(true);
        currentPane.requestFocus();

        this.content.setImageSmoothing(false);

        this.loadImages();
        this.scale();

        overlay.getChildren().addAll(this.powerUpBox, this.coinLabel);
        this.currentPane.getChildren().addAll(this.canvas, overlay);
        this.root.getChildren().add(this.currentPane);
    }

    /**
     * Computes the scale for the game;
     */
    private void scale() {
        double scaleX = this.canvas.getWidth() / GAME_WIDTH;
        double scaleY = this.canvas.getHeight() / GAME_HEIGHT;
        this.scale = Math.min(scaleX, scaleY);
    }

    @Override
    public void setController(final GameController c) {
        this.gameController = c;
    }

    @Override
    public void render(final List<Positionable> positionables) {
        Platform.runLater(() -> {
            this.content.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

            positionables.stream().filter(p -> p instanceof Chunk).forEach(this::drawElement);
            positionables.stream().filter(p -> p instanceof Player).forEach(this::drawElement);
            positionables.stream().filter(p -> p instanceof Obstacle).forEach(this::drawElement);
            positionables.stream().filter(p -> p instanceof Coin).forEach(this::drawElement);
            positionables.stream().filter(p -> p instanceof PowerUp).forEach(this::drawElement);
        });
    }

    private void drawElement(Positionable pos) {
        Image image = this.images.get(pos.getEntityType());

        if (image != null) {
            double x = Math.round(pos.getPosition().x() * this.scale);
            double y = Math.round(pos.getPosition().y() * this.scale);
            double width = Math.round(pos.getDimension().width() * this.scale);
            double height = Math.round(pos.getDimension().height() * this.scale);

            this.content.drawImage(image, x, y, width, height);
        }
    }


    //TODO add paths
    /**
     * Load the images for the various elements.
     */
    private void loadImages() {
        this.images.put(EntityType.PLAYER, new Image("assets/player.png"));
        this.images.put(EntityType.GRASS, new Image("assets/grass.png"));
        this.images.put(EntityType.ROAD, new Image("assets/road.png"));
        // this.images.put(River.class, new Image("path"));
        //this.images.put(EntityType.CAR, new Image("assets/car.png"));
        // this.images.put(WoodLog.class, new Image("path"));
        this.images.put(EntityType.RAILWAY, new Image("assets/railway.png"));
        this.images.put(EntityType.ROCK, new Image("assets/rock.png"));
        this.images.put(EntityType.TREE, new Image("assets/tree.png"));
        //this.images.put(EntityType.TRAIN, new Image("assets/train.png"));
        this.images.put(EntityType.COIN, new Image("assets/coin.png"));
        this.images.put(EntityType.COIN_MULTIPLIER, new Image("assets/coinMultiplier.png"));
        this.images.put(EntityType.INVINCIBILITY, new Image("assets/invincibility.png"));
        this.images.put(EntityType.SLOW_CARS, new Image("assets/obstacleSpeed.png"));
        // this.images.put(Water.class, new Image("path"));
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
     * Formats the power-up text for display.
     *
     * @param type the power-up type
     * @param secondsLeft the remaining time in seconds
     * @return the formatted text
     */
    private String formatPowerUpText(final EntityType type, final int secondsLeft) {
        return type.getDisplayName() + ": " + secondsLeft + "s";
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

    @Override
    public void show() {
        Platform.runLater(() -> {
            this.currentPane.setVisible(true);
        });
    }

    @Override
    public void hide() {
        Platform.runLater(() -> {
            this.currentPane.setVisible(false);
        });
    }
}
