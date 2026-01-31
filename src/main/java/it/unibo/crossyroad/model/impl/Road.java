package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.AbstractActiveChunk;


import java.util.Random;

/**
 * Chunk representing a road where car can move.
 */
public class Road extends AbstractActiveChunk {
    private static final int MAX_CARS_PER_CHUNKS = 8;
    private static final long SPAWN_CAR_INTERVAL_MS = 1200;

    private final double[] laneSpeed;
    private final Random rnd = new Random();
    private long elapsedTime = 0;

    /**
     * Initializes the Chunk.
     *
     * @param initialPosition the ActiveChunk's initial position.
     *
     * @param dimension the ActiveChunk's dimension.
     */
    public Road(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
        this.laneSpeed = new double[] { rnd.nextDouble(2, 5), rnd.nextDouble(1, 4) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void spawnIfNeeded(final long deltaTime) {
        this.elapsedTime += deltaTime;

        int activeCars = (int) getObstacles().stream()
                .filter(obs -> obs instanceof Car)
                .count();

        if (elapsedTime >= SPAWN_CAR_INTERVAL_MS && activeCars < MAX_CARS_PER_CHUNKS) {
            spawnCars();
            elapsedTime = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removeOutOfBoundObstacles() {
        this.getObstacles().removeIf(obs -> obs instanceof Car &&
                (obs.getPosition().x() < getPosition().x() - 5 ||
                 obs.getPosition().x() > getPosition().x() + getDimension().width() + 5));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        spawnCars();
    }

    /**
     * Spawns cars in random lanes with random speeds.
     * It calculates the lane and speed, then creates a new Car
     * object and adds it to the ActiveChunk's obstacles.
     */
    private void spawnCars() {
        final int lane = this.rnd.nextInt(2);

        final double speed = this.laneSpeed[lane];

        final double laneHeight = this.getDimension().height() / 2;

        final Direction dir = lane == 0 ? Direction.LEFT : Direction.RIGHT;

        final double y = getPosition().y() + lane * laneHeight + this.rnd.nextDouble(laneHeight);
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
