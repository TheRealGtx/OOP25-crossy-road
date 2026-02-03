package it.unibo.crossyroad.model.impl;

/**
 * Builder class for {@link GameParametersImpl}.
 */
public class GameParametersBuilder {
    private int coinMultiplier = 1;
    private double carSpeedMultiplier = 1.0;
    private double trainSpeedMultiplier = 1.0;
    private boolean invincibility;
    private double logSpeedMultiplier = 1.0;
    private int coinCount;

    /**
     * Sets the coin multiplier.
     *
     * @param multiplier the coin multiplier
     * @return the builder instance
     */
    public GameParametersBuilder setCoinMultiplier(final int multiplier) {
        this.coinMultiplier = multiplier;
        return this;
    }

    /**
     * Sets the car speed multiplier.
     *
     * @param multiplier the car speed multiplier
     * @return the builder instance
     */
    public GameParametersBuilder setCarSpeedMultiplier(final double multiplier) {
        this.carSpeedMultiplier = multiplier;
        return this;
    }

    /**
     * Sets the train speed multiplier.
     *
     * @param multiplier the train speed multiplier
     * @return the builder instance
     */
    public GameParametersBuilder setTrainSpeedMultiplier(final double multiplier) {
        this.trainSpeedMultiplier = multiplier;
        return this;
    }

    /**
     * Sets the invincibility status.
     *
     * @param isInvincible true if invincible, false otherwise
     * @return the builder instance
     */
    public GameParametersBuilder setInvincibility(final boolean isInvincible) {
        this.invincibility = isInvincible;
        return this;
    }

    /**
     * Sets the log speed multiplier.
     *
     * @param multiplier the log speed multiplier
     * @return the builder instance
     */
    public GameParametersBuilder setLogSpeedMultiplier(final double multiplier) {
        this.logSpeedMultiplier = multiplier;
        return this;
    }

    /**
     * Sets the coin count.
     *
     * @param coin the init coin.
     * @return the builder instance
     */
    public GameParametersBuilder setCoinCount(final int coin) {
        this.coinCount = coin;
        return this;
    }

    /**
     * Builds the {@link GameParametersImpl} instance.
     *
     * @return the constructed GameParametersImpl
     */
    public GameParametersImpl build() {
        return new GameParametersImpl(
                this.coinMultiplier,
                this.carSpeedMultiplier,
                this.trainSpeedMultiplier,
                this.invincibility,
                this.logSpeedMultiplier,
                this.coinCount
        );
    }
}
