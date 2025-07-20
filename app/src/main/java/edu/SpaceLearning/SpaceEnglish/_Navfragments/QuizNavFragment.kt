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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerQuizNavAdapter
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.Listeners.RecyclerItemQuizListener
import edu.SpaceLearning.SpaceEnglish.QuizFiles.QuizCategoriesSubFragment
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentNavQuizBinding

// Android imports

/**
 * A fragment that handles quiz navigation within the SpaceEnglish app.
 * This fragment includes UI components for selecting quiz categories,
 * configuring quiz parameters, and enabling/disabling quiz category buttons
 * based on user scores.
 */
class QuizNavFragment  : Fragment() {
    // Binding variable for FragmentNavQuizBinding
    private lateinit var binding: FragmentNavQuizBinding

    // Listener for interaction events with the parent activity
    private var interactionListener: InteractionActivityFragmentsListener? = null


    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        setUpTvMaxQuestionPerQuiz()
        setUpRecyclerView()
    }

    /**
     * Initializes the AutoCompleteTextView for selecting maximum questions per quiz.
     * Configures the adapter and handles item selection events.
     */
    private fun setUpTvMaxQuestionPerQuiz() {
        // Set initial value from Utils.maxQuestionsPerQuiz
        binding.tvMaxQuestionsPerQuiz.setText(Utils.maxQuestionsPerQuiz.toString())
        // Update Utils.maxQuestionsPerQuiz on item selection
        binding.tvMaxQuestionsPerQuiz.onItemClickListener =
            OnItemClickListener { parent: AdapterView<*>?, view1: View?, position: Int, id: Long ->
                val selectedValue = binding.tvMaxQuestionsPerQuiz.text.toString()
                Utils.maxQuestionsPerQuiz = selectedValue.toInt()
            }
        // Set adapter for AutoCompleteTextView
        val maxQuestionsList =
            resources.getStringArray(R.array.quiz_option_max_questions_per_quizzes)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, maxQuestionsList)
        binding.tvMaxQuestionsPerQuiz.setAdapter(adapter)

    }

    /**
     * Sets up RecyclerView with adapter and layout manager for displaying quiz categories.
     */
    private fun setUpRecyclerView() {
        val adapter = RecyclerQuizNavAdapter(Utils.itemRecyclerQuizNavList, object : RecyclerItemQuizListener {
            override fun onQuizItemClicked(position: Int) {
                interactionListener?.onSetRequiredCategoryFragmentQuiz(
                    QuizCategoriesSubFragment.getInstance(
                        Constants.categoryNamesList[position]
                    )
                )
            }

        })
        binding.recyclerViewQuizNav.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireActivity())
            it.setHasFixedSize(true)
        }

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
