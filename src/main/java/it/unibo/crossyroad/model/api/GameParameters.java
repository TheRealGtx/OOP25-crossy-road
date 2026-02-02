package it.unibo.crossyroad.model.api;

import java.io.IOException;

/**
 * Interface representing game parameters and settings.
 */
public interface GameParameters {

    /**
     * Sets the new coin multiplier.
     *
     * @param coinMultiplier the new coin multiplier.
     * @throws IllegalArgumentException if coinMultiplier is <= 0.
     */
    void setCoinMultiplier(int coinMultiplier);

    /**
     * Sets the new car speed multiplier.
     *
     * @param carSpeedMultiplier the new car speed multiplier.
     * @throws IllegalArgumentException if carSpeedMultiplier is <= 0.
     */
    void setCarSpeedMultiplier(double carSpeedMultiplier);

    /**
     * Sets the new train speed multiplier.
     *
     * @param trainSpeedMultiplier the new train speed multiplier.
     * @throws IllegalArgumentException if trainSpeedMultiplier is <= 0.
     */
    void setTrainSpeedMultiplier(double trainSpeedMultiplier);

    /**
     * Sets the invincibility status.
     *
     * @param invincibility true if invincible, false otherwise.
     */
    void setInvincibility(boolean invincibility);

    /**
     * Sets the current coin count.
     *
     * @param coinCount the coin count.
     * @throws IllegalArgumentException if coinCount < 0.
     */
    void setCoinCount(int coinCount);

    /**
     * Sets the new log speed multiplier.
     *
     * @param logSpeedMultiplier the new log speed multiplier.
     * @throws IllegalArgumentException if logSpeedMultiplier is <= 0.
     */
    void setLogSpeedMultiplier(double logSpeedMultiplier);

    /**
     * Gets the current coin multiplier.
     *
     * @return the coin multiplier.
     */
    int getCoinMultiplier();

    /**
     * Gets the current car speed multiplier.
     *
     * @return the car speed multiplier.
     */
    double getCarSpeedMultiplier();

    /**
     * Gets the current train speed multiplier.
     *
     * @return the train speed multiplier.
     */
    double getTrainSpeedMultiplier();

    /**
     * Checks if the player is invincible.
     *
     * @return true if invincible, false otherwise.
     */
    boolean isInvincible();

    /**
     * Gets the current log speed multiplier.
     *
     * @return the log speed multiplier.
     */
    double getLogSpeedMultiplier();

    /**
     * Gets the current coin count.
     *
     * @return the coin count.
     */
    int getCoinCount();

    /**
     * Increments the coin based on the coin multiplier.
     */
    void incrementCoinCount();

    /**
     * Loads game parameters from a json file.
     *
     * @param filepath the path to the file.
     * @return a GameParameters instance with loaded parameters.
     * @throws IOException if an I/O error occurs.
     */
    GameParameters loadFromFile(String filepath) throws IOException;
}
