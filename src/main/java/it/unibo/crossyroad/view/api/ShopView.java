package it.unibo.crossyroad.view.api;

import it.unibo.crossyroad.controller.api.ShopController;

/**
 * Interface representing the view for the shop.
 */
public interface ShopView extends View {

    /**
     * Set the controller for the shop view.
     * 
     * @param shopController ShopController instance.
     */
    void setController(ShopController shopController);

}
