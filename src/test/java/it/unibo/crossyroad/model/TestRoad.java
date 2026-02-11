package it.unibo.crossyroad.model;

import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.impl.Car;
import it.unibo.crossyroad.model.impl.GameParametersBuilder;
import it.unibo.crossyroad.model.impl.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRoad {
    private static final long DELTA_TIME = 1250;
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
     */
    @Test
    void testRandomCarGeneration() {
        int leftCars = 0;
        int rightCars = 0;

        final GameParameters gp = new GameParametersBuilder()
                .setCarSpeedMultiplier(1)
                .build();
        for (int i = 0; i < UPDATES_ROAD; i++) {
            this.road.update(gp, DELTA_TIME);
        }
        final int cars = road.getObstacles().size();
        for (final var obs: road.getObstacles()) {
            if (obs instanceof Car car) {
                switch (car.getEntityType()) {
                    case EntityType.CAR_LEFT -> leftCars++;
                    case EntityType.CAR_RIGHT -> rightCars++;
                    default -> { }
                }
            }
        }
        final int totalCars = leftCars + rightCars;
        assertTrue(totalCars > 0);
        assertTrue(cars <= MAX_CARS_PER_CHUNKS);
    }

}
