package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import it.unibo.crossyroad.model.api.GameParameters;
import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.api.SkinManager;
import it.unibo.crossyroad.model.api.StateManager;
import it.unibo.crossyroad.model.impl.GameParametersImpl;
import it.unibo.crossyroad.model.impl.SkinManagerImpl;
import it.unibo.crossyroad.model.impl.StateManagerImpl;

/**
 * Test class for the {@link StateManagerImpl} class.
 */
class TestStateManager {

    private static final String DEFAULT_SKIN = "default";
    private static final String CONDUCTOR_SKIN = "conductor";
    private static final String AURA_SKIN = "aura";
    private static final String FILE_SAVE_NAME = "save";
    private static final int BALANCE = 40;
    private static final int INSUFFICIENT_BALANCE = 29;
    private static final int HIGH_BALANCE = 80;
    private GameParameters gameParameters;
    private SkinManager skinManager;
    private StateManager stateManager;

    @TempDir
    private Path dir;

    /**
     * Sets up a new instance of gameParameters, skinManager and stateManager.
     * 
     * @throws IOException if there are any errors with file.
     */
    @BeforeEach
    void setUp() throws IOException {
        this.gameParameters = new GameParametersImpl();
        this.skinManager = new SkinManagerImpl();
        this.createSkinsFile();
        this.stateManager = new StateManagerImpl(this.gameParameters, this.skinManager);
    }

    /**
     * Load the skins into the skin manager.
     * 
     * @throws IOException if there are any problems with the file.
     */
    private void createSkinsFile() throws IOException {
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
                        "price": 40,
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
     * Test that the constructor initializes with deafault skin as active.
     */
    @Test
    void testConstructorSetsDefaultSkinAsActive() {
        assertEquals(DEFAULT_SKIN, this.stateManager.getActiveSkin().getId());
    }

    /**
     * Test that constructor throws exception when no unlocked skins are avaible.
     */
    @Test
    void testConstructorThrowsExceptionWhenNoUnlockedSkins() {
        final SkinManager tmpSkinManager = new SkinManagerImpl();
        assertThrows(IllegalStateException.class, () -> new StateManagerImpl(gameParameters, tmpSkinManager));
    }

    /**
     * Test that constructor throws NullPointerException when skinManager is null.
     */
    @Test
    void testConstructorThrowsExceptionWhenSkinManagerIsNull() {
        assertThrows(NullPointerException.class, () -> new StateManagerImpl(gameParameters, null));
    }

    /**
     * Test the coin counter return the correct coin count.
     */
    @Test
    void testGetCointer() {
        this.gameParameters.setCoinCount(BALANCE);
        assertEquals(BALANCE, this.stateManager.getCoinCounter());
    }

    /**
     * Find a skin by its id.
     * 
     * @param id the id of the skin to search.
     * @return the skin found by id, exceptio otherwise.
     */
    private Skin findSkinById(final String id) {
        return this.skinManager.getSkins().stream()
            .filter(s -> id.equals(s.getId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("skin not found"));
    }

    /**
     * Test that the reset clears coins and locks all skins except default.
     */
    @Test
    void testReset() {
        this.gameParameters.setCoinCount(BALANCE);
        this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN));
        assertEquals(2, this.stateManager.getAllUnlockedSkins().size());
        this.stateManager.reset();
        assertEquals(0, this.stateManager.getCoinCounter());
        assertEquals(1, this.stateManager.getAllUnlockedSkins().size());
        assertEquals(DEFAULT_SKIN, this.stateManager.getAllUnlockedSkins().stream().iterator().next().getId());
        assertEquals(DEFAULT_SKIN, this.stateManager.getActiveSkin().getId());
    }

    /**
     * Test that skin unlock with sufficient balance.
     */
    @Test
    void testUnlockSkinWithEnoughBalance() {
        this.gameParameters.setCoinCount(BALANCE);
        assertTrue(this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(10, this.stateManager.getCoinCounter());
        assertTrue(this.stateManager.getAllUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
    }

    /**
     * Test that skin unlock fails for insufficient balance.
     */
    @Test
    void testUnlockSkinWithInsufficientBalance() {
        this.gameParameters.setCoinCount(INSUFFICIENT_BALANCE);
        assertFalse(this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(INSUFFICIENT_BALANCE, this.stateManager.getCoinCounter());
        assertFalse(this.stateManager.getAllUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
    }

    /**
     * Test that unlocking an already unlocked skin returns false.
     */
    @Test
    void testUnlockAlreadyUnlockedSkin() {
        this.gameParameters.setCoinCount(BALANCE);
        this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN));
        assertTrue(this.stateManager.getAllUnlockedSkins().contains(this.findSkinById(CONDUCTOR_SKIN)));
        this.gameParameters.setCoinCount(BALANCE);
        assertFalse(this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN)));
    }

    /**
     * Test the success of skin activation.
     */
    @Test
    void testActiveSkinSuccess() {
        this.gameParameters.setCoinCount(BALANCE);
        assertTrue(this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN)));
        assertTrue(this.stateManager.activateSkin(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(CONDUCTOR_SKIN, this.stateManager.getActiveSkin().getId());
    }

    /**
     * Test that activating a locked skin return false.
     */
    @Test
    void testActiveSkinFail() {
        assertFalse(this.stateManager.activateSkin(this.findSkinById(CONDUCTOR_SKIN)));
        assertEquals(DEFAULT_SKIN, this.stateManager.getActiveSkin().getId());
    }

    /**
     * Test that returns the currently active skin.
     */
    @Test
    void testGetActiveSkin() {
        assertEquals(DEFAULT_SKIN, this.stateManager.getActiveSkin().getId());
    }

    /**
     * Test save, writes the state data correctly.
     * 
     * @throws IOException if there are any problems with the file.
     */
    @Test
    void testSave() throws IOException {
        this.gameParameters.setCoinCount(BALANCE);
        this.stateManager.unlockSkin(this.findSkinById(CONDUCTOR_SKIN));
        this.stateManager.activateSkin(this.findSkinById(CONDUCTOR_SKIN));
        this.stateManager.save(dir.resolve(FILE_SAVE_NAME));
        final String content = Files.readString(dir.resolve(FILE_SAVE_NAME).resolve("data.json"));
        assertTrue(content.contains("\"coins\" : 10"));
        assertTrue(content.contains("\"activeSkinId\" : \"" + CONDUCTOR_SKIN + "\""));
        assertTrue(content.contains(DEFAULT_SKIN));
        assertTrue(content.contains(CONDUCTOR_SKIN));
    }

    /**
     * Test that the load sets the data correctly.
     * 
     * @throws IOException if there are any problems with the files.
     */
    @Test
    void testLoad() throws IOException {
        this.gameParameters.setCoinCount(HIGH_BALANCE);
        this.stateManager.unlockSkin(this.findSkinById(AURA_SKIN));
        this.stateManager.activateSkin(this.findSkinById(AURA_SKIN));
        this.stateManager.save(dir.resolve(FILE_SAVE_NAME));
        this.stateManager.reset();
        assertEquals(0, this.stateManager.getCoinCounter());
        assertEquals(DEFAULT_SKIN, this.stateManager.getActiveSkin().getId());
        this.stateManager.load(dir.resolve(FILE_SAVE_NAME));
        assertEquals(BALANCE, this.stateManager.getCoinCounter());
        assertEquals(AURA_SKIN, this.stateManager.getActiveSkin().getId());
        assertEquals(2, this.stateManager.getAllUnlockedSkins().size());
    }

    /**
     * Test load throws exception when file doesn't exist.
     */
    @Test
    void testLoadErrorWhenFileNotFound() {
        assertThrows(IOException.class, () -> this.stateManager.load(dir.resolve("doesn't exist")));
    }
}
