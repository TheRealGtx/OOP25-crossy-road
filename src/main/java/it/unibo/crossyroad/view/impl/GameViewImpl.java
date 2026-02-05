package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Player;
import it.unibo.crossyroad.model.api.Positionable;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the GameView interface.
 *
 */
public class GameViewImpl implements GameView {

    private final StackPane root;
    private final StackPane currenPane;
    private final VBox powerUpBox = new VBox(5);
    private final Label coinLabel = new Label();
    private GameController gameController;
    private final Map<Class<?>, Image> images = new HashMap<>();

    /**
     * Constructor.
     *
     * @param root the stack pane.
     */
    public GameViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "root cannot be null");
        this.currenPane = new StackPane();

        this.root.getChildren().add(this.currenPane);
        this.currenPane.getChildren().add(this.powerUpBox);
        this.currenPane.getChildren().add(this.coinLabel);
    }

    @Override
    public void setController(final GameController c) {
        this.gameController = c;
    }

    @Override
    public void render(final List<Positionable> positionables) {
        this.currenPane.getChildren().clear();

        for (Positionable pos : positionables) {
            Image image = this.images.get(pos.getClass());
            ImageView imageView = new ImageView(image);
            imageView.setX(pos.getPosition().x());
            imageView.setY(pos.getPosition().y());
            imageView.setFitWidth(pos.getDimension().width());
            imageView.setFitHeight(pos.getDimension().height());
            this.currenPane.getChildren().add(imageView);
        }
    }

    //TODO add paths
    /**
     * Load the images for the various elements.
     */
    private void loadImages() {
        this.images.put(Player.class, new Image("path"));
        this.images.put(Grass.class, new Image("path"));
        this.images.put(Road.class, new Image("path"));
        this.images.put(River.class, new Image("path"));
        this.images.put(Car.class, new Image("path"));
        this.images.put(WoodLog.class, new Image("path"));
        this.images.put(Railway.class, new Image("path"));
        this.images.put(Rock.class, new Image("path"));
        this.images.put(Tree.class, new Image("path"));
        this.images.put(Train.class, new Image("path"));
        this.images.put(Coin.class, new Image("path"));
        this.images.put(CoinMultiplier.class, new Image("path"));
        this.images.put(Invincibility.class, new Image("path"));
        this.images.put(SlowCars.class, new Image("path"));
        this.images.put(Water.class, new Image("path"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUpTime(final Map<EntityType, Long> powerUps) {
        powerUpBox.getChildren().clear();
        for (final Map.Entry<EntityType, Long> entry: powerUps.entrySet()) {
            final int duration = (int) (entry.getValue() / 1000);
            powerUpBox.getChildren().add(new Label(
                    formatPowerUpText(entry.getKey(), duration)
            ));
        }
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
        coinLabel.setText("Coins: " + count);
    }

    @Override
    public void show() {
        this.currenPane.setVisible(true);
    }

    @Override
    public void hide() {
        this.currenPane.setVisible(false);
    }
}
