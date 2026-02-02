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
    private double logSpeedMultiplier;
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
     * @param logSpeedMultiplier the log multiplier.
     */
    public GameParametersImpl(final int coinMultiplier, final double carSpeedMultiplier,
                              final double trainSpeedMultiplier, final boolean invincibility,
                              final double logSpeedMultiplier, final int coinCount) {
        validateParameters(coinMultiplier, carSpeedMultiplier, trainSpeedMultiplier, logSpeedMultiplier, coinCount);
        this.coinMultiplier = coinMultiplier;
        this.carSpeedMultiplier = carSpeedMultiplier;
        this.trainSpeedMultiplier = trainSpeedMultiplier;
        this.invincibility = invincibility;
        this.logSpeedMultiplier = logSpeedMultiplier;
        this.coinCount = coinCount;
    }

    /**
     * Default constructor initializing parameters to default values.
     */
    public GameParametersImpl() {
        this(1, 1.0, 1.0, false, 1.0, 0);
    }

    /**
     * Copy constructor.
     *
     * @param p the GameParameters instance to copy from.
     */
    public GameParametersImpl(final GameParameters p) {
        this(p.getCoinMultiplier(), p.getCarSpeedMultiplier(), p.getTrainSpeedMultiplier(),
                p.isInvincible(), p.getLogSpeedMultiplier(), p.getCoinCount());
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
    public void setLogSpeedMultiplier(final double logSpeedMultiplier) {
        if (logSpeedMultiplier <= 0.0) {
            throw new IllegalArgumentException("Log speed multiplier must be > 0.0");
        }
        this.logSpeedMultiplier = logSpeedMultiplier;
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
    public double getLogSpeedMultiplier() {
        return this.logSpeedMultiplier;
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
                newParameters.getTrainSpeedMultiplier(), newParameters.getLogSpeedMultiplier(), newParameters.getCoinCount());
        return newParameters;
    }

    /**
     * Validates the game parameters.
     *
     * @param coinMult the coin multiplier.
     * @param carSpeedMult the car speed multiplier.
     * @param trainSpeedMult the train speed multiplier.
     * @param coinStart the coin start count.
     * @param logSpeedMult he log speed multiplier.
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    private void validateParameters(final int coinMult, final double carSpeedMult,
                                       final double trainSpeedMult, final double logSpeedMult, final int coinStart) {
        if (coinMult < 1) {
            throw new IllegalArgumentException("Coin multiplier must be >= 1");
        }
        if (carSpeedMult <= 0.0 || trainSpeedMult <= 0.0) {
            throw new IllegalArgumentException("Speed multipliers must be > 0.0");
        }
        if (logSpeedMult <= 0.0) {
            throw new IllegalArgumentException("Log speed multiplier must be > 0.0");
        }
        if (coinStart < 0) {
            throw new IllegalArgumentException("Coin count must be >= 0");
        }
    }
}
