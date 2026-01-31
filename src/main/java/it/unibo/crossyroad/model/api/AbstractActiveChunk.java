package it.unibo.crossyroad.model.api;

public abstract class AbstractActiveChunk extends AbstractChunk implements ActiveChunk {
    
    public AbstractActiveChunk(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(GameParameters params, long deltaTime) {
        for (Obstacle obs : this.getObstacles()) {
            if (obs instanceof ActiveObstacle) {
                ((ActiveObstacle)obs).update(deltaTime, params);
            }
        }
    }
}
