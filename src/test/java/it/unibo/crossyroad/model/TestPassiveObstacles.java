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
    private Rock rock;
    private Tree tree;

    @BeforeEach
    void setup() {
        this.rock = new Rock(new Position(6, 6), new Dimension(1, 1));
        this.tree = new Tree(new Position(5, 5), new Dimension(1, 1));
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
}
