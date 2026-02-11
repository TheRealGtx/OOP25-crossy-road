package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.ActiveObstacle;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Obstacle;
import it.unibo.crossyroad.model.api.Position;

/**
 * Class representing a River chunk in the game.
 */
public final class River extends AbstractActiveChunk {
    public static final int LOGS_DISTANCE = 6;
    public static final double LOGS_SPEED = 1.0;
    private static final int LOGS_LENGTH = 3;
    private static final int CHUNK_SECTIONS = 3;
    private static final long LOG_INTERVAL = Math.round(LOGS_DISTANCE / LOGS_SPEED * 1000);

    private final double sectionHeight;
    private final Direction direction;
    private long timeSinceLastGeneration;

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

        this.sectionHeight = this.getDimension().height() / CHUNK_SECTIONS;
        this.direction = direction;
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        this.addWater();
        super.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldGenerateNewObstacles(final long deltaTime, final GameParameters params) {
        this.timeSinceLastGeneration += deltaTime;

        if (this.timeSinceLastGeneration >= LOG_INTERVAL) {
            this.timeSinceLastGeneration = 0;
            return true;
        }
        return false;
    }

    private void addWater() {
        final Obstacle water = new Water(
            Position.of(0, this.sectionHeight).relative(this.getPosition()),
            Dimension.of(this.getDimension().width(), this.sectionHeight)
        );
        this.addObstacle(water);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        final ActiveObstacle log = new WoodLog(
            this.getLogStartPosition(),
            Dimension.of(LOGS_LENGTH, this.sectionHeight),
            LOGS_SPEED,
            this.direction
        );

        this.addObstacle(log);
    }

    private Position getLogStartPosition() {
        return switch (this.direction) {
            case LEFT -> Position.of(this.getDimension().width(), this.sectionHeight).relative(this.getPosition());
            case RIGHT -> Position.of(-LOGS_LENGTH, this.sectionHeight).relative(this.getPosition());
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
