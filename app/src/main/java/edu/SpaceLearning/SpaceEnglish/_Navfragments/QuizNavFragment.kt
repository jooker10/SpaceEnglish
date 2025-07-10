/*
 * File: QuizNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment representing quiz navigation in the SpaceEnglish app.
 *          Provides UI for selecting quiz categories, setting quiz parameters,
 *          and interaction handling with RecyclerView.
 */

package edu.SpaceLearning.SpaceEnglish._Navfragments;

// Android imports
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerQuizNavAdapter;
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentNavQuizBinding;

/**
 * A fragment that handles quiz navigation within the SpaceEnglish app.
 * This fragment includes UI components for selecting quiz categories,
 * configuring quiz parameters, and enabling/disabling quiz category buttons
 * based on user scores.
 */
public class QuizNavFragment extends Fragment {

    // Binding variable for FragmentNavQuizBinding
    private FragmentNavQuizBinding binding;

    // Listener for interaction events with the parent activity
    private InteractionActivityFragmentsListener interactionListener;

    // Lists to store UI components dynamically
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<TextView> textViews = new ArrayList<>();

    // Default constructor
    public QuizNavFragment() {
        // Required empty public constructor
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_quiz, container, false);
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view hierarchy for this fragment.
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize view binding
        binding = FragmentNavQuizBinding.bind(view);

        // Initialize UI components and set up RecyclerView
        addUIToListAndInitialize();
        initAutoTextViewMaxQuestionPerQuiz();
        setUpRecyclerView();
    }

    /**
     * Initializes the AutoCompleteTextView for selecting maximum questions per quiz.
     * Configures the adapter and handles item selection events.
     */
    private void initAutoTextViewMaxQuestionPerQuiz() {
        // Set initial value from Utils.maxQuestionsPerQuiz
        binding.autoTvmaxQuestionsPerQuiz.setText(String.valueOf(Utils.maxQuestionsPerQuiz));

        // Update Utils.maxQuestionsPerQuiz on item selection
        binding.autoTvmaxQuestionsPerQuiz.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedValue = binding.autoTvmaxQuestionsPerQuiz.getText().toString();
            Utils.maxQuestionsPerQuiz = Integer.parseInt(selectedValue);
        });

        // Set adapter for AutoCompleteTextView
        String[] maxQuestionsList = getResources().getStringArray(R.array.quiz_option_max_questions_per_quizzes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, maxQuestionsList);
        binding.autoTvmaxQuestionsPerQuiz.setAdapter(adapter);
    }

    /**
     * Adds UI buttons and text views to lists and initializes them.
     * Enables/disables buttons based on user scores and handles button click events.
     */
    private void addUIToListAndInitialize() {
        // Add buttons and text views to respective lists
        buttons.add(binding.btnVerbs);
        buttons.add(binding.btnSentences);
        buttons.add(binding.btnPhrasals);
        buttons.add(binding.btnNouns);
        buttons.add(binding.btnAdjs);
        buttons.add(binding.btnAdvs);
        buttons.add(binding.btnIdioms);

        textViews.add(binding.tvPhrasalRequiredLabel);
        textViews.add(binding.tvNounRequiredLabel);
        textViews.add(binding.tvAdjRequiredLabel);
        textViews.add(binding.tvAdvRequiredLabel);
        textViews.add(binding.tvIdiomRequiredLabel);

        // Enable/disable buttons and set click listeners
        for (int i = 0; i < Constants.permissionCategoryScoreArray.length; i++) {
            enableButton(buttons.get(i + 2), textViews.get(i), Scores.totalScore, Constants.permissionCategoryScoreArray[i]);
        }

        for (int i = 0; i < buttons.size(); i++) {
            final int index = i;
            buttons.get(i).setOnClickListener(view ->
                    interactionListener.onSetRequiredCategoryFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.categoryNameArray[index])));
        }
    }

    /**
     * Enables or disables a button based on the global main score compared to permission score.
     * @param button The Button to enable/disable.
     * @param tvRequiredLabel The TextView indicating requirements.
     * @param globalMainScore The overall score of the user.
     * @param permissionScore The required score to enable the button.
     */
    private void enableButton(Button button, TextView tvRequiredLabel, int globalMainScore, int permissionScore) {
        if (globalMainScore >= permissionScore) {
            button.setEnabled(true);
            tvRequiredLabel.setVisibility(View.GONE);
        } else {
            button.setEnabled(false);
            tvRequiredLabel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Sets up RecyclerView with adapter and layout manager for displaying quiz categories.
     */
    private void setUpRecyclerView() {
        RecyclerQuizNavAdapter adapter = new RecyclerQuizNavAdapter(Utils.itemRecyclerQuizNavList, position ->
                interactionListener.onSetRequiredCategoryFragmentQuiz(QuizCategoriesFragment.getInstance(Constants.categoryNameArray[position])));
        binding.recyclerViewQuizNav.setAdapter(adapter);
        binding.recyclerViewQuizNav.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerViewQuizNav.setHasFixedSize(true);
    }

    /**
     * Called when the fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context instanceof InteractionActivityFragmentsListener) {
            interactionListener = (InteractionActivityFragmentsListener) context;
        }
    }

    /**
     * Called when the fragment is no longer attached to its context.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        // Release the interaction listener to avoid memory leaks
        interactionListener = null;
    }
}
