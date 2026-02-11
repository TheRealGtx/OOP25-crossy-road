package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPowerUp;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;

/**
 * A power-up that temporarily makes the player invincible.
 */
public class Invincibility extends AbstractPowerUp {

    private static final long INVINCIBILITY_DURATION = 10_000L;

    /**
     * Creates a new invincibility power-up at the given position.
     * 
     * @param position the initial position of the power-up.
     */
    public Invincibility(final Position position) {
        super(position, INVINCIBILITY_DURATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate(final GameParameters gameParameters) {
        gameParameters.setInvincibility(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void applyEffect(final GameParameters gameParameters) {
        gameParameters.setInvincibility(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.INVINCIBILITY; 
    }
}
