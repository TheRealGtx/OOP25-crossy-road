package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.Direction;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.River;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRiver {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 3;
    private static final long LOG_INTERVAL = 3000;

    private GameParameters gameParameters;
    private River river;
    private Position position;
    private Dimension dimension;

    @BeforeEach
    void setUp() {
        this.gameParameters = new GameParametersImpl();
        this.position = Position.of(0, 0);
        this.dimension = Dimension.of(WIDTH, HEIGHT);
        this.river = new River(position, dimension, Direction.LEFT);
    }

    @Test
    void testValidDirection() {
        assertDoesNotThrow(() -> new River(position, dimension, Direction.LEFT));
        assertDoesNotThrow(() -> new River(position, dimension, Direction.RIGHT));
    }

    @Test
    void testInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () -> new River(position, dimension, Direction.UP));
        assertThrows(IllegalArgumentException.class, () -> new River(position, dimension, Direction.DOWN));
    }

    @Test
    void testEntityType() {
        assertEquals(EntityType.RIVER, river.getEntityType());
    }

    @Test
    void testInit() {
        assertTrue(river.getObstacles().isEmpty());
        river.init();
        assertFalse(river.getObstacles().isEmpty());
        assertTrue(river.getObstacles().size() >= 2);
    }

    @Test
    void testObstaclesContainWater() {
        river.init();

        final boolean hasWater = river.getObstacles().stream()
            .anyMatch(o -> o.getEntityType() == EntityType.WATER);

        assertTrue(hasWater);
    }

    @Test
    void testObstaclesContainLog() {
        river.init();

        assertTrue(this.countLogs() > 0);
    }

    @Test
    void testNewLogGenerated() {
        river.init();
        final int logCount = countLogs();

        river.update(gameParameters, LOG_INTERVAL);

        assertTrue(countLogs() > logCount);
    }

    @Test
    void testNoNewLogBeforeInterval() {
        river.init();
        final int logCount = countLogs();

        river.update(gameParameters, LOG_INTERVAL / 2);

        assertEquals(logCount, countLogs());
    }

    private int countLogs() {
        final long count = river.getObstacles().stream()
            .filter(o -> o.getEntityType() == EntityType.WOOD_LOG)
            .count();
        return Math.toIntExact(count);
    }
}
