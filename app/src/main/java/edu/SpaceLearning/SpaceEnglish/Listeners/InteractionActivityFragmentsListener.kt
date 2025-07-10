/*
 * File: InteractionActivityFragmentsListener.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Listener interface for interaction events between activities and fragments.
 */

package edu.SpaceLearning.SpaceEnglish.Listeners;

import android.content.Context;
import java.io.File;

import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;

/**
 * Interface definition for a listener to handle interaction events between activities and fragments.
 */
public interface InteractionActivityFragmentsListener {

    /**
     * Called when the "Get Started" button is clicked in HomeFragment.
     * @param index The index of the item clicked.
     */
    void onHomeGetStarted(int index);

    /**
     * Called when selecting an image for profile in HomeFragment.
     */
    void onPickImageProfile();

    /**
     * Called to filter the table in TableFragment using the provided RecyclerViewAdapter.
     * @param tableRecyclerAdapter The RecyclerViewAdapter used for filtering.
     */
    void onFilterTableRecycler(RecyclerViewAdapter tableRecyclerAdapter);

    /**
     * Called to send scores to a dialog with specific details.
     * @param categoryType The type of category associated with the scores.
     * @param pointsAdded The points added.
     * @param elementsAdded The elements added.
     * @param userRightAnswerScore The user's score for right answers.
     * @param msg The message to display.
     */
    void onSendScoresToDialog(String categoryType, int pointsAdded, int elementsAdded, int userRightAnswerScore, String msg);

    /**
     * Called when the theme is changed in SettingsFragment.
     * @param isDarkMode True if dark mode is enabled, false otherwise.
     */
    void onChangeTheme(boolean isDarkMode);

    /**
     * Called when clicking the "Send Home" button in DialogFragment.
     * @param categoryType The category type associated with the action.
     */
    void onDialogSendHomeClick(String categoryType);

    /**
     * Called when clicking the "New Quiz" button in DialogFragment.
     */
    void onDialogNewQuiz();

    /**
     * Called to set the required category fragment in QuizNavFragment.
     * @param fragment The fragment to set as a required category.
     */
    void onSetRequiredCategoryFragmentQuiz(Fragment fragment);

    /**
     * Called to open a PDF file with an intent.
     * @param context The context from which to open the PDF.
     * @param pdfFile The PDF file to open.
     */
    void openPdfWithIntent(Context context, File pdfFile);

}
