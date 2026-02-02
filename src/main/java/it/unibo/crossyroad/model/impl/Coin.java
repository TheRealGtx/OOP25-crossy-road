package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPickable;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;

/**
 * A class representig a coin in the game.
 */
public class Coin extends AbstractPickable {

    /**
     * It creates a new pickable (coin) with the initial position.
     * 
     * @param position the initial position of the coin.
     */
    public Coin(final Position position) {
        super(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void applyEffect(final GameParameters gameParameters) {
        gameParameters.incrementCoinCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.COIN;
    }
}
