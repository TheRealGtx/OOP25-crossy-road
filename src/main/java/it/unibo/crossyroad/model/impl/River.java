package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.ActiveObstacle;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

/**
 * Class representing a River chunk in the game.
 */
public class River extends AbstractActiveChunk {
    private static final int LOGS_DISTANCE = 3;
    private static final double LOGS_SPEED = 1.0;
    private static final int LOGS_LENGTH = 4;

    private final Direction direction;
    private final long logInterval;

    /**
     * Constructor for River.
     *
     * @param initialPosition The initial position of the River chunk.
     * @param dimension The dimension of the River chunk.
     * @param direction The direction of the logs in the river.
     */
    public River(final Position initialPosition, final Dimension dimension, final Direction direction) {
        super(initialPosition, dimension);

        if (direction != Direction.LEFT && direction != Direction.RIGHT) {
            throw new IllegalArgumentException("Direction must be LEFT or RIGHT for River logs.");
        }

        this.direction = direction;
        this.logInterval = (long) (LOGS_DISTANCE / LOGS_SPEED * 1000);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.addWater();
        this.generateObstacles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long getGenerationInterval() {
        return this.logInterval;
    }

    private void addWater() {
        final Obstacle water = new Water(this.getPosition(), this.getDimension());

        this.addObstacle(water);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        final ActiveObstacle log = new WoodLog(
            LOGS_SPEED,
            this.direction,
            this.getLogStartPosition(),
            LOGS_LENGTH
        );

        this.addObstacle(log);
    }

    private Position getLogStartPosition() {
        return switch (this.direction) {
            case LEFT -> new Position(this.getPosition().x() + this.getDimension().width(), this.getPosition().y());
            case RIGHT -> new Position(this.getPosition().x() - LOGS_LENGTH, this.getPosition().y());
            default -> throw new IllegalStateException("Direction must be LEFT or RIGHT");
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.RIVER;
    }
}
