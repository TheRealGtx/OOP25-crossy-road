package it.unibo.crossyroad.view.api;

import it.unibo.crossyroad.controller.api.ShopController;

public interface ShopView extends View {
    
    /**
     * Set the controller for the shop view.
     * 
     * @param shopController ShopController instance.
     */
    void setController(ShopController shopController);

}
