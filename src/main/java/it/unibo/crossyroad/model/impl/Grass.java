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
    private static final Position PLAYER_START_POSITION = new Position(5, 8);
    private static final Double COMPARING_DELTA = 0.0001;
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
        this.init();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GRASS;
    }

    @Override
    protected void generateObstacles() {
        for (int i = 0; i < this.rnd.nextInt(16); i++) {
            final int relativeX = this.rnd.nextInt((int) this.getDimension().width());
            final int relativeY = this.rnd.nextInt((int) this.getDimension().height());
            final Position randomPosition = new Position(this.getPosition().x() + relativeX, this.getPosition().y() + relativeY);

            //Leave at least a path
            if (Math.abs(randomPosition.x() - PLAYER_START_POSITION.x()) < COMPARING_DELTA
                && !this.getPositionables().stream().anyMatch(p -> p.getPosition().equals(randomPosition))) {
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
    }
}
