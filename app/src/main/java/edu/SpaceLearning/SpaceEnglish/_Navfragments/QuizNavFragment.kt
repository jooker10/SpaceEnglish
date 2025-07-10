/*
 * File: QuizNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment representing quiz navigation in the SpaceEnglish app.
 *          Provides UI for selecting quiz categories, setting quiz parameters,
 *          and interaction handling with RecyclerView.
 */
package edu.SpaceLearning.SpaceEnglish._Navfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerQuizNavAdapter
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.Listeners.RecyclerItemQuizListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentNavQuizBinding

// Android imports

/**
 * A fragment that handles quiz navigation within the SpaceEnglish app.
 * This fragment includes UI components for selecting quiz categories,
 * configuring quiz parameters, and enabling/disabling quiz category buttons
 * based on user scores.
 */
class QuizNavFragment  // Default constructor
    : Fragment() {
    // Binding variable for FragmentNavQuizBinding
    private lateinit var binding: FragmentNavQuizBinding

    // Listener for interaction events with the parent activity
    private var interactionListener: InteractionActivityFragmentsListener? = null

    // Lists to store UI components dynamically
    private val buttons = ArrayList<Button>()
    private val textViews = ArrayList<TextView>()

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_quiz, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, providing a view hierarchy for this fragment.
     * @param view The View returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view binding
        binding = FragmentNavQuizBinding.bind(view)

        // Initialize UI components and set up RecyclerView
        //addUIToListAndInitialize();
        initAutoTextViewMaxQuestionPerQuiz()
        setUpRecyclerView()
    }

    /**
     * Initializes the AutoCompleteTextView for selecting maximum questions per quiz.
     * Configures the adapter and handles item selection events.
     */
    private fun initAutoTextViewMaxQuestionPerQuiz() {
        // Set initial value from Utils.maxQuestionsPerQuiz
        binding.autoTvmaxQuestionsPerQuiz.setText(Utils.maxQuestionsPerQuiz.toString())

        // Update Utils.maxQuestionsPerQuiz on item selection
        binding.autoTvmaxQuestionsPerQuiz.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
                val selectedValue = binding.autoTvmaxQuestionsPerQuiz.text.toString()
                Utils.maxQuestionsPerQuiz = selectedValue.toInt()
            }

        // Set adapter for AutoCompleteTextView
        val maxQuestionsList =
            resources.getStringArray(R.array.quiz_option_max_questions_per_quizzes)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, maxQuestionsList)
        binding.autoTvmaxQuestionsPerQuiz.setAdapter(adapter)
    }

    /**
     * Adds UI buttons and text views to lists and initializes them.
     * Enables/disables buttons based on user scores and handles button click events.
     */
    /*private void addUIToListAndInitialize() {
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
    }*/
    /**
     * Enables or disables a button based on the global main score compared to permission score.
     * @param button The Button to enable/disable.
     * @param tvRequiredLabel The TextView indicating requirements.
     * @param globalMainScore The overall score of the user.
     * @param permissionScore The required score to enable the button.
     */
    private fun enableButton(
        button: Button,
        tvRequiredLabel: TextView,
        globalMainScore: Int,
        permissionScore: Int
    ) {
        if (globalMainScore >= permissionScore) {
            button.isEnabled = true
            tvRequiredLabel.visibility = View.GONE
        } else {
            button.isEnabled = false
            tvRequiredLabel.visibility = View.VISIBLE
        }
    }

    /**
     * Sets up RecyclerView with adapter and layout manager for displaying quiz categories.
     */
    private fun setUpRecyclerView() {
        val adapter = RecyclerQuizNavAdapter(Utils.itemRecyclerQuizNavList, object : RecyclerItemQuizListener {
            override fun onQuizItemClicked(position: Int) {
                /*interactionListener?.onSetRequiredCategoryFragmentQuiz(
                    QuizCategoriesFragment.getInstance(
                        Constants.categoryNameArray[position]
                ))*/
            }

        })
        /*val adapter = RecyclerQuizNavAdapter(
            Utils.itemRecyclerQuizNavList
        ) { position: Int ->
            interactionListener!!.onSetRequiredCategoryFragmentQuiz(
                QuizCategoriesFragment.getInstance(
                    Constants.categoryNameArray[position]
                )
            )
        } */
        binding.recyclerViewQuizNav.adapter = adapter
        binding.recyclerViewQuizNav.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewQuizNav.setHasFixedSize(true)
    }

    /**
     * Called when the fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
    }

    /**
     * Called when the fragment is no longer attached to its context.
     */
    override fun onDetach() {
        super.onDetach()
        // Release the interaction listener to avoid memory leaks
        interactionListener = null
    }
}
