package edu.SpaceLearning.SpaceEnglish.Listeners;

import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;

public interface InteractionActivityFragmentsListener {
    void onHomeGetStarted(int index); // Button start quiz or learn in HomeFragment.
    void onPickImageProfile(); // Pick Image for profile in HomeFragment.
    void onFilterTableRecycler(RecyclerViewAdapter tableRecyclerAdapter); // Filter table in TableFragment.
    void onSendScoresToDialog(String categoryType, int pointsAdded, int elementsAdded , int userRightAnswerScore , String msg); // Set Scores to DialogFragment.
    void onChangeTheme(boolean isDarkMode);   // change theme in SettingsFragment.
    void onDialogSendHomeClick(String categoryType); // Button return to HomeFragment in DialogFragment.
    void onDialogNewQuiz();   // button new quiz in DialogFragment.
    void onSetRequiredCategoryFragmentQuiz(Fragment fragment); // Set the click Listener to each button in QuizNavFragment.
}
