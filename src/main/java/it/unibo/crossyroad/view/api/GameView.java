package it.unibo.crossyroad.view.api;

import java.util.List;
import java.util.Map;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;

/**
 * View interface for the game.
 */
public interface GameView extends View {

    /**
     * Sets the controller that will manage the view.
     * 
     * @param c the game controller.
     */
    void setController(GameController c);

    /**
     * Renders all the positionables on the map.
     * 
     * @param positionables the list of items to render.
     */
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
