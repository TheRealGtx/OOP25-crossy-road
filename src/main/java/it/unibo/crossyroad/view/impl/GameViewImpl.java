package it.unibo.crossyroad.view.impl;

import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.view.api.GameView;

import java.util.List;
import java.util.Map;

public class GameViewImpl implements GameView {

    @Override
    public void render(List<Positionable> positionables) {
        //TODO: Giuli
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePowerUpTime(Map<EntityType, Long> powerUps) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCoinCount(int count) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
