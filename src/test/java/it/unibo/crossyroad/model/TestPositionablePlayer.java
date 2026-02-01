package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.PositionablePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the {@link PositionablePlayer} class.
 */
class TestPositionablePlayer {
    private static final Position INITIAL_POSITION = Position.of(10, 2);
    private static final double MOVE_STEPS = 1.0;
    private static final Position EXPECTED_MOVE_UP_POSITION = Position.of(0, -MOVE_STEPS).relative(INITIAL_POSITION);
    private static final Position EXPECTED_MOVE_DOWN_POSITION = Position.of(0, MOVE_STEPS).relative(INITIAL_POSITION);
    private static final Position EXPECTED_MOVE_LEFT_POSITION = Position.of(-MOVE_STEPS, 0).relative(INITIAL_POSITION);
    private static final Position EXPECTED_MOVE_RIGHT_POSITION = Position.of(MOVE_STEPS, 0).relative(INITIAL_POSITION);

    private PositionablePlayer player;

    /**
     * Sets up a new PositionablePlayer instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.player = new PositionablePlayer(INITIAL_POSITION);
    }

    /**
     * Tests that the initial position of the PositionablePlayer is set correctly.
     */
    @Test
    void testInitialPosition() {
        assertEquals(INITIAL_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player up.
     */
    @Test
    void testMoveUp() {
        this.player.move(Direction.UP, MOVE_STEPS);
        assertEquals(EXPECTED_MOVE_UP_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player down.
     */
    @Test
    void testMoveDown() {
        this.player.move(Direction.DOWN, MOVE_STEPS);
        assertEquals(EXPECTED_MOVE_DOWN_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player left.
     */
    @Test
    void testMoveLeft() {
        this.player.move(Direction.LEFT, MOVE_STEPS);
        assertEquals(EXPECTED_MOVE_LEFT_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player right.
     */
    @Test
    void testMoveRight() {
        this.player.move(Direction.RIGHT, MOVE_STEPS);
        assertEquals(EXPECTED_MOVE_RIGHT_POSITION, this.player.getPosition());
    }

    /**
     * Tests multiple moves of the PositionablePlayer class.
     */
    @Test
    void testMultipleMoves() {
        this.player.move(Direction.UP, MOVE_STEPS);
        this.player.move(Direction.LEFT, MOVE_STEPS);
        this.player.move(Direction.DOWN, MOVE_STEPS);
        this.player.move(Direction.RIGHT, MOVE_STEPS);
        assertEquals(INITIAL_POSITION, this.player.getPosition());
    }

    /**
     * Tests move method with fractional steps.
     */
    @Test
    void testMoveWithFractionalSteps() {
        final double fractionalSteps = 0.5;
        final Position expectedPosition = Position.of(0, -fractionalSteps).relative(INITIAL_POSITION);
        this.player.move(Direction.UP, fractionalSteps);
        assertEquals(expectedPosition, this.player.getPosition());
    }
}
