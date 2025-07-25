
package edu.SpaceLearning.SpaceEnglish.Listeners

/**
 * Interface definition for a listener to handle click events on items in a RecyclerView in a quiz context.
 */
interface QuizNavItemClickListener {
    /**
     * Called when an item in the RecyclerView representing quiz items is clicked.
     * @param position The position of the clicked item in the RecyclerView.
     */
    fun onQuizNavItemClicked(position: Int)
}
