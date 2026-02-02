package it.unibo.crossyroad.view.api;

import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;

import java.util.List;
import java.util.Map;

/**
 * Interface representing the game view.
 */
public interface GameView extends View {
    //void setController(GameController c);

    void render(List<Positionable> positionables);

    /**
     * Updates the power-up time display.
     *
     * @param powerUps a map of entity types to their remaining time
     */
    void updatePowerUpTime(Map<EntityType, Long> powerUps);

    /**
     * Updates the coin count display.
     *
     * @param count the current coin count
     */
    void updateCoinCount(int count);
}
