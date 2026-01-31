package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for GameParameters implementation.
 */
class TestGameParameters {
    private static final int DEFAULT_COIN_MULTIPLIER = 1;
    private static final double DEFAULT_CAR_SPEED_MULTIPLIER = 1.0;
    private static final double DEFAULT_TRAIN_SPEED_MULTIPLIER = 1.0;
    private static final boolean DEFAULT_INVINCIBILITY = false;
    private static final double DEFAULT_LOG_SPEED_MULTIPLIER = 1.0;
    private static final int DEFAULT_COIN_START_COUNT = 0;
    private static final int TEST_COIN_MULTIPLIER = 2;
    private static final double TEST_CAR_SPEED_MULTIPLIER = 1.5;
    private static final double TEST_TRAIN_SPEED_MULTIPLIER = 2.0;
    private static final boolean TEST_INVINCIBILITY = true;
    private static final double TEST_LOG_SPEED_MULTIPLIER = 1.8;
    private static final int TEST_COIN_START_COUNT = 5;
    private static final int INCREMENT_TIMES = 7;

    private GameParameters gameParameters;

    /**
     * Sets up a new GameParameters instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.gameParameters = new GameParametersBuilder()
                .setCoinCount(DEFAULT_COIN_START_COUNT)
                .setCarSpeedMultiplier(DEFAULT_CAR_SPEED_MULTIPLIER)
                .setTrainSpeedMultiplier(DEFAULT_TRAIN_SPEED_MULTIPLIER)
                .setCoinMultiplier(DEFAULT_COIN_MULTIPLIER)
                .setInvincibility(DEFAULT_INVINCIBILITY)
                .setLogSpeedMultiplier(DEFAULT_LOG_SPEED_MULTIPLIER)
                .build();
    }

    /**
     * Tests the default parameters of GameParameters.
     */
    @Test
    void testDefaultParameters() {
        assertEquals(DEFAULT_COIN_MULTIPLIER, this.gameParameters.getCoinMultiplier());
        assertEquals(DEFAULT_CAR_SPEED_MULTIPLIER, this.gameParameters.getCarSpeedMultiplier());
        assertEquals(DEFAULT_TRAIN_SPEED_MULTIPLIER, this.gameParameters.getTrainSpeedMultiplier());
        assertEquals(DEFAULT_INVINCIBILITY, this.gameParameters.isInvincible());
        assertEquals(DEFAULT_COIN_START_COUNT, this.gameParameters.getCoinCount());
        assertEquals(DEFAULT_LOG_SPEED_MULTIPLIER, this.gameParameters.getLogSpeedMultiplier());
    }

    /**
     * Test setters and getters of GameParameters.
     */
    @Test
    void testSetAndGetParameters() {
        this.gameParameters.setCoinMultiplier(TEST_COIN_MULTIPLIER);
        this.gameParameters.setCarSpeedMultiplier(TEST_CAR_SPEED_MULTIPLIER);
        this.gameParameters.setTrainSpeedMultiplier(TEST_TRAIN_SPEED_MULTIPLIER);
        this.gameParameters.setInvincibility(TEST_INVINCIBILITY);
        this.gameParameters.setCoinCount(TEST_COIN_START_COUNT);
        this.gameParameters.setLogSpeedMultiplier(TEST_LOG_SPEED_MULTIPLIER);

        assertEquals(TEST_COIN_MULTIPLIER, this.gameParameters.getCoinMultiplier());
        assertEquals(TEST_CAR_SPEED_MULTIPLIER, this.gameParameters.getCarSpeedMultiplier());
        assertEquals(TEST_TRAIN_SPEED_MULTIPLIER, this.gameParameters.getTrainSpeedMultiplier());
        assertTrue(this.gameParameters.isInvincible());
        assertEquals(TEST_LOG_SPEED_MULTIPLIER, this.gameParameters.getLogSpeedMultiplier());
        assertEquals(TEST_COIN_START_COUNT, this.gameParameters.getCoinCount());
    }

    /**
     * Tests that invalid parameters throw IllegalArgumentException.
     */
    @Test
    void testNotValidParameters() {
        assertThrows(IllegalArgumentException.class, () ->
                this.gameParameters.setCoinMultiplier(0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                this.gameParameters.setCarSpeedMultiplier(0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                this.gameParameters.setTrainSpeedMultiplier(0)
        );
        assertThrows(IllegalArgumentException.class, () ->
                this.gameParameters.setCoinCount(-1)
        );
        assertThrows(IllegalArgumentException.class, () ->
                this.gameParameters.setLogSpeedMultiplier(0)
        );
    }

    /**
     * Tests that invalid constructor parameters throw IllegalArgumentException.
     */
    @Test
    void testNotValidConstructors() {
        assertThrows(IllegalArgumentException.class, () ->
                new GameParametersBuilder()
                        .setCoinMultiplier(0)
                        .build()
        );
        assertThrows(IllegalArgumentException.class, () ->
                new GameParametersBuilder()
                        .setCarSpeedMultiplier(0)
                        .build()
        );
        assertThrows(IllegalArgumentException.class, () ->
                new GameParametersBuilder()
                        .setTrainSpeedMultiplier(0)
                        .build()
        );
        assertThrows(IllegalArgumentException.class, () ->
                new GameParametersBuilder()
                        .setCoinCount(-1)
                        .build()
        );
        assertThrows(IllegalArgumentException.class, () ->
                new GameParametersBuilder()
                        .setLogSpeedMultiplier(0)
                        .build()
        );
    }

    /**
     * Tests the incrementCoinCount method.
     */
    @Test
    void testIncrementCoinCount() {
       this.gameParameters.setCoinMultiplier(TEST_COIN_MULTIPLIER);
       for (int i = 0; i < INCREMENT_TIMES; i++) {
           this.gameParameters.incrementCoinCount();
       }
       assertEquals(INCREMENT_TIMES * TEST_COIN_MULTIPLIER + DEFAULT_COIN_START_COUNT, this.gameParameters.getCoinCount());
    }

    /**
     * Tests loading game parameters from a file.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    void loadFromFile() throws IOException {
        final String invalidFilepath = "not_valid/file/path.json";
        final Path tmpFile = Files.createTempFile("game_parameters_file", ".json");
        Files.writeString(tmpFile, """
                    {
                     "coinMultiplier": 2,
                     "carSpeedMultiplier": 1.5,
                     "trainSpeedMultiplier": 2.0,
                     "invincibility": true,
                     "logSpeedMultiplier": 1.8,
                     "coinCount": 5
                    }
                """);

        assertThrows(IOException.class, () ->
            this.gameParameters.loadFromFile(invalidFilepath)
        );

        this.gameParameters = new GameParametersImpl().loadFromFile(tmpFile.toString());
        assertEquals(TEST_COIN_MULTIPLIER, this.gameParameters.getCoinMultiplier());
        assertEquals(TEST_CAR_SPEED_MULTIPLIER, this.gameParameters.getCarSpeedMultiplier());
        assertEquals(TEST_TRAIN_SPEED_MULTIPLIER, this.gameParameters.getTrainSpeedMultiplier());
        assertEquals(TEST_LOG_SPEED_MULTIPLIER, this.gameParameters.getLogSpeedMultiplier());
        assertTrue(this.gameParameters.isInvincible());
        assertEquals(TEST_COIN_START_COUNT, this.gameParameters.getCoinCount());

        Files.deleteIfExists(tmpFile);
    }
}
