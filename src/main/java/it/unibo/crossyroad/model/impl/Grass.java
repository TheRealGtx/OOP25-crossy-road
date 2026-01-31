package it.unibo.crossyroad.model.impl;

import java.util.Random;

import it.unibo.crossyroad.model.api.AbstractChunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;

public class Grass extends AbstractChunk {
    private final Random rnd = new Random();

    public Grass(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GRASS;
    }

    @Override
    protected void generateObstacles() {
        final double x_limit = this.getPosition().x() + this.getDimension().height();
        final double y_limit = this.getPosition().y() + this.getDimension().width();

        for (int i = 0; i < this.rnd.nextInt(16); i++) {
            final Position randomPosition = new Position(this.rnd.nextDouble(x_limit), this.rnd.nextDouble(y_limit));

            switch (this.rnd.nextInt(2)) {
                case 0:
                    this.addObstacle(new Tree(randomPosition, new Dimension(1, 1)));
                    break;
                case 1:
                    this.addObstacle(new Rock(randomPosition, new Dimension(1, 1)));
                    break;
            }
        }
    }

    @Override
    public void init() {
        this.clearObstacles();
        this.generateObstacles();
    }
}
