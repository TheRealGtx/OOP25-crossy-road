package it.unibo.crossyroad.model.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.SkinManager;
import it.unibo.crossyroad.model.api.StateManager;

/**
 * Implementation of the StateManager interface.
 * 
 * @see StateManager
 */
public final class StateManagerImpl implements StateManager {

    private Skin activeSkin;
    private final GameParameters gameParameters;
    private final SkinManager skinManager;

    /**
     * Create a new game state manager by initializing the game parameters and the skin manager.
     * The active skin will be the default one, and can be changed later.
     * 
     * @param gameParameters the game parameters of the game.
     * @param skinManager the skin manager of the game.
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "GameParameters is intentionally shared between StateManager and GameManager,"
                + "as both need to read and modify common game state values"
    )
    public StateManagerImpl(final GameParameters gameParameters, final SkinManager skinManager) {
        this.gameParameters = gameParameters;
        this.skinManager = Objects.requireNonNull(skinManager);
        this.activeSkin = this.skinManager.getUnlockedSkins().stream()
        .filter(skin -> "default".equals(skin.getId()))
        .findFirst()
        .or(() -> this.skinManager.getUnlockedSkins().stream().findFirst())
        .orElseThrow(() -> new IllegalStateException("No unlocked skins"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoinCounter() {
        return this.gameParameters.getCoinCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.gameParameters.setCoinCount(0);
        this.skinManager.lockSkins();
        this.activeSkin = this.skinManager.getUnlockedSkins().stream().findFirst().orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getAllSkins() {
        return this.skinManager.getSkins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unlockSkin(final Skin skin) {
        final int currentBalance = this.getCoinCounter();
        final int newBalance = this.skinManager.tryUnlock(skin, this.getCoinCounter());
        if (currentBalance != newBalance) {
            this.gameParameters.setCoinCount(newBalance);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Skin> getAllUnlockedSkins() {
        return this.skinManager.getUnlockedSkins();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activateSkin(final Skin skin) {
        return this.skinManager.getUnlockedSkins().stream()
        .filter(s -> s.equals(skin))
        .findFirst()
        .map(s -> {
            this.activeSkin = s;
            return true;
        })
        .orElse(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skin getActiveSkin() {
        return this.activeSkin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final Path directory) throws IOException {
        Files.createDirectories(directory);
        final State state = new State(
            this.getCoinCounter(),
            this.activeSkin.getId(),
            this.getAllUnlockedSkins().stream()
            .map(Skin::getId)
            .collect(Collectors.toSet())
        );
        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(directory.resolve("data.json").toFile(), state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(final Path directory) throws IOException {
        final File file = directory.resolve("data.json").toFile();
        final ObjectMapper mapper = new ObjectMapper();
        final State state = mapper.readValue(file, State.class);
        this.gameParameters.setCoinCount(state.coins());
        final Set<Skin> unlockedSkins = this.skinManager.getSkins().stream()
            .filter(skin -> state.unlockedSkins().contains(skin.getId()))
            .collect(Collectors.toSet());
        this.skinManager.loadUnlockedSkins(unlockedSkins);
        this.activeSkin = this.skinManager.getUnlockedSkins().stream()
            .filter(skin -> skin.getId().equals(state.activeSkinId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Active skin not found"));
    }

    private record State(int coins, String activeSkinId, Set<String> unlockedSkins) { }
}
