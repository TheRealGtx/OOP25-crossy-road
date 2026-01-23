package it.unibo.crossyroad.model.impl;

import java.util.Random;

import it.unibo.crossyroad.model.api.Dimension;
import it.unibo.crossyroad.model.api.EntityType;
import it.unibo.crossyroad.model.api.Position;

public class Grass extends AbstractChunk {

    public Grass(Position initialPosition, Dimension dimension) {
        super(initialPosition, dimension);
    }

    @Override
    public void genObstacles() {
        final Random rnd = new Random();

        switch (rnd.nextInt(2)) {
            case 0:
                this.getObstacles().add(
                    new Tree(
                        new Position(rnd.nextDouble(this.getDimension().width()), this.getDimension().height()), 
                        new Dimension(1, 1)
                    )
                );
                break;
        case 1:
                this.getObstacles().add(
                    new Rock(
                        new Position(rnd.nextDouble(this.getDimension().width()), this.getDimension().height()), 
                        new Dimension(1, 1)
                    )
                );
                break;
            default:
                break;
        }
    }
    
    //TODO update

    @Override
    public EntityType getEntityType() {
        return EntityType.GRASS;
    }

}
