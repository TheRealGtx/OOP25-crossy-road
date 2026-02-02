package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.SlowCars;

/**
 * Tests class for the {@link SlowCars} class.
 */
class TestSlowCars extends AbstractTestPowerUp<SlowCars> {
    private static final Position POSITION = new Position(0, 0);
    private static final long CAR_SLOW_MOTION_DURATION = 10_000L;

    /**
     * {@inheritDoc}
     */
    @Override
    SlowCars createPowerUP() {
        setDuration(CAR_SLOW_MOTION_DURATION);
        return new SlowCars(POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectApplied(final GameParameters currentGameParameters) {
        assertEquals(0.5, currentGameParameters.getCarSpeedMultiplier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectRemoved(final GameParameters currentGameParameters) {
        assertEquals(1.0, currentGameParameters.getCarSpeedMultiplier());
    }
}
