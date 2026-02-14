package it.unibo.crossyroad.model.impl;

import java.nio.file.Path;
import java.util.Objects;

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
     * Creates a {@link Skin} instance using the values provided by the builder.
     * 
     * @param builder the builder containing the skin configuration.
     */
    private SkinImpl(final Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "Name cannot be null");
        this.id = Objects.requireNonNull(builder.id, "Id cannot be null");
        this.price = Objects.requireNonNull(builder.price, "Price cannot be null");
        this.overheadImagePath = Objects.requireNonNull(builder.overheadImagePath, "Overhead image path cannot be null");
        this.frontImagePath = Objects.requireNonNull(builder.frontImagePath, "Front image path cannot be null");
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

    /**
     * Builder for skin instances.
     */
    public static final class Builder {

        private String name;
        private String id;
        private int price;
        private Path overheadImagePath;
        private Path frontImagePath;

        /**
         * Set the name of the skin.
         * 
         * @param skinName the name to set.
         * @return this build instance.
         */
        public Builder name(final String skinName) {
            this.name = skinName;
            return this;
        }

        /**
         * Set the id of the skin.
         * 
         * @param skinId the id to set.
         * @return this build instance.
         */
        public Builder id(final String skinId) {
            this.id = skinId;
            return this;
        }

        /**
         * Set the price of the skin.
         * 
         * @param skinPrice the price to set.
         * @return this build instance.
         */
        public Builder price(final int skinPrice) {
            this.price = skinPrice;
            return this;
        }

        /**
         * Set the overhead image path.
         * 
         * @param path the overhead image path to set.
         * @return this builder instance.
         */
        public Builder overheadImagePath(final Path path) {
            this.overheadImagePath = path;
            return this;
        }

        /**
         * Set the front image path.
         * 
         * @param path the front image path to set.
         * @return this builder instance.
         */
        public Builder frontImagePath(final Path path) {
            this.frontImagePath = path;
            return this;
        }

        /**
         * Build and return the skin instance.
         * 
         * @return a new skin instance.
         */
        public Skin build() {
            return new SkinImpl(this);
        }
    }
}
