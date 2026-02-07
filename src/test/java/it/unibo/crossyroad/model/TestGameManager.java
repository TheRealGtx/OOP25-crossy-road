package it.unibo.crossyroad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameManagerImpl;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestGameManager {
    private GameManager gameManager;

    @BeforeEach
    void setUpGameManager() {
        final GameParameters gameParameters = new GameParametersBuilder()
                              .setCoinMultiplier(1)
                              .setCarSpeedMultiplier(1)
                              .setTrainSpeedMultiplier(1)
                              .setInvincibility(false)
                              .setCoinCount(0)
                              .setLogSpeedMultiplier(1)
                              .build();
        this.gameManager = new GameManagerImpl(gameParameters);
    }

    @Test
    void testPositionables() {
        assertNotNull(this.gameManager.getPositionables());
        assertFalse(this.gameManager.getPositionables().isEmpty());
    }

    @Test
    void testActivePowerUps() {
        assertNotNull(this.gameManager.getActivePowerUps());
        assertTrue(this.gameManager.getActivePowerUps().isEmpty());
    }

    @Test
    void testGameOver() {
        assertFalse(this.gameManager.isGameOver());
    }
}
