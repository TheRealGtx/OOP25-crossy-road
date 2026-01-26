package it.unibo.crossyroad.model.impl;

import java.util.Random;

import it.unibo.crossyroad.model.api.AbstractChunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;

/**
 * Chunk without active obstacles on it, only passive ones.
 */
public final class Grass extends AbstractChunk {
    private final Random rnd = new Random();

    /**
     * Initializes the Chunk.
     * 
     * @param initialPosition the Chunk's initial position.
     * 
     * @param dimension the Chunk's dimension.
     */
    public Grass(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GRASS;
    }

    @Override
    protected void generateObstacles() {
        final double xLimit = this.getPosition().x() + this.getDimension().height();
        final double yLlimit = this.getPosition().y() + this.getDimension().width();

        for (int i = 0; i < this.rnd.nextInt(16); i++) {
            final Position randomPosition = new Position(this.rnd.nextDouble(xLimit), this.rnd.nextDouble(yLlimit));

            switch (this.rnd.nextInt(2)) {
                case 0:
                    this.addObstacle(new Tree(randomPosition, new Dimension(1, 1)));
                    break;
                case 1:
                    this.addObstacle(new Rock(randomPosition, new Dimension(1, 1)));
                    break;
                default:
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
