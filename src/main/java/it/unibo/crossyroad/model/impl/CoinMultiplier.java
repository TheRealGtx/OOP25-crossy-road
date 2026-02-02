package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPowerUp;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;

/**
 * A power-up that temporarily increases the amount of coins gained by the player.
 */
public class CoinMultiplier extends AbstractPowerUp {

    private static final int COIN_MULTIPLIER = 3;
    private static final long COIN_MULTIPLIER_DURATION = 10_000L;

    /**
     * Creates a new coin multiplier power-up at the given position.
     * 
     * @param position the initial position of the power-up.
     */
    public CoinMultiplier(final Position position) {
        super(position, COIN_MULTIPLIER_DURATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void deactivate(final GameParameters gameParameters) {
        gameParameters.setCoinMultiplier(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void applyEffect(final GameParameters gameParameters) {
        gameParameters.setCoinMultiplier(COIN_MULTIPLIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.COIN_MULTIPLIER; 
    }
}
