package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Invincibility;

/**
 * Test class for the {@link Invincibility} class.
 */
class TestInvincibility extends AbstractTestPowerUp<Invincibility> {
    private static final Position POSITION = new Position(0, 0);
    private static final long INVINCIBILITY_DURATION = 10_000L;

    /**
     * {@inheritDoc}
     */
    @Override
    Invincibility createPowerUP() {
        setDuration(INVINCIBILITY_DURATION);
        return new Invincibility(POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectApplied(final GameParameters currentGameParameters) {
        assertTrue(currentGameParameters.isInvincible());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void assertEffectRemoved(final GameParameters currentGameParameters) {
        assertFalse(currentGameParameters.isInvincible());
    }
}
