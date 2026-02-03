package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;
import it.unibo.crossyroad.model.impl.Railway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for the {@link Railway} class.
 */
class TestRailway {
    private static final int UPDATES_RAILWAY = 20;
    private static final int DELTA_TIME = 1200;

    private Railway railway;

    /**
     * Sets up a new Railway instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.railway = new Railway(new Position(0, 0), new Dimension(10, 3));
    }

    /**
     * Test to verify that the entity type of the railway is correctly identified as RAILWAY.
     */
    @Test
    void testEntityType() {
        assertEquals(EntityType.RAILWAY, this.railway.getEntityType());
    }

    /**
     * Test to verify the spawn of train in the railway.
     * There must be no more than 1 train in a railway.
     */
    @Test
    void testTrainGeneration() {
        final GameParameters gp = new GameParametersBuilder()
                .setTrainSpeedMultiplier(1.0)
                .build();
        for (int i = 0; i < UPDATES_RAILWAY; i++) {
            this.railway.update(gp, DELTA_TIME);
            assertFalse(this.railway.getObstacles().size() > 1);
        }
    }
}
