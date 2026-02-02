package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.AbstractPowerUp;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameParametersImpl;

/**
 * An Abstract class for testing power-up.
 * 
 * @param <T> its the type of power-up.
 */
abstract class AbstractTestPowerUp<T extends AbstractPowerUp> {

    private T powerUp;
    private GameParameters gameParameters;
    private long duration;

    /**
     * Set the duration of the power-up.
     * 
     * @param duration the duration of the power-up.
     */
    void setDuration(final long duration) {
        if (duration > 0) {
            this.duration = duration;
        } else {
            throw new IllegalArgumentException("duration must be > 0");
        }
    }

    /**
     * Create a power-up instance.
     * 
     * @return a new power-up.
     */
    abstract T createPowerUP();

    /**
     * Control that the power-up effect is active.
     * 
     * @param currentGameParameters the game parameters to check.
     */
    abstract void assertEffectApplied(GameParameters currentGameParameters);

    /**
     * Control that the power-up effect is removed.
     * 
     * @param currentGameParameters the game parameters to check.
     */
    abstract void assertEffectRemoved(GameParameters currentGameParameters);

    /**
     * Sets up a new power-up and a new instance of game parameters.
     */
    @BeforeEach
    void setUp() {
        this.powerUp = createPowerUP();
        this.gameParameters = new GameParametersImpl();
    }

    /**
     * Tests that a power-up isn't picked up at the begining.
     */
    @Test
    void testNotPickedUpAtTheBeginning() {
        assertFalse(this.powerUp.isPickedUp());
    }

    /**
     * Tests that the power-up is marked as picked.
     */
    @Test
    void testPickedUp() {
        this.powerUp.pickUp(this.gameParameters);
        assertTrue(this.powerUp.isPickedUp());
    }

    /**
     * Test that the power-up effect is applied only once.
     */
    @Test
    void testAppliedOnlyOnce() {
        this.powerUp.pickUp(this.gameParameters);
        this.powerUp.pickUp(this.gameParameters);
        assertEffectApplied(this.gameParameters);
    }

    /**
     * Tests that the power-up terminates after his duration.
     */
    @Test
    void testEffectDeactivatedAfterDuration() {
        this.powerUp.pickUp(this.gameParameters);
        this.powerUp.update(this.duration, this.gameParameters);
        assertEffectRemoved(this.gameParameters);
    }

    /**
     * Tests that the power-up doesn't terminate before its duration.
     */
    @Test
    void testEffectNotDeactivatedBeforeDuration() {
        this.powerUp.pickUp(this.gameParameters);
        this.powerUp.update(this.duration / 3, this.gameParameters);
        assertEffectApplied(this.gameParameters);
    }
}
