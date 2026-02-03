package it.unibo.crossyroad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.GameManager;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Player;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Positionable;
import it.unibo.crossyroad.model.api.PowerUp;
import it.unibo.crossyroad.model.impl.Coin;
import it.unibo.crossyroad.model.impl.GameManagerImpl;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
        this.printMap();
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

    private void printMap() {
        System.out.print("\n"); //NOPMD

        for (int j = 8; j >= 0; j--) {
            for (int i = 0; i < 10; i++) {
                final Position current = new Position(i, j);
                final Optional<Positionable> element = this.gameManager.getPositionables()
                                                                       .stream()
                                                                       .filter(p -> p.getPosition().equals(current))
                                                                       .findFirst();

                if (element.isPresent()) {
                    if (element.get() instanceof Obstacle) {
                        System.out.print("x"); //NOPMD
                    } else if (element.get() instanceof PowerUp) {
                        System.out.print("!"); //NOPMD
                    } else if (element.get() instanceof Coin) {
                        System.out.print("o"); //NOPMD
                    } else if (element.get() instanceof Player) {
                        System.out.print("?"); //NOPMD
                    } else {
                        System.out.print("-"); //NOPMD
                    }
                } else {
                    System.out.print("-"); //NOPMD
                }
            }
            System.out.print("\n"); //NOPMD
        }
    }
}
