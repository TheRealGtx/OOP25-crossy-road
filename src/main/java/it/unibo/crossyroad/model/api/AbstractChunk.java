package it.unibo.crossyroad.model.api;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractChunk extends AbstractPositionable implements Chunk {
    
    private final List<Obstacle> obstacles;

    public AbstractChunk(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
        this.obstacles = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void init();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Obstacle> getObstacles() { 
        return List.copyOf(this.obstacles);
    }

    /**
     * Generates random Obstacles objects on the Chunk.
     */
    protected abstract void generateObstacles();

    /**
     * Adds a new obstacle to the list.
     * 
     * @param obs the obstacle to add to the list.
     * 
     * @see Obstacle.
     */
    protected void addObstacle(Obstacle obs) {
        this.obstacles.add(obs);
    }

    protected void clearObstacles() {
        this.obstacles.clear();
    }

    /**
     * Generates random Pickables objects on the Chunk.
     */
    private void generatePickables() { }

    /**
     * Updates the positions of the Obstacles on the Chunk.
     */
    private void moveObstacles(GameParameters g, long deltaTime) {
        for (Obstacle obs : this.obstacles) {
            if (obs instanceof ActiveObstacle) {
                ((ActiveObstacle)obs).update(deltaTime, g.getCarSpeedMultiplier()); //TODO pass all parameters.
            }
        }
    }

    /**
     * Updates the positions of the Pickables on the Chunk.
     */
    private void movePickables() {}
}
