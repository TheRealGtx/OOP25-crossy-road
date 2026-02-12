package it.unibo.crossyroad.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.SkinManager;

/**
 *  Implementation of the SkinManager interface.
 * 
 *  @see SkinManager
 */
public final class SkinManagerImpl implements SkinManager {

    private static final String SKINS_RESOURCE = "/skins.json";
    private static final String SKINS_KEY = "skins";
    private final Set<Skin> skins;
    private final Set<Skin> unlockedSkins;

    /**
     * Create a new skin manager by initializing empty collections.
     */
    public SkinManagerImpl() {
        this.skins = new HashSet<>();
        this.unlockedSkins = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromResources() throws IOException {
        try (InputStream inputstream = openResourceStream()) {
            this.loadSkinsFromStream(inputstream);
        }
    }

    /** 
     * Open the skins resource file as an input stream.
     * 
     * @return the input stream for the skins resource file.
     * @throws IOException if there are problems with resource file.
     */
    private InputStream openResourceStream() throws IOException {
        final InputStream stream = getClass().getResourceAsStream(SKINS_RESOURCE);
        if (stream == null) {
            throw new IOException("Resource not found");
        }
        return stream;
    }

    /**
     * Load and parse skins from the input stream.
     * 
     * @param inputStream the input stream to read from.
     * @throws IOException if an error occurs while reading.
     */
    private void loadSkinsFromStream(final InputStream inputStream) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode skinsNode = objectMapper.readTree(inputStream).get(SKINS_KEY);
        this.skins.clear();
        skinsNode.forEach(this::parseSkin);
        this.unlockDefaultSkin();
    }

    /**
     * Parse a single skin from json and add it to the collection.
     * 
     * @param node the json node containing the skin data.
     */
    private void parseSkin(final JsonNode node) {
        final Skin skin = SkinFactory.loadFromJson(node);
        this.skins.add(skin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getSkins() {
        return Set.copyOf(this.skins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getUnlockedSkins() {
        return Set.copyOf(this.unlockedSkins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int tryUnlock(final Skin skin, final int balance) {
        return this.skins.stream()
            .filter(s -> s.equals(skin) && !this.unlockedSkins.contains(s) && s.getPrice() <= balance)
            .findFirst()
            .map(s -> {
                this.unlockedSkins.add(skin);
                return balance - s.getPrice();
            })
            .orElse(balance);
    }

    /** 
     * Unlock the default skin if present, otherwise throw an exception.
     */
    private void unlockDefaultSkin() {
        this.skins.stream()
            .filter(s -> "default".equals(s.getId()))
            .findFirst()
            .ifPresentOrElse(
                this.unlockedSkins::add,
                () -> { 
                    throw new IllegalStateException("Default skin not found"); 
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lockSkins() {
        this.unlockedSkins.clear();
        this.unlockDefaultSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadUnlockedSkins(final Set<Skin> skinsLoaded) {
        this.unlockedSkins.clear();
        skinsLoaded.stream()
            .filter(this.skins::contains)
            .forEach(this.unlockedSkins::add);
        this.unlockDefaultSkin();
    }
}
