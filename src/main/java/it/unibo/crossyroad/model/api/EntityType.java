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
    COIN_MULTIPLIER("Coin Multiplier"),
    SLOW_CARS("Slow Cars"),
    INVINCIBILITY("Invincibility"),
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
}
