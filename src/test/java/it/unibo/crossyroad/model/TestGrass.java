package it.unibo.crossyroad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Grass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestGrass {
    private static final int LOOPS = 10_000;
    private static final Position CHUNK_INITIAL_POSITION = new Position(0, 0);
    private Grass grass;

    @BeforeEach
    void setUp() {
        this.grass = new Grass(CHUNK_INITIAL_POSITION, new Dimension(10, 3));
    }

    @Test
    void testEntity() {
        assertEquals(EntityType.GRASS, grass.getEntityType());
    }

    @Test
    void testRandomGenerations() {
        for (int i = 0; i < LOOPS; i++) {
            this.grass.init();
            final int nOfObstacles = this.grass.getObstacles().size();
            final int nOfPickables = this.grass.getPickables().size();

            assertTrue(nOfObstacles > 0);
            assertTrue(nOfObstacles < 16);
            assertTrue(nOfPickables < 3);
        }
    }

    @Test
    void testPositionablesCount() {
        final int positionables = this.grass.getObstacles().size() + this.grass.getPickables().size();
        assertEquals(positionables, this.grass.getPositionables().size());
    }
}
