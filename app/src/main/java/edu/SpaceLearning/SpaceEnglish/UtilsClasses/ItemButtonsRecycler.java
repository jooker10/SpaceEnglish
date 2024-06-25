package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

/**
 * ItemButtonsRecycler represents an item in a RecyclerView that holds button text and points required information.
 */
public class ItemButtonsRecycler {

    private final String btnText; // Text displayed on the button
    private final String tvPointsRequired; // Points required information displayed on the item

    /**
     * Constructor to initialize ItemButtonsRecycler with button text and points required information.
     *
     * @param btnText         The text displayed on the button.
     * @param tvPointsRequired The points required information displayed on the item.
     */
    public ItemButtonsRecycler(String btnText, String tvPointsRequired) {
        this.btnText = btnText;
        this.tvPointsRequired = tvPointsRequired;
    }

    /**
     * Get the text displayed on the button.
     *
     * @return The button text.
     */
    public String getBtnText() {
        return btnText;
    }

    /**
     * Get the points required information displayed on the item.
     *
     * @return The points required information.
     */
    public String getTvPointsRequired() {
        return tvPointsRequired;
    }
}
