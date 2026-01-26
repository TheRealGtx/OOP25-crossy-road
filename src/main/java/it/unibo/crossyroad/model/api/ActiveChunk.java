package it.unibo.crossyroad.model.api;

/**
 * Represents a Chunk with active obstacles on it.
 */
public interface ActiveChunk extends Chunk {
    /**
     * Updates the elements present on the Chunk and the Chunk itself.
     * 
     * @param params the GameParameters.
     * 
     * @param deltaTime time since last update.
     * 
     * @see GameParameters
     */
    void update(GameParameters params, long deltaTime);
}
