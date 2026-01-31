package it.unibo.crossyroad.model.impl;

import it.unibo.crossyroad.model.api.AbstractActiveChunk;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.GameParameters;

import java.util.Random;

/**
 * Chunk representing a railway where trains can move.
 */
public class Railway extends AbstractActiveChunk {

    private final Direction direction;
    private final double speed;
    private final Random rnd = new Random();

    /**
     * Initializes the Chunk.
     *
     * @param initialPosition the ActiveChunk's initial position.
     *
     * @param dimension the ActiveChunk's dimension.
     */
    public Railway(final Position initialPosition, final Dimension dimension) {
        super(initialPosition, dimension);
        this.direction = rnd.nextBoolean() ? Direction.LEFT : Direction.RIGHT;
        this.speed = rnd.nextDouble(8, 10);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    protected void spawnIfNeeded(final long deltaTime) {
        final boolean hasTrain = this.getObstacles().stream()
                .anyMatch(obs -> obs instanceof Train);
        if (!hasTrain) {
            spawnTrain();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removeOutOfBoundObstacles() {
        this.getObstacles().removeIf(obs -> obs instanceof Train &&
                (direction == Direction.LEFT && obs.getPosition().x() > this.getPosition().x() + this.getDimension().width() + 10 ||
                 direction == Direction.RIGHT && obs.getPosition().x() < this.getPosition().x() - 10));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void generateObstacles() {
        spawnTrain();
    }

    /**
     * Spawns a train at a random position on the railway chunk.
     */
    private void spawnTrain() {
        final double y = this.getPosition().y() + rnd.nextDouble(getDimension().height());
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
        return EntityType.TRAIN;
    }
}
