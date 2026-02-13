package it.unibo.crossyroad.model.api;

/**
 * Enum representing an entity type.
 */
public enum EntityType {
    RAILWAY("Railway"),
    GRASS("Grass"),
    ROAD("Road"),
    TRAIN_LEFT("Train direction left"),
    TRAIN_RIGHT("Train direction right"),
    RIVER("River"),
    CAR_LEFT("Car direction left"),
    CAR_RIGHT("Car direction right"),
    ROCK("Rock"),
    TREE("Tree"),
    WOOD_LOG("Wood log"),
    WATER("Water"),
    COIN_MULTIPLIER("COIN MULTIPLIER"),
    SLOW_CARS("SLOW CARS"),
    INVINCIBILITY("INVINCIBILITY"),
    COIN("Coin"),
    PLAYER("Player");

    private final String displayName;

    EntityType(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of this entity type.
     *
     * @return the name of this entity type.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the current EntityType is an obstacle.
     * 
     * @return ture if it's a obstacle
     */
    public boolean isObstacle() {
        return this == CAR_LEFT || this == CAR_RIGHT || this == TRAIN_LEFT
                || this == TRAIN_RIGHT || this == WATER || this == WOOD_LOG
                || this == TREE || this == ROCK;
    }

    /**
     * Checks if the current EntityType is a powerup.
     * 
     * @return ture if it's a powerup
     */
    public boolean isPowerup() {
        return this == SLOW_CARS || this == INVINCIBILITY 
                || this == COIN_MULTIPLIER;
    }

    /**
     * Checks if the current EntityType is a chunk.
     * 
     * @return ture if it's a chunk
     */
    public boolean isChunk() {
        return this == GRASS || this == RAILWAY 
            || this == ROAD || this == RIVER;
    }
}
