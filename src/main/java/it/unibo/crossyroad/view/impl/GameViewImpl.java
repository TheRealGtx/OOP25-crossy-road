package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.view.api.GameView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Map;
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

    /**
     * Constructor.
     *
     * @param root the stack pane
     */
    public GameViewImpl(final StackPane root) {
        this.root = Objects.requireNonNull(root, "root cannot be null");
        this.currenPane = new StackPane();

        this.root.getChildren().add(this.currenPane);
    }

    @Override
    public void setController(final GameController c) {
        //TODO: Giuli
    }

    @Override
    public void render(final List<Positionable> positionables) {
        //TODO: Giuli
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
        //TODO: Giuli
    }
}
