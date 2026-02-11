package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.CollisionType;
import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;
import it.unibo.crossyroad.model.impl.Rock;
import it.unibo.crossyroad.model.impl.Tree;

class TestPassiveObstacles {
    private static final Position OBSTACLE_POS_1 = new Position(6, 6);
    private static final Position OBSTACLE_POS_2 = new Position(5, 5);
    private Rock rock;
    private Tree tree;

    @BeforeEach
    void setUp() {
        this.rock = new Rock(OBSTACLE_POS_1, new Dimension(1, 1));
        this.tree = new Tree(OBSTACLE_POS_2, new Dimension(1, 1));
    }

    @Test
    void testCollision() {
        assertEquals(CollisionType.SOLID, rock.getCollisionType());
        assertEquals(CollisionType.SOLID, tree.getCollisionType());
    }

    @Test
    void testEntity() {
        assertEquals(EntityType.ROCK, rock.getEntityType());
        assertEquals(EntityType.TREE, tree.getEntityType());
    }

    @Test
    void testDimension() {
        assertEquals(1, this.rock.getDimension().width());
        assertEquals(1, this.tree.getDimension().width());
        assertEquals(1, this.rock.getDimension().height());
        assertEquals(1, this.tree.getDimension().height());
    }

    @Test
    void testPosition() {
        assertEquals(OBSTACLE_POS_1, this.rock.getPosition());
        assertEquals(OBSTACLE_POS_2, this.tree.getPosition());
    }
}
