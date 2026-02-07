package it.unibo.crossyroad.model.api;

import java.nio.file.Path;

/**
 * A skin in the game.
 */
public interface Skin {

    /**
     * Returns the name of the skin.
     * 
     * @return the skin name.
     */
    String getName();

    /**
     * Returns the id of the skin.
     * 
     * @return the skin id.
     */
    String getId();

    /**
     * Return the path to the frontal image of the skin.
     * 
     * @return the path to the front image.
     */
    Path getFrontImage();

    /**
     * Returns the path to the overhead image of the skin.
     * 
     * @return the path to the overhead image.
     */
    Path getOverheadImage();

    /**
     * Returns the price of the skins.
     * 
     * @return the skin price.
     */
    int getPrice();
}
