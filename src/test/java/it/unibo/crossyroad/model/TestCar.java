package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Car;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the {@link Car} class.
 */
class TestCar {
    private static final Position INITIAL_POSITION_CAR_LEFT_DIRECTION = new Position(20, 5);
    private static final Position INITIAL_POSITION_CAR_RIGHT_DIRECTION = new Position(0, 4);
    private static final double SPEED = 2.0;
    private static final long DELTA_TIME = 100;
    private static final int UPDATES_COUNT = 5;
    private static final double DELTA_DOUBLE = 0.0001;

    private Car carWithLeftDirection;
    private Car carWithRightDirection;
    private GameParameters gameParameters;

    /**
     * Sets up two Car instances before each test, one moving left and the other moving right.
     */
    @BeforeEach
    void setUp() {
        this.carWithLeftDirection = new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.LEFT);
        this.carWithRightDirection = new Car(INITIAL_POSITION_CAR_RIGHT_DIRECTION, SPEED, Direction.RIGHT);
        this.gameParameters = new GameParametersImpl();
    }

    /**
     * Tests that creating a Car with UP or DOWN direction throws an IllegalArgumentException.
     */
    @Test
    void testCarUpOrDownDirectionNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
           new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.UP);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, SPEED, Direction.DOWN);
        });
    }

    /**
     * Tests the collision type and entity type of the Car.
     */
    @Test
    void testCollisionTypeAndEntityType() {
        assertEquals(CollisionType.DEADLY, this.carWithLeftDirection.getCollisionType());
        assertEquals(EntityType.CAR, this.carWithLeftDirection.getEntityType());
    }

    /**
     * Tests that creating a Car with zero speed throws an IllegalArgumentException.
     */
    @Test
    void testCarZeroSpeedNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car(INITIAL_POSITION_CAR_LEFT_DIRECTION, 0.0, Direction.LEFT);
        });
    }

    /**
     * Tests the initial positions of the Cars.
     */
    @Test
    void testInitialPositionCarLeftAndRightDirection() {
        assertEquals(INITIAL_POSITION_CAR_LEFT_DIRECTION, this.carWithLeftDirection.getPosition());
        assertEquals(INITIAL_POSITION_CAR_RIGHT_DIRECTION, this.carWithRightDirection.getPosition());
    }

    /**
     * Tests the movement of the Car with LEFT direction.
     */
    @Test
    void testMoveCarLeftDirection() {
        this.carWithLeftDirection.update(DELTA_TIME, gameParameters);
        final double deltaX = -SPEED * gameParameters.getCarSpeedMultiplier() * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(
                INITIAL_POSITION_CAR_LEFT_DIRECTION.x() + deltaX,
                INITIAL_POSITION_CAR_LEFT_DIRECTION.y()
        );
        assertEquals(expectedPosition, this.carWithLeftDirection.getPosition());
    }

    /**
     * Tests the movement of the Car with RIGHT direction.
     */
    @Test
    void testMoveCarRightDirection() {
        this.carWithRightDirection.update(DELTA_TIME, gameParameters);
        final double deltaX = SPEED * gameParameters.getCarSpeedMultiplier() * DELTA_TIME / 1000.0;
        final Position expectedPosition = new Position(
                INITIAL_POSITION_CAR_RIGHT_DIRECTION.x() + deltaX,
                INITIAL_POSITION_CAR_RIGHT_DIRECTION.y()
        );
        assertEquals(expectedPosition, this.carWithRightDirection.getPosition());
    }

    /**
     * Tests multiple updates of the Cars in both LEFT and RIGHT directions.
     */
    @Test
    void testMultipleUpdates() {
        final double deltaX = SPEED * gameParameters.getCarSpeedMultiplier() * DELTA_TIME / 1000.0 * UPDATES_COUNT;
        final double expectedLeft = INITIAL_POSITION_CAR_LEFT_DIRECTION.x() - deltaX;
        final double expectedRight = INITIAL_POSITION_CAR_RIGHT_DIRECTION.x() + deltaX;
        for (int i = 0; i < UPDATES_COUNT; i++) {
            this.carWithLeftDirection.update(DELTA_TIME, gameParameters);
            this.carWithRightDirection.update(DELTA_TIME, gameParameters);
        }
        assertEquals(expectedRight, this.carWithRightDirection.getPosition().x(), DELTA_DOUBLE);
        assertEquals(expectedLeft, this.carWithLeftDirection.getPosition().x(), DELTA_DOUBLE);
    }
}
