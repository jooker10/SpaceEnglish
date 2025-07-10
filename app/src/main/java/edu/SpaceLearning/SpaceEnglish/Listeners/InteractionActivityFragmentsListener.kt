/*
 * File: InteractionActivityFragmentsListener.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Listener interface for interaction events between activities and fragments.
 */
package edu.SpaceLearning.SpaceEnglish.Listeners

import android.content.Context
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter
import java.io.File

/**
 * Interface definition for a listener to handle interaction events between activities and fragments.
 */
interface InteractionActivityFragmentsListener {
    /**
     * Called when the "Get Started" button is clicked in HomeFragment.
     * @param index The index of the item clicked.
     */
    fun onHomeGetStarted(index: Int)

    /**
     * Called when selecting an image for profile in HomeFragment.
     */
   // fun onPickImageProfile()

    /**
     * Called to filter the table in TableFragment using the provided RecyclerViewAdapter.
     * @param tableRecyclerAdapter The RecyclerViewAdapter used for filtering.
     */
   // fun onFilterTableRecycler(tableRecyclerAdapter: RecyclerViewAdapter)

    /**
     * Called to send scores to a dialog with specific details.
     * @param categoryType The type of category associated with the scores.
     * @param pointsAdded The points added.
     * @param elementsAdded The elements added.
     * @param userRightAnswerScore The user's score for right answers.
     * @param msg The message to display.
     */
    fun onSendScoresToDialog(
        categoryType: String,
        pointsAdded: Int,
        elementsAdded: Int,
        userRightAnswerScore: Int,
        msg: String
    )

    /**
     * Called when the theme is changed in SettingsFragment.
     * @param isDarkMode True if dark mode is enabled, false otherwise.
     */
  //  fun onChangeTheme(isDarkMode: Boolean)

    /**
     * Called when clicking the "Send Home" button in DialogFragment.
     * @param categoryType The category type associated with the action.
     */
    fun onDialogSendHomeClick(categoryType: String)

    /**
     * Called when clicking the "New Quiz" button in DialogFragment.
     */
    fun onDialogNewQuiz()

    /**
     * Called to set the required category fragment in QuizNavFragment.
     * @param android.R.attr.fragment The fragment to set as a required category.
     */
   // fun onSetRequiredCategoryFragmentQuiz(fragment: Fragment)

    /**
     * Called to open a PDF file with an intent.
     * @param context The context from which to open the PDF.
     * @param pdfFile The PDF file to open.
     */
   // fun openPdfWithIntent(context: Context, pdfFile: File?)
}
