package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractPowerUp;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;

/**
 * A power-up that temporarily slows down all cars in the game. 
 */
public class SlowCars extends AbstractPowerUp {

    private static final double CAR_SLOW_MOTION = 0.5;
    private static final long CAR_SLOW_MOTION_DURATION = 10_000L;

    /**
     * Creates a new slow cars power-up at the given position.
     * 
     * @param position the initial position of the power-up.
     */
    public SlowCars(final Position position) {
        super(position, CAR_SLOW_MOTION_DURATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deactivate(final GameParameters gameParameters) {
        gameParameters.setCarSpeedMultiplier(1.0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void applyEffect(final GameParameters gameParameters) {
        gameParameters.setCarSpeedMultiplier(CAR_SLOW_MOTION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.SLOW_CARS; 
    }
}
