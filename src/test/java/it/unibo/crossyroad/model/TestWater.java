package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Water;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestWater {
    private static final Position INITIAL_POSITION = Position.of(10, 20);

    private final Random random = new Random();
    private Dimension dimension;
    private Water water;

    @BeforeEach
    void setUp() {
        dimension = new Dimension(random.nextInt(1, 100), random.nextInt(1, 10));
        water = new Water(INITIAL_POSITION, dimension);
    }

    @Test
    void testInitialPosition() {
        assertEquals(INITIAL_POSITION, water.getPosition());
    }

    @Test
    void testDimension() {
        assertEquals(dimension, water.getDimension());
    }

    @Test
    void testEntityType() {
        assertEquals(EntityType.WATER, water.getEntityType());
    }

    @Test
    void testCollisionType() {
        assertEquals(CollisionType.DEADLY, water.getCollisionType());
    }
}
