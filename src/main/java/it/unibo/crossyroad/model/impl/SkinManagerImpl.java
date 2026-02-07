package it.unibo.crossyroad.model.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.io.File;
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
    public void loadFromFile(final String path) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(new File(path));
        final JsonNode skinsNode = root.get("skins");
        this.skins.clear();
        for (final JsonNode node: skinsNode) {
            final Skin skin = new SkinImpl(
                node.get("name").asText(),
                node.get("id").asText(),
                node.get("price").asInt(),
                Path.of(node.get("overheadImage").asText()),
                Path.of(node.get("frontalImage").asText()));
            this.skins.add(skin);
        }
        this.unlockDefaultSkin();
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
