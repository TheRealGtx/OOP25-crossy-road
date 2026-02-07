package it.unibo.crossyroad.model.impl;

import java.nio.file.Path;

import it.unibo.crossyroad.model.api.Skin;

/**
 * Implementation of the Skin interface.
 * 
 * @see Skin
 */
public final class SkinImpl implements Skin {

    private final String id;
    private final int price;
    private final String name;
    private final Path overheadImagePath;
    private final Path frontImagePath;

    /**
     * Create a new skin with the specified name, id, price, overhead image and front image.
     * 
     * @param name the name of the skin.
     * @param id the id of the skin.
     * @param price the price of the skin.
     * @param overheadImagePath the path to the overhead image of the skin.
     * @param frontImagePath the path to the front image of the skin.
     */
    public SkinImpl(
            final String name,
            final String id,
            final int price,
            final Path overheadImagePath,
            final Path frontImagePath) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.overheadImagePath = overheadImagePath;
        this.frontImagePath = frontImagePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrice() {
        return this.price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getFrontImage() {
        return this.frontImagePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getOverheadImage() {
        return this.overheadImagePath;
    }
}
