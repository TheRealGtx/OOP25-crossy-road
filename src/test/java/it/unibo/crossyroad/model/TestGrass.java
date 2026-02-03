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
    private static final int MAX_OBS_EXPECTED = 14;
    private static final int MIN_OBS_EXPECTED = 0;
    private static final int MAX_PICK_EXPECTED = 4;
    private static final int MIN_PICK_EXPECTED = 0;
    private static final int LOOPS = 10_000_000;
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
        int maxObs = MIN_OBS_EXPECTED;
        int minObs = MAX_OBS_EXPECTED;
        int maxPick = MIN_PICK_EXPECTED;
        int minPick = MAX_PICK_EXPECTED;

        for (int i = 0; i < LOOPS; i++) {
            this.grass.init();
            final int nOfObstacles = this.grass.getObstacles().size();
            final int nOfPickables = this.grass.getPickables().size();

            if (nOfObstacles > maxObs) {
                maxObs = nOfObstacles;
            }
            if (nOfObstacles < minObs) {
                minObs = nOfObstacles;
            }

            if (nOfPickables > maxPick) {
                maxPick = nOfPickables;
            }
            if (nOfPickables < minPick) {
                minPick = nOfPickables;
            }
        }

        assertNotEquals(maxObs, minObs);
        assertNotEquals(maxPick, minPick);
        assertEquals(MIN_OBS_EXPECTED, minObs);
        assertEquals(MAX_OBS_EXPECTED, maxObs);
        assertEquals(MIN_PICK_EXPECTED, minPick);
        assertEquals(MAX_PICK_EXPECTED, maxPick);
    }

    @Test
    void testPositionablesCount() {
        final int positionables = this.grass.getObstacles().size() + this.grass.getPickables().size();
        assertEquals(positionables, this.grass.getPositionables().size());
    }
}
