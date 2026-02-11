package it.unibo.crossyroad.model.impl;

import java.util.Random;

import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Pair;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;

/**
 * Chunk representing a road where car can move.
 */
public final class Road extends AbstractActiveChunk {
    private static final int MAX_CARS_PER_CHUNKS = 8;
    private static final long SPAWN_CAR_INTERVAL_MS = 1250;
    private static final int MAX_SPEED = 5;
    private static final int MIN_SPEED = 3;
    private static final Random RND = new Random();

    private final Pair<Double, Double> laneSpeed;
    private long elapsedTime;

    /**
     * Initializes the Chunk.
     *
     * @param initialPosition the ActiveChunk's initial position.
     *
     * @param dimension the ActiveChunk's dimension.
     */
    public Road(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
        this.laneSpeed = new Pair<>(RND.nextDouble(MIN_SPEED, MAX_SPEED), RND.nextDouble(MIN_SPEED, MAX_SPEED));
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldGenerateNewObstacles(final long deltaTime, final GameParameters params) {
        this.elapsedTime += deltaTime;
        if (this.getObstacles().size() >= MAX_CARS_PER_CHUNKS) {
            return false;
        }

        final long adjustedInterval = (long) (SPAWN_CAR_INTERVAL_MS / params.getCarSpeedMultiplier());

        if (elapsedTime >= adjustedInterval || this.getObstacles().isEmpty()) {
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
        final boolean isLeftLane = RND.nextBoolean();
        final Direction dir = isLeftLane ? Direction.LEFT : Direction.RIGHT;
        final double speed = isLeftLane ? this.laneSpeed.e2() : this.laneSpeed.e1();
        final double y = this.getPosition().y() + (isLeftLane ? 1 : 2);

        final double x = dir == Direction.RIGHT
                ? this.getPosition().x() - 2
                : this.getPosition().x() + this.getDimension().width() + 2;

        this.addObstacle(new Car(new Position(x, y), speed, dir));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ROAD;
    }
}
