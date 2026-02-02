package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.CoinMultiplier;

/**
 * Test class for the {@link CoinMultiplier} class. 
 */
class TestCoinMultiplier extends AbstractTestPowerUp<CoinMultiplier> {
    private static final Position POSITION = new Position(0, 0);
    private static final long DURATION = 10_000L;

    /**
     * {@inheritDoc}
     */
    @Override
    CoinMultiplier createPowerUP() {
        setDuration(DURATION);
        return new CoinMultiplier(POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectApplied(final GameParameters currentGameParameters) {
        assertEquals(3.0, currentGameParameters.getCoinMultiplier());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectRemoved(final GameParameters currentGameParameters) {
        assertEquals(1.0, currentGameParameters.getCoinMultiplier());
    }
}
