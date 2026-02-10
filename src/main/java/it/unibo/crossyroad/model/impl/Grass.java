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
    private static final int MIN_OBSTACLES_NUMBER = 5;
    private static final int MAX_OBSTACLES_NUMBER = 16;
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

    /**
     * Initializes the Chunk.
     * 
     * @param initialPosition the Chunk's initial position.
     * 
     * @param dimension the Chunk's dimension.
     * 
     * @param firstChunk tells if the Chunk is part of the first set of Chunks of the game.
     */
    public Grass(final Position initialPosition, final Dimension dimension, final boolean firstChunk) {
        super(initialPosition, dimension, firstChunk);
        this.init();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GRASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        for (int i = 0; i < this.rnd.nextInt(MIN_OBSTACLES_NUMBER, MAX_OBSTACLES_NUMBER); i++) {
            final int relativeX = this.rnd.nextInt((int) this.getDimension().width());
            final int relativeY = this.rnd.nextInt((int) this.getDimension().height());
            final Position randomPosition = new Position(this.getPosition().x() + relativeX, this.getPosition().y() + relativeY);

            if (!this.getPositionables().stream().anyMatch(p -> p.getPosition().equals(randomPosition))
                && !(this.isFirstChunk() && randomPosition.equals(PLAYER_START_POSITION))) {
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
