/*
 * File: ActivityFragmentInteractionListener.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: TimerListener interface for interaction events between activities and fragments.
 */
package edu.SpaceLearning.SpaceEnglish.Listeners

import android.content.Context
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerTableAdapter
import java.io.File

/**
 * Interface definition for a listener to handle interaction events between activities and fragments.
 */
interface ActivityFragmentInteractionListener {
    /**
     * Called when the "Get Started" button is clicked in HomeFragment.
     * @param index The index of the item clicked.
     */
    fun onHomeStartClicked(index: Int)

    /**
     * Called to filter the table in TableFragment using the provided RecyclerViewAdapter.
     * @param tableRecyclerAdapter The RecyclerViewAdapter used for filtering.
     */
    fun onTableFilterRequested(tableRecyclerAdapter: RecyclerTableAdapter)

    /**
     * Called to send scores to a dialog with specific details.
     * @param categoryType The type of category associated with the scores.
     * @param pointsAdded The points added.
     * @param elementsAdded The elements added.
     * @param userRightAnswerScore The user's score for right answers.
     * @param msg The message to display.
     */
    fun onScoresSubmittedToDialog(categoryType: String, pointsAdded: Int, elementsAdded: Int, userRightAnswerScore: Int, msg: String)

    /**
     * Called when the theme is changed in SettingsFragment.
     * @param isDarkMode True if dark mode is enabled, false otherwise.
     */
    fun onThemeToggled(isDarkMode: Boolean)

    /**
     * Called when clicking the "Send Home" button in DialogFragment.
     * @param categoryType The category type associated with the action.
     */
    fun onDialogReturnHomeClicked(categoryType: String)

    /**
     * Called when clicking the "New Quiz" button in DialogFragment.
     */
    fun onStartNewQuizClicked()

    /**
     * Called to set the required category fragment in QuizNavigationFragment.
     * @param android.R.attr.fragment The fragment to set as a required category.
     */
    fun onSetQuizCategoryFragment(fragment: Fragment)

    /**
     * Called to open a PDF file with an intent.
     * @param context The context from which to open the PDF.
     * @param pdfFile The PDF file to open.
     */
    fun onPdfOpenRequested(context: Context, pdfFile: File?)
}
