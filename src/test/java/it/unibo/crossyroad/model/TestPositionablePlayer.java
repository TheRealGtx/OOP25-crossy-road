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
    private static final Position INITIAL_POSITION = new Position(10, 2);
    private static final Position EXPECTED_MOVE_UP_POSITION = new Position(INITIAL_POSITION.x(), INITIAL_POSITION.y() - 1);
    private static final Position EXPECTED_MOVE_DOWN_POSITION = new Position(INITIAL_POSITION.x(), INITIAL_POSITION.y() + 1);
    private static final Position EXPECTED_MOVE_LEFT_POSITION = new Position(INITIAL_POSITION.x() - 1, INITIAL_POSITION.y());
    private static final Position EXPECTED_MOVE_RIGHT_POSITION = new Position(INITIAL_POSITION.x() + 1, INITIAL_POSITION.y());

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
        this.player.move(Direction.UP);
        assertEquals(EXPECTED_MOVE_UP_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player down.
     */
    @Test
    void testMoveDown() {
        this.player.move(Direction.DOWN);
        assertEquals(EXPECTED_MOVE_DOWN_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player left.
     */
    @Test
    void testMoveLeft() {
        this.player.move(Direction.LEFT);
        assertEquals(EXPECTED_MOVE_LEFT_POSITION, this.player.getPosition());
    }

    /**
     * Tests the move method of the PositionablePlayer class, moving the player right.
     */
    @Test
    void testMoveRight() {
        this.player.move(Direction.RIGHT);
        assertEquals(EXPECTED_MOVE_RIGHT_POSITION, this.player.getPosition());
    }

    /**
     * Tests multiple moves of the PositionablePlayer class.
     */
    @Test
    void testMultipleMoves() {
        this.player.move(Direction.UP);
        this.player.move(Direction.LEFT);
        this.player.move(Direction.DOWN);
        this.player.move(Direction.RIGHT);
        assertEquals(INITIAL_POSITION, this.player.getPosition());
    }
}
