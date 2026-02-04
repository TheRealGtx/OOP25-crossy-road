package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.view.api.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the GameView interface.
 *
 */
public class GameViewImpl implements GameView {

    private final Map<EntityType, Label> powerUpLabels;
    private final VBox powerUpBox = new VBox(5);
    private final Label coinLabel = new Label();

    public GameViewImpl() {
        this.powerUpLabels = new EnumMap<>(EntityType.class);
    }

    @Override
    public void setController(GameController c) {
        //TODO: Giuli
    }

    @Override
    public void render(List<Positionable> positionables) {
        //TODO: Giuli
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUpTime(Map<EntityType, Long> powerUps) {
        powerUpBox.getChildren().clear();
        for (Map.Entry<EntityType, Long> entry: powerUps.entrySet()) {
            EntityType powerUpType = entry.getKey();
            final int duration = (int) (entry.getValue() / 1000);

            if (powerUpLabels.containsKey(powerUpType)) {

                startPowerUpTimer(powerUpType, duration);
            } else {
                final Label label = new Label(formatPowerUpText(powerUpType, duration));
                powerUpLabels.put(powerUpType, label);
                powerUpBox.getChildren().add(label);
                startPowerUpTimer(powerUpType, duration);
            }
        }
    }

    /**
     * Formats the power-up text for display.
     *
     * @param type the power-up type
     * @param secondsLeft the remaining time in seconds
     * @return the formatted text
     */
    private String formatPowerUpText(EntityType type, int secondsLeft) {
        return type.getDisplayName() + ": " + secondsLeft + "s";
    }

    /**
     * Extracts the remaining time from the label text.
     *
     * @param label the label containing the time
     * @return the remaining time in seconds
     */
    private int extractTime(Label label) {
        return Integer.parseInt(label.getText().replace("\\D+", ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCoinCount(int count) {
        coinLabel.setText("Coins: " + count);
    }

    @Override
    public void show() {
        //TODO: Mattia
    }

    @Override
    public void hide() {
        //TODO: Giuli
    }
}
