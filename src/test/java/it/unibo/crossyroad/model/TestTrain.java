package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the {@link Train} class.
 */
class TestTrain {
    private static final Position INITIAL_POSITION_TRAIN = new Position(0, 4);
    private static final double SPEED = 8.0;
    private static final long DELTA_TIME = 100;
    private static final int UPDATES_COUNT = 5;
    private static final double DELTA_DOUBLE = 0.0001;

    private Train train;
    private GameParameters gameParameters;

    /**
     * Sets up a Train instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.train = new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.RIGHT);
        this.gameParameters = new GameParametersImpl();
    }

    /**
     * Tests that creating a Train with UP or DOWN direction throws an IllegalArgumentException.
     */
    @Test
    void testTrainUpOrDownDirectionNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.UP);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.DOWN);
        });
    }

    /**
     * Tests the collision type and entity type of the Train.
     */
    @Test
    void testCollisionTypeAndEntityType() {
        assertEquals(CollisionType.DEADLY, this.train.getCollisionType());
        assertEquals(EntityType.TRAIN_RIGHT, this.train.getEntityType());
    }

    /**
     * Tests that creating a Train with zero speed throws an IllegalArgumentException.
     */
    @Test
    void testTrainZeroSpeedNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, 0.0, Direction.RIGHT);
        });
    }

    /**
     * Tests the initial position of the Train.
     */
    @Test
    void testInitialPositionTrain() {
        assertEquals(INITIAL_POSITION_TRAIN, this.train.getPosition());
    }

    /**
     * Tests the movement of the Train after a single update.
     */
    @Test
    void testMoveTrain() {
        this.train.update(DELTA_TIME, gameParameters);
        final double deltaX = SPEED * gameParameters.getTrainSpeedMultiplier() * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(INITIAL_POSITION_TRAIN.x() + deltaX, INITIAL_POSITION_TRAIN.y());
        assertEquals(expectedPosition, this.train.getPosition());
    }

    /**
     * Tests the movement of the Train after multiple updates.
     */
    @Test
    void testMultipleUpdates() {
        final double deltaX = SPEED * gameParameters.getTrainSpeedMultiplier() * DELTA_TIME / 1000.0 * UPDATES_COUNT;
        final double expected = INITIAL_POSITION_TRAIN.x() + deltaX;
        for (int i = 0; i < UPDATES_COUNT; i++) {
            this.train.update(DELTA_TIME, gameParameters);
        }
        assertEquals(expected, this.train.getPosition().x(), DELTA_DOUBLE);
    }
}
