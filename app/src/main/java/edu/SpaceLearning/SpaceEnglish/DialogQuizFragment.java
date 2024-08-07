package edu.SpaceLearning.SpaceEnglish;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.DialogQuizScoresBinding;

/**
 * DialogQuizFragment is a dialog fragment that displays quiz scores and options to start a new quiz or return home.
 */
public class DialogQuizFragment extends DialogFragment {

    private InteractionActivityFragmentsListener interactionListener;

    // Argument keys for fragment arguments
    private static final String ARG_CATEGORY_TYPE = "category";
    private static final String ARG_MAIN_SCORE = "arg_main_score";
    private static final String ARG_POINTS_ADDED = "arg_points_added";
    private static final String ARG_ELEMENTS_ADDED = "arg_elements_added";
    private static final String ARG_USER_RIGHT_SCORE = "arg_quiz_counter";
    private static final String ARG_MSG = "msg";

    private DialogQuizScoresBinding binding;
    public static final String TAG = "quizDialog";

    private String categoryType, msg;
    private int pointsAdded, elementsAdded, userRightScore;

    public DialogQuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InteractionActivityFragmentsListener) {
            interactionListener = (InteractionActivityFragmentsListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(ARG_CATEGORY_TYPE);
            pointsAdded = bundle.getInt(ARG_POINTS_ADDED);
            elementsAdded = bundle.getInt(ARG_ELEMENTS_ADDED);
            userRightScore = bundle.getInt(ARG_USER_RIGHT_SCORE);
            msg = bundle.getString(ARG_MSG);
        }
    }

    /**
     * Creates a new instance of DialogQuizFragment with provided arguments.
     *
     * @param categoryType   The category type of the quiz.
     * @param pointsAdded    Points added in the quiz.
     * @param elementsAdded  Elements added during the quiz.
     * @param userRightScore Number of questions answered correctly.
     * @param msg            Message to display in the dialog.
     * @return A new instance of DialogQuizFragment.
     */
    public static DialogQuizFragment newInstance(String categoryType, int pointsAdded, int elementsAdded, int userRightScore, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY_TYPE, categoryType);
        bundle.putInt(ARG_POINTS_ADDED, pointsAdded);
        bundle.putInt(ARG_ELEMENTS_ADDED, elementsAdded);
        bundle.putInt(ARG_USER_RIGHT_SCORE, userRightScore);
        bundle.putString(ARG_MSG, msg);

        DialogQuizFragment dialog_fragment = new DialogQuizFragment();
        dialog_fragment.setArguments(bundle);
        return dialog_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_quiz_scores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogQuizScoresBinding.bind(view);

        // Set text and events for UI elements
        binding.tvDialogCongrat.setText(msg); // Set congratulatory message
        if (MainActivity.textToSpeechManager != null) {
            MainActivity.textToSpeechManager.speak(msg); // Speak congratulatory message
        }

        binding.tvDialogResult.setText(userRightScore + "/" + Utils.maxQuestionsPerQuiz); // Display quiz result
        binding.tvPointsAdded.setText(String.valueOf(pointsAdded)); // Display points added
        binding.tvElementsAdded.setText(String.valueOf(elementsAdded)); // Display elements added
        binding.tvDialogCategoryElementAdded.setText(categoryType + " Added :"); // Display category type

        // Button click listeners
        binding.btnNewQuiz.setOnClickListener(v -> {
            interactionListener.onDialogNewQuiz(); // Notify listener to start a new quiz
            dismiss(); // Dismiss the dialog
        });

        binding.btnSendHome.setOnClickListener(view1 -> {
            interactionListener.onDialogSendHomeClick(categoryType); // Notify listener to send home with category type
            dismiss(); // Dismiss the dialog
        });
    }
}
