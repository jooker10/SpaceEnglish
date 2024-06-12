package edu.SpaceLearning.SpaceEnglish.Listeners;

import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;

public interface InteractionMainActivityFragmentsListener {
    void onHomeGetStarted(int index);
    void onPickImage();
    void onTableRecyclerViewClick(RecyclerViewAdapter adapter);
    void onSetQuizCategoryResultClick(String categoryType, int pointsAdded, int elementsAdded , int userRightAnswerScore , String msg);
    void onChangeTheme(boolean isDarkMode);
    void onDialogSendHomeClick(String categoryType);
    void onSheetDialogNewQuizClick();
    void onSetRequiredFragmentQuiz(Fragment fragment);
}
