package it.unibo.crossyroad.view.api;

import java.util.List;
import java.util.Map;

import it.unibo.crossyroad.controller.api.GameController;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;

public interface GameView {
    
    void setCOntroller(GameController controller);

    void render(List<Positionable> positionables);

    void update(Map<EntityType, Long> powerUps);

    void updateCoinCount(int count);
}
