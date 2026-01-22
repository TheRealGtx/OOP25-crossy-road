package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestCar {
    private static final Position INITIAL_POSITION_CAR_LEFT_DIRECTION = new Position(20, 5);
    private static final Position INITIAL_POSITION_CAR_RIGHT_DIRECTION = new Position(0, 4);
    private static final double SPEED_MULTIPLIER = 1.0;
    private static final double SPEED = 2.0;
    private static final long DELTA_TIME = 100;
    private static final int UPDATES_COUNT = 5;
    private static final double DELTA_DOUBLE = 0.0001;

    private Car carWithLeftDirection;
    private Car carWithRightDirection;

    @BeforeEach
    void setUp() {
        this.carWithLeftDirection = new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.LEFT);
        this.carWithRightDirection = new Car(INITIAL_POSITION_CAR_RIGHT_DIRECTION, SPEED, Direction.RIGHT);
    }

    @Test
    void testCarUpOrDownDirectionNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
           new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.UP);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.DOWN);
        });
    }

    @Test
    void testCollisionTypeAndEntityType() {
        assertEquals(CollisionType.DEADLY, this.carWithLeftDirection.getCollisionType());
        assertEquals(EntityType.CAR, this.carWithLeftDirection.getEntityType());
    }

    @Test
    void testCarZeroSpeedNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, 0.0, Direction.LEFT);
        });
    }

    @Test
    void testInitialPositionCarLeftAndRightDirection() {
        assertEquals(INITIAL_POSITION_CAR_LEFT_DIRECTION, this.carWithLeftDirection.getPosition());
        assertEquals(INITIAL_POSITION_CAR_RIGHT_DIRECTION, this.carWithRightDirection.getPosition());
    }

    @Test
    void testMoveCarLeftDirection() {
        this.carWithLeftDirection.update(DELTA_TIME, SPEED_MULTIPLIER);
        final double deltaX = -SPEED * SPEED_MULTIPLIER * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(
                INITIAL_POSITION_CAR_LEFT_DIRECTION.x() + deltaX,
                INITIAL_POSITION_CAR_LEFT_DIRECTION.y()
        );
        assertEquals(expectedPosition, this.carWithLeftDirection.getPosition());
    }

    @Test
    void testMoveCarRightDirection() {
        this.carWithRightDirection.update(DELTA_TIME, SPEED_MULTIPLIER);
        final double deltaX = SPEED * SPEED_MULTIPLIER * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(
                INITIAL_POSITION_CAR_RIGHT_DIRECTION.x() + deltaX,
                INITIAL_POSITION_CAR_RIGHT_DIRECTION.y()
        );
        assertEquals(expectedPosition, this.carWithRightDirection.getPosition());
    }

    @Test
    void testMultipleUpdates() {
        final double deltaX = SPEED * SPEED_MULTIPLIER * DELTA_TIME / 1000.0 * UPDATES_COUNT;
        final double expectedLeft = INITIAL_POSITION_CAR_LEFT_DIRECTION.x() - deltaX;
        final double expectedRight = INITIAL_POSITION_CAR_RIGHT_DIRECTION.x() + deltaX;
        for (int i = 0; i < UPDATES_COUNT; i++) {
            this.carWithLeftDirection.update(DELTA_TIME, SPEED_MULTIPLIER);
            this.carWithRightDirection.update(DELTA_TIME, SPEED_MULTIPLIER);
        }
        assertEquals(expectedRight, this.carWithRightDirection.getPosition().x(), DELTA_DOUBLE);
        assertEquals(expectedLeft, this.carWithLeftDirection.getPosition().x(), DELTA_DOUBLE);
    }
}
