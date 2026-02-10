package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.model.impl.GameParametersImpl;

/**
 * Test class for the {@link Coin} class.
 */
class TestCoin {
    private static final Position POS = new Position(1 ,1);
    private static final int COIN_MULTIPLIER = 4;
    private Coin coin;
    private GameParameters gameParameters;

    /**
     * Sets up a new coin and a new instance of game parameters.
     */
    @BeforeEach
    void setUp() {
        this.coin = new Coin(POS);
        this.gameParameters = new GameParametersImpl();
    }

    /**
     * Tests that a coin is not picked up at the beginning.
     */
    @Test
    void testCoinNotPickedUpAtTheBeginning() {
        assertFalse(this.coin.isPickedUp());
    }

    /**
     * Tests that picking up a coin increments the coin count according to the current coin multiplier.
     */
    @Test
    void testCoinPickUpIncrementsCoinCount() {
        final int initCoins = this.gameParameters.getCoinCount();
        coin.pickUp(gameParameters);
        assertEquals(initCoins + this.gameParameters.getCoinMultiplier(), this.gameParameters.getCoinCount());
    }

    /**
     * Tests that picking up the coin multiple times applies the effect only once. 
     */
    @Test
    void testCoinCanBePickUpOnlyOnce() {
        final int initcoins = this.gameParameters.getCoinCount();
        coin.pickUp(this.gameParameters);
        coin.pickUp(this.gameParameters);
        assertEquals(initcoins + this.gameParameters.getCoinMultiplier(), this.gameParameters.getCoinCount());
        assertTrue(coin.isPickedUp());
    }

    /**
     * Tests that coin applies a custom coin multiplier.
     */
    @Test
    void testCoinMultiplier() {
        gameParameters.setCoinMultiplier(COIN_MULTIPLIER);
        final int initcoins = gameParameters.getCoinCount();
        this.coin.pickUp(gameParameters);
        assertEquals(initcoins + COIN_MULTIPLIER, gameParameters.getCoinCount());
    }
}
