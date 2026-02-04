package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.view.api.GameView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the GameView interface.
 *
 */
public class GameViewImpl extends StackPane implements GameView {

    private final VBox powerUpBox = new VBox(5);
    private final Label coinLabel = new Label();

    public GameViewImpl() {
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
    private String formatPowerUpText(EntityType type, int secondsLeft) {
        return type.getDisplayName() + ": " + secondsLeft + "s";
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
        this.setVisible(true);
    }

    @Override
    public void hide() {
        //TODO: Giuli
    }
}
