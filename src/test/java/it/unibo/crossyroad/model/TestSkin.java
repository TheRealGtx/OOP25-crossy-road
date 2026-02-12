package it.unibo.crossyroad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.crossyroad.model.api.Skin;
import it.unibo.crossyroad.model.impl.SkinImpl;

/**
 * Test class for the {@link SkinImpl} class.
 */
class TestSkin {

    private static final String NAME = "Wizard";
    private static final String ID = "wizard";
    private static final int PRICE = 90;
    private static final Path OVERHEAD_PATH = Paths.get("resources/skins/wizard_overhead.png");
    private static final Path FRONT_PATH = Paths.get("resources/skins/wizard_front.png");
    private Skin skin;

    /**
     * Sets up a new instance of skin.
     */
    @BeforeEach
    void setUp() {
        this.skin = new SkinImpl.Builder()
            .name(NAME)
            .id(ID)
            .price(PRICE)
            .overheadImagePath(OVERHEAD_PATH)
            .frontImagePath(FRONT_PATH)
            .build();
    }

    /**
     * Test that getName returns the correct skin name.
     */
    @Test
    void testGetName() {
        assertEquals(NAME, this.skin.getName());
    }

    /**
     * Test that getId return the correct skin id. 
     */
    @Test
    void testGetId() {
        assertEquals(ID, this.skin.getId());
    }

    /**
     * Test that getPrice return the correct skin price.
     */
    @Test
    void testGetPrice() {
        assertEquals(PRICE, this.skin.getPrice());
    }

    /**
     * Test that getOverheadImage return the correct skin overhead image path.
     */
    @Test
    void testGetOverheadPathImage() {
        assertEquals(OVERHEAD_PATH, this.skin.getOverheadImage());
    }

    /**
     * Test that getFrontImage return the correct skin front image path.
     */
    @Test
    void testGetFrontPathImage() {
        assertEquals(FRONT_PATH, this.skin.getFrontImage());
    }
}
