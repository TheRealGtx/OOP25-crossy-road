package it.unibo.crossyroad.model.impl;

import java.util.LinkedList;
import java.util.List;

import it.unibo.crossyroad.model.api.AbstractPositionable;
import it.unibo.crossyroad.model.api.ActiveObstacle;
import it.unibo.crossyroad.model.api.Chunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

public abstract class AbstractChunk extends AbstractPositionable implements Chunk {

    public AbstractChunk(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
    }

    private List<Obstacle> obstacles;

    public void init() {
        this.obstacles = new LinkedList<>();
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public abstract void genObstacles();

    private void moveObstacles() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof ActiveObstacle) {
                //((ActiveObstacle)obstacle).update(gameParameters); //TODO
            }
        }
    }
    
    //TODO rest
}
