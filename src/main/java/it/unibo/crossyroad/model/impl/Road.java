package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.Pair;

import java.util.Random;

/**
 * Chunk representing a road where car can move.
 */
public class Road extends AbstractActiveChunk {
    private static final int MAX_CARS_PER_CHUNKS = 8;
    private static final long SPAWN_CAR_INTERVAL_MS = 1200;
    private static final int MAX_SPEED = 5;
    private static final int MIN_SPEED = 1;
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldGenerateNewObstacles(final long deltaTime) {
        this.elapsedTime += deltaTime;
        final int activeCars = (int) getObstacles().stream()
                .filter(obs -> obs instanceof Car)
                .count();
        if (elapsedTime >= SPAWN_CAR_INTERVAL_MS && activeCars < MAX_CARS_PER_CHUNKS) {
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
        final Direction dir;
        final double speed;
        final int lane = RND.nextInt(2);

        switch (lane) {
            case 0:
                dir = Direction.LEFT;
                speed = this.laneSpeed.e2();
                break;
            case 1:
                dir = Direction.RIGHT;
                speed = this.laneSpeed.e1();
                break;
            default:
                dir = null;
                speed = 0.0;
                break;
        }

        final double laneHeight = this.getDimension().height() / 2;

        final double y = this.getPosition().y() - lane * laneHeight;
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
