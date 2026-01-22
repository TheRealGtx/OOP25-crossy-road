package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTrain {
    private static final Position INITIAL_POSITION_TRAIN = new Position(0, 4);
    private static final double SPEED_MULTIPLIER = 1.0;
    private static final double SPEED = 8.0;
    private static final long DELTA_TIME = 100;
    private static final int UPDATES_COUNT = 5;
    private static final double DELTA_DOUBLE = 0.0001;

    private Train train;

    @BeforeEach
    void setUp() {
        this.train = new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.RIGHT);
    }

    @Test
    void testTrainUpOrDownDirectionNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.UP);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, SPEED, Direction.DOWN);
        });
    }

    @Test
    void testCollisionTypeAndEntityType() {
        assertEquals(CollisionType.DEADLY, this.train.getCollisionType());
        assertEquals(EntityType.TRAIN, this.train.getEntityType());
    }

    @Test
    void testTrainZeroSpeedNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Train(INITIAL_POSITION_TRAIN, 0.0, Direction.RIGHT);
        });
    }

    @Test
    void testInitialPositionTrain() {
        assertEquals(INITIAL_POSITION_TRAIN, this.train.getPosition());
    }

    @Test
    void testMoveTrain() {
        this.train.update(DELTA_TIME, SPEED_MULTIPLIER);
        final double deltaX = SPEED * SPEED_MULTIPLIER * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(INITIAL_POSITION_TRAIN.x() + deltaX, INITIAL_POSITION_TRAIN.y());
        assertEquals(expectedPosition, this.train.getPosition());
    }

    @Test
    void testMultipleUpdates() {
        final double deltaX = SPEED * SPEED_MULTIPLIER * DELTA_TIME / 1000.0 * UPDATES_COUNT;
        final double expected = INITIAL_POSITION_TRAIN.x() + deltaX;
        for (int i = 0; i < UPDATES_COUNT; i++) {
            this.train.update(DELTA_TIME, SPEED_MULTIPLIER);
        }
        assertEquals(expected, this.train.getPosition().x(), DELTA_DOUBLE);
    }
}
