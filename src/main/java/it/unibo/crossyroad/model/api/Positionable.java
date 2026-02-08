package it.unibo.crossyroad.model.api;

/**
 * An interface representing an entity that has a position in a 2D space.
 */
public interface Positionable {
    /**
     * Get the position of the entity.
     *
     * @return the position of the entity
     */
    Position getPosition();

    /**
     * Get the width and height of the entity as a Dimension object.
     *
     * @return the width and height of the entity
     */
    Dimension getDimension();

    /**
     * Shift the y coordinate of the entity of a given delta (positive).
     *
     * @param delta the delta to shift the y coordinate
     * @throws IllegalArgumentException if delta is negative
     */
    void increaseY(double delta);

    /**
     * It returns the specific type of the entity, useful to identify it.
     *
     * @return the type of the entity
     */
    EntityType getEntityType();

    /**
     * Checks if the Positionable contains a given position.
     *
     * @param position the position to check
     * @return true if the Positionable contains the position
     */
    boolean contains(Position position);

    /**
     * Checks if two Positionables collide with each other.
     * 
     * @param second the second Positionable.
     * @return true if the Positionables collide, false otherwise.
     */
    boolean overlaps(Positionable second);
}
