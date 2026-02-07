package it.unibo.crossyroad.model.impl;

import java.util.Objects;

import it.unibo.crossyroad.model.api.AbstractPickable;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;

/**
 * A class representig a coin in the game.
 */
public class Coin extends AbstractPickable {

    /**
     * It creates a new pickable (coin) with the initial position.
     * 
     * @param position the initial position of the coin.
     */
    public Coin(final Position position) {
        super(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void applyEffect(final GameParameters gameParameters) {
        gameParameters.incrementCoinCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.COIN;
    }

    /**
     * Checks whether this coin is equal to another object.
     *
     * @param o the object to compare with.
     * @return true if the given object is considered equal to this coin, false otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Coin other = (Coin) o;

        return Objects.equals(this.getDimension(), other.getDimension())
                && Objects.equals(this.getPosition(), other.getPosition())
                && this.getEntityType() == other.getEntityType();
    }

    /**
     * Computes the hash code for this coin.
     *
     * @return an integer hash code consistent with equals
     */
    @Override
    public int hashCode() {
        return Objects.hash(getEntityType(), getPosition(), getDimension());
    }
}
