package it.unibo.crossyroad.model.impl;

import java.util.Random;

import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;

/**
 * Chunk representing a railway where trains can move.
 */
public final class Railway extends AbstractActiveChunk {
    private static final Random RND = new Random();
    private static final int MAX_TRAINS = 3;
    private static final long SPAWN_INTERVAL_MS = 1400;

    private final Direction direction;
    private final double speed;
    private long elapsedTime;

    /**
     * Initializes the Chunk.
     *
     * @param initialPosition the ActiveChunk's initial position.
     *
     * @param dimension the ActiveChunk's dimension.
     */
    public Railway(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
        this.direction = RND.nextBoolean() ? Direction.LEFT : Direction.RIGHT;
        this.speed = RND.nextDouble(8, 10);
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldGenerateNewObstacles(final long deltaTime) {
        this.elapsedTime += deltaTime;

        if (this.getObstacles().isEmpty()) {
            return true;
        }
        if (elapsedTime >= SPAWN_INTERVAL_MS && this.getObstacles().size() < MAX_TRAINS) {
            this.elapsedTime = 0;
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        final int y = (int) Math.round(this.getPosition().y() + RND.nextDouble(getDimension().height() - 1));
        final double x = direction == Direction.LEFT
                ? this.getPosition().x() + this.getDimension().width() + 10
                : this.getPosition().x() - 10;
        this.addObstacle(new Train(new Position(x, y), speed, direction));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.RAILWAY;
    }
}
