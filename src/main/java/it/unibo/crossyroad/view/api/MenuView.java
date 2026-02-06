package it.unibo.crossyroad.view.api;

import it.unibo.crossyroad.controller.api.MenuController;

/**
 * Interface representing the menu view component.
 */
public interface MenuView extends View {
    /**
     * Set the controller for the menu view.
     *
     * @param controller MenuController instance
     */
    void setController(MenuController controller);
}
