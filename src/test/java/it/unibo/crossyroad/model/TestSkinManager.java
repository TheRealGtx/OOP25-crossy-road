package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.SkinManager;
import it.unibo.crossyroad.model.impl.SkinImpl;
import it.unibo.crossyroad.model.impl.SkinManagerImpl;

/**
 * Test class for the {@link SkinManagerImpl} class.
 */
class TestSkinManager {
    private static final String DEFAULT_SKIN = "default";
    private static final String CONDUCTOR_SKIN = "conductor";
    private static final String AURA_SKIN = "aura";
    private static final int BALANCE = 40;
    private static final int INSUFFICIENT_BALANCE = 29;
    private static final int HIGH_BALANCE = 50;
    private static final int FAKE_SKIN_PRICE = 20;
    private SkinManager skinManager;

    @TempDir
    private Path dir;

    /**
     * Sets up a new instance of skinManager.
     */
    @BeforeEach
    void setUp() {
        this.skinManager = new SkinManagerImpl();
    }

    /**
     * Test that the constructor initializes skins and unlocked skins empty.
     */
    @Test
    void testInitialStateIsEmpty() {
        assertTrue(this.skinManager.getSkins().isEmpty());
        assertTrue(this.skinManager.getUnlockedSkins().isEmpty());
    }

    /**
     * Test loading from a non existent file throws IOException.
     */
    @Test
    void testLoadFromFileThrowsExceptionForInvalidPath() {
        assertThrows(IOException.class, () -> {
            skinManager.loadFromFile("/invalid/path/skins.json");
        });
    }

    private void loadSkinsFile() throws IOException {
        final String jsonContent = """
            {
                "skins": [
                    {
                        "name": "Chicken",
                        "id": "default", 
                        "price": 0,
                        "overheadImage": "resources/skins/default_overhead.png",
                        "frontalImage": "resources/skins/default_front.png"
                    },
                    {
                        "name": "Conductur",
                        "id": "conductor", 
                        "price": 30,
                        "overheadImage": "resources/skins/conductor_overhead.png",
                        "frontalImage": "resources/skins/conductor_front.png"
                    },
                    {
                        "name": "Aura",
                        "id": "aura", 
                        "price": 45,
                        "overheadImage": "resources/skins/aura_overhead.png",
                        "frontalImage": "resources/skins/aura_front.png"
                    }
                ]
            }
            """;
        final File jsonFile = dir.resolve("skins.json").toFile();
        Files.writeString(jsonFile.toPath(), jsonContent);
        this.skinManager.loadFromFile(jsonFile.getAbsolutePath());
    }

    /**
     * Test loading skins.
     * 
     * @throws IOException if there are any problems with the file.
     */
    @Test
    void testLoadFromFile() throws IOException {
        this.loadSkinsFile();
        assertEquals(3, this.skinManager.getSkins().size());
        assertEquals(1, this.skinManager.getUnlockedSkins().size());
        assertEquals(DEFAULT_SKIN, this.skinManager.getUnlockedSkins().iterator().next().getId());
    }

    /**
     * Test that loading a file without a default skin throws a exception.
     * 
     * @throws IOException if the default skin isn't in the file.
     */
    @Test
    void testLoadWithoutDefault() throws IOException {
        final String jsonContent = """
            {
                "skins": [
                    {
                        "name": "Conductur",
                        "id": "conductor", 
                        "price": 30,
                        "overheadImage": "resources/skins/conductor_overhead.png",
                        "frontalImage": "resources/skins/conductor_front.png"
                    }
                ]
            }
            """;

        final File jsonFile = dir.resolve("skins.json").toFile();
        Files.writeString(jsonFile.toPath(), jsonContent);
        assertThrows(IllegalStateException.class, () -> this.skinManager.loadFromFile(jsonFile.getAbsolutePath()));
    }

    /**
     * Find a skin by its id.
     * 
     * @param id the id of the skin to search.
     * @return the skin found by id, exception otherwise.
     */
    private Skin findSkinById(final String id) {
        return this.skinManager.getSkins().stream()
            .filter(s -> id.equals(s.getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("skin not found"));
    }

    /**
     * Test successfull unlock of a skin with sufficient balance.
     * 
     * @throws IOException if the file doesn't exists.
     */
    @Test
    void testTryUnlock() throws IOException {
        this.loadSkinsFile();
        assertEquals(10, this.skinManager.tryUnlock(this.findSkinById(CONDUCTOR_SKIN), BALANCE));
        assertTrue(this.skinManager.getUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(2, this.skinManager.getUnlockedSkins().size());
    }

    /**
     * Test that unlock fails with insufficient balance.
     * 
     * @throws IOException if the file doesn't exists.
     */
    @Test
    void testTryUnlockWithInsufficientBalance() throws IOException {
        this.loadSkinsFile();
        assertEquals(INSUFFICIENT_BALANCE, this.skinManager.tryUnlock(this.findSkinById(CONDUCTOR_SKIN), INSUFFICIENT_BALANCE));
        assertFalse(this.skinManager.getUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(1, this.skinManager.getUnlockedSkins().size());
    }

    /**
     * Test that unlocking an already unlocked skin doesn't change balance.
     * 
     * @throws IOException if the file doesn't exist.
     */
    @Test
    void testTryUnlockAlreadyUnlockedSkin() throws IOException {
        this.loadSkinsFile();
        this.skinManager.tryUnlock(this.findSkinById(CONDUCTOR_SKIN), BALANCE);
        assertEquals(BALANCE, this.skinManager.tryUnlock(this.findSkinById(CONDUCTOR_SKIN), BALANCE));
    }

    /**
     * Create a test skin.
     * 
     * @param name the name of the skin.
     * @param id the id of the skin.
     * @param price the price of the skin.
     * @return the new skin created.
     */
    private Skin createSkin(final String name, final String id, final int price) {
        return new SkinImpl(
            name,
            id,
            price,
            Path.of(id + "_overhead.png"),
            Path.of(id + "_front.png")
        );
    }

    /**
     * Test that trying to unlock a non existent skin doesn't change the balance. 
     * 
     * @throws IOException if the file doesn't exist.
     */
    @Test
    void testTryUnlockNonExistentSkin() throws IOException {
        this.loadSkinsFile();
        assertEquals(BALANCE, this.skinManager.tryUnlock(this.createSkin("Fake", "fake", FAKE_SKIN_PRICE), BALANCE));
    }


    /**
     * Test lockSkins clear all unlocked skins except default.
     * 
     * @throws IOException if the file doesn't exist.
     */
    @Test
    void testLockSkins() throws IOException {
        this.loadSkinsFile();
        this.skinManager.tryUnlock(this.findSkinById(AURA_SKIN), HIGH_BALANCE);
        assertEquals(2, this.skinManager.getUnlockedSkins().size());
        this.skinManager.lockSkins();
        assertEquals(1, this.skinManager.getUnlockedSkins().size());
        assertEquals(DEFAULT_SKIN, this.skinManager.getUnlockedSkins().iterator().next().getId());
    }


    /**
     * Test loadUnlockedSkins with valid skin set.
     * 
     * @throws IOException if there are any problems with the file.
     */
    @Test
    void testLoadUnlockedSkins() throws IOException {
        this.loadSkinsFile();
        final Set<Skin> skinToUnlock = new HashSet<>();
        skinToUnlock.add(this.findSkinById(AURA_SKIN));
        skinToUnlock.add(this.findSkinById(CONDUCTOR_SKIN));
        this.skinManager.loadUnlockedSkins(skinToUnlock);
        assertEquals(3, this.skinManager.getUnlockedSkins().size());
        assertTrue(this.skinManager.getUnlockedSkins().contains(this.findSkinById(DEFAULT_SKIN)));
        assertTrue(this.skinManager.getUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
        assertTrue(this.skinManager.getUnlockedSkins().contains(this.findSkinById(AURA_SKIN)));
    }
}
