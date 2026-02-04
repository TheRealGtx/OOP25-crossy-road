package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;
import it.unibo.crossyroad.model.impl.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRoad {
    private static final long DELTA_TIME = 1200;
    private static final int MAX_CARS_PER_CHUNKS = 8;
    private static final int UPDATES_ROAD = 150;

    private Road road;

    /**
     * Sets up a new Road instance before each test.
     */
    @BeforeEach
    void setUp() {
        this.road = new Road(new Position(0, 0), new Dimension(10, 3));
    }

    /**
     * Test to verify that the entity type of the road is correctly identified as ROAD.
     */
    @Test
    void testEntityType() {
        assertEquals(EntityType.ROAD, this.road.getEntityType());
    }

    /**
     * Test to verify the spawn of some car in the road.
     * This test verifies that after multiple calls to {@link Road#update(GameParameters, long)},
     * cars are generated correctly and the total number of cars does not exceed the maximum allowed per chunk.
     * The test counts the cars in each lane using y coordinate of each car:
     * - Cars in the top lane are considered moving left.
     * - Cars in the bottom lane are considered moving right.
     */
    @Test
    void testRandomCarGeneration() {
        int leftCars = 0;
        int rightCars = 0;

        final double chunkY = road.getPosition().y();
        final double laneHeight = road.getDimension().height() / 2;
        final double tolerance = 0.001;

        final GameParameters gp = new GameParametersBuilder()
                .setCarSpeedMultiplier(1)
                .build();
        for (int i = 0; i < UPDATES_ROAD; i++) {
            this.road.update(gp, DELTA_TIME);
        }
        final int cars = road.getObstacles().size();
        for (final var obs: road.getObstacles()) {
            final double carY = obs.getPosition().y();
            if (Math.abs(carY - chunkY) < tolerance) {
                leftCars++;
            } else if (Math.abs(carY - (chunkY - laneHeight)) < tolerance) {
                rightCars++;
            }
        }

        assertTrue(leftCars > 0);
        assertTrue(rightCars > 0);
        assertTrue(cars <= MAX_CARS_PER_CHUNKS);
    }

}
