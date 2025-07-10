/*
 * File: RecyclerItemQuizListener.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Listener interface for handling click events on items in a RecyclerView in a quiz context.
 */

package edu.SpaceLearning.SpaceEnglish.Listeners;

/**
 * Interface definition for a listener to handle click events on items in a RecyclerView in a quiz context.
 */
public interface RecyclerItemQuizListener {

    /**
     * Called when an item in the RecyclerView representing quiz items is clicked.
     * @param position The position of the clicked item in the RecyclerView.
     */
    void onQuizItemClicked(int position);
}
