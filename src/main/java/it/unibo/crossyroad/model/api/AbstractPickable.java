package it.unibo.crossyroad.model.api;

/**
 * An abstract class representing a pickable that has a position in a 2D space.
 */
public abstract class AbstractPickable extends AbstractPositionable implements Pickable {

    /**
     * Indicates if the power-up has been picked up.
     */
    private boolean pickedUp;

    /** 
     * It creates a new pickable with the given initial position.
     * 
     * @param position the initial position of the active pickable.
     */
    public AbstractPickable(final Position position) {
        super(position, new Dimension(0.3, 0.3));
        this.pickedUp = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPickedUp() {
        return this.pickedUp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pickUp(final GameParameters gameParameters) {
        if (!this.pickedUp) {
            this.pickedUp = true;
            this.applyEffect(gameParameters);
        }
    }

    /**
     * Apply the specific effect of this pickable to the game parameter.
     * 
     * @param gameParameters the game parameters affected by this pickable.
     */
    protected abstract void applyEffect(GameParameters gameParameters);

}
