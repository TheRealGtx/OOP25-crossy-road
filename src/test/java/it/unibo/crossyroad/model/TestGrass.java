package it.unibo.crossyroad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Grass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestGrass {
    private static final int MAX_OBS_EXPECTED = 15;
    private static final int MIN_OBS_EXPECTED = 0;
    private Grass grass;
    
    @BeforeEach
    void setUp() {
        this.grass = new Grass(new Position(0, 9), new Dimension(10, 3));
    }

    @Test
    void testEntity() {
        assertEquals(EntityType.GRASS, grass.getEntityType());
    }

    @Test
    void testObstaclegeneration() {
        int max = MIN_OBS_EXPECTED;
        int min = MAX_OBS_EXPECTED;

        for (int i = 0; i < 10000000; i++) {
            this.grass.init();
            int nOfObstacles = this.grass.getObstacles().size();

            if (nOfObstacles > max) {
                max = nOfObstacles;
            }

            if (nOfObstacles < min) {
                min = nOfObstacles;
            }
        }

        assertNotEquals(max, min);
        assertEquals(0, MIN_OBS_EXPECTED);
        assertEquals(15, MAX_OBS_EXPECTED);
    }
}
