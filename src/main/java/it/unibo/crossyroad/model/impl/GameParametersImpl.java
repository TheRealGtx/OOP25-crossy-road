package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.GameParameters;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of the GameParameters interface.
 */
public class GameParametersImpl implements GameParameters {
    private int coinMultiplier;
    private double carSpeedMultiplier;
    private double trainSpeedMultiplier;
    private boolean invincibility;
    private int coinCount;

    /**
     * Constructor to initialize game parameters using {@link GameParametersBuilder} to create instance.
     * Before using this constructor, ensure that the parameters are validated with the apposite function.
     *
     * @param coinMultiplier the coin multiplier.
     * @param carSpeedMultiplier the car speed multiplier.
     * @param trainSpeedMultiplier the train speed multiplier.
     * @param invincibility the invincibility status.
     * @param coinCount the initial coin count.
     */
    public GameParametersImpl(final int coinMultiplier, final double carSpeedMultiplier,
                              final double trainSpeedMultiplier, final boolean invincibility,
                              final int coinCount) {
        validateParameters(coinMultiplier, carSpeedMultiplier, trainSpeedMultiplier, coinCount);
        this.coinMultiplier = coinMultiplier;
        this.carSpeedMultiplier = carSpeedMultiplier;
        this.trainSpeedMultiplier = trainSpeedMultiplier;
        this.invincibility = invincibility;
        this.coinCount = coinCount;
    }

    /**
     * Default constructor initializing parameters to default values.
     */
    public GameParametersImpl() {
        this(1, 1.0, 1.0, false, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCoinMultiplier(final int coinMultiplier) {
        if (coinMultiplier < 1) {
            throw new IllegalArgumentException("Coin multiplier must be >= 1");
        }
        this.coinMultiplier = coinMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCarSpeedMultiplier(final double carSpeedMultiplier) {
        if (carSpeedMultiplier <= 0.0) {
            throw new IllegalArgumentException("Car speed multiplier must be > 0.0");
        }
        this.carSpeedMultiplier = carSpeedMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrainSpeedMultiplier(final double trainSpeedMultiplier) {
        if (trainSpeedMultiplier <= 0.0) {
            throw new IllegalArgumentException("Train speed multiplier must be > 0.0");
        }
        this.trainSpeedMultiplier = trainSpeedMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInvincibility(final boolean invincibility) {
        this.invincibility = invincibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCoinCount(final int coinCount) {
        if (coinCount < 0) {
            throw new IllegalArgumentException("Coin count must be >= 0");
        }
        this.coinCount = coinCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinMultiplier() {
        return this.coinMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCarSpeedMultiplier() {
        return this.carSpeedMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTrainSpeedMultiplier() {
        return this.trainSpeedMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvincible() {
        return this.invincibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCount() {
        return this.coinCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCoinCount() {
        this.coinCount += this.coinMultiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameParameters loadFromFile(final String filepath) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final GameParameters newParameters = mapper.readValue(new File(filepath), GameParametersImpl.class);
        validateParameters(newParameters.getCoinMultiplier(), newParameters.getCarSpeedMultiplier(),
                newParameters.getTrainSpeedMultiplier(), newParameters.getCoinCount());
        return newParameters;
    }

    /**
     * Validates the game parameters.
     *
     * @param coinMult the coin multiplier.
     * @param carSpeedMult the car speed multiplier.
     * @param trainSpeedMult the train speed multiplier.
     * @param coinStart the coin start count.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    private void validateParameters(final int coinMult, final double carSpeedMult,
                                       final double trainSpeedMult, final int coinStart) {
        if (coinMult < 1) {
            throw new IllegalArgumentException("Coin multiplier must be >= 1");
        }
        if (carSpeedMult <= 0.0 || trainSpeedMult <= 0.0) {
            throw new IllegalArgumentException("Speed multipliers must be > 0.0");
        }
        if (coinStart < 0) {
            throw new IllegalArgumentException("Coin count must be >= 0");
        }
    }
}
