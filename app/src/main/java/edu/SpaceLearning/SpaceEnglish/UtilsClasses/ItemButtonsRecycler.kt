package edu.SpaceLearning.SpaceEnglish.UtilsClasses

/**
 * ItemButtonsRecycler represents an item in a RecyclerView that holds button text and points required information.
 */
class ItemButtonsRecycler(
    /**
     * Get the text displayed on the button.
     *
     * @return The button text.
     */
    @JvmField val btnText: String, // Text displayed on the button
    /**
     * Get the points required information displayed on the item.
     *
     * @return The points required information.
     */
    @JvmField var tvPointsRequired: String // Points required information displayed on the item
) {
    /**
     * Constructor to initialize ItemButtonsRecycler with button text and points required information.
     *
     * @param btnText         The text displayed on the button.
     * @param tvPointsRequired The points required information displayed on the item.
     */
    init {
        this.tvPointsRequired = tvPointsRequired
    }
}
