package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.WoodLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestWoodLog {
    private static final Dimension DIMENSION = new Dimension(4.0, 1.0);
    private static final Position LEFT_INITIAL_POSITION = new Position(-DIMENSION.width(), 0);
    private static final Position RIGHT_INITIAL_POSITION = new Position(10, 0);
    private static final double MAX_SPEED = 5.0;
    private static final double MIN_SPEED = 1;

    private static final double MAX_POSITION_DELTA = 0.0001;
    private static final int MAX_DELTA_TIME = 10_000;

    private final Random random = new Random();
    private final GameParameters gameParameters = new GameParametersImpl();
    private double speed;
    private Direction direction;
    private WoodLog woodLog;

    /**
     * Sets up a new random WoodLog instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.speed = random.nextDouble() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED;
        this.direction = random.nextBoolean() ? Direction.LEFT : Direction.RIGHT;
        this.woodLog = new WoodLog(
            direction == Direction.LEFT ? RIGHT_INITIAL_POSITION : LEFT_INITIAL_POSITION,
            DIMENSION,
            this.speed,
            direction
        );
    }

    @Test
    void testWoodLogInit() {
        assertEquals(direction == Direction.LEFT ? RIGHT_INITIAL_POSITION : LEFT_INITIAL_POSITION, woodLog.getPosition());
        assertEquals(DIMENSION, woodLog.getDimension());
        assertEquals(EntityType.WOOD_LOG, woodLog.getEntityType());
        assertEquals(CollisionType.TRANSPORT, woodLog.getCollisionType());
    }

    @Test
    void testPositionAfterUpdate() {
        final long randomDeltaTime = random.nextInt(MAX_DELTA_TIME);
        final Position initialPosition = woodLog.getPosition();

        final double expectedDeltaX = speed * gameParameters.getLogSpeedMultiplier() * (randomDeltaTime / 1000.0);
        final double expectedX = initialPosition.x() + expectedDeltaX * (direction == Direction.LEFT ? -1 : 1);
        woodLog.update(randomDeltaTime, gameParameters);

        assertEquals(expectedX, woodLog.getPosition().x(), MAX_POSITION_DELTA);
        assertEquals(initialPosition.y(), woodLog.getPosition().y());
    }

    @Test
    void testPositionAfterMultipleUpdates() {
        final List<Long> deltaTimes = List.of(500L, 1000L, 1500L, 2000L);
        final Position initialPosition = woodLog.getPosition();
        double totalDeltaX = 0.0;

        for (final long deltaTime : deltaTimes) {
            final double deltaX = speed * gameParameters.getLogSpeedMultiplier() * (deltaTime / 1000.0);
            totalDeltaX += direction == Direction.LEFT ? -deltaX : deltaX;
            woodLog.update(deltaTime, gameParameters);
        }

        assertEquals(initialPosition.x() + totalDeltaX, woodLog.getPosition().x(), MAX_POSITION_DELTA);
        assertEquals(initialPosition.y(), woodLog.getPosition().y());
    }

    @Test
    void testPositionAfterZeroDeltaTime() {
        final Position initialPosition = woodLog.getPosition();
        woodLog.update(0, gameParameters);
        assertEquals(initialPosition.x(), woodLog.getPosition().x());
        assertEquals(initialPosition.y(), woodLog.getPosition().y());
    }
}
