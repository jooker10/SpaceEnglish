package edu.SpaceLearning.SpaceEnglish

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.Listeners.AdEventListener
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores

/**
 * Fragment to display video buttons for different categories.
 */
class AdCategorySheetFragment : Fragment() {
    private var adListener: AdEventListener? = null // TimerListener for handling ad clicks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Check if the context implements AdEventListener interface
        if (context is AdEventListener) {
            adListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Detach the AdEventListener to prevent memory leaks
        adListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sheet_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize buttons for each video category
        val btnVerbs = view.findViewById<Button>(R.id.btnVideoVerbs)
        val btnSentences = view.findViewById<Button>(R.id.btnVideoSentences)
        val btnPhrasals = view.findViewById<Button>(R.id.btnVideoPhrasals)
        val btnNouns = view.findViewById<Button>(R.id.btnVideoNouns)
        val btnAdjectives = view.findViewById<Button>(R.id.btnVideoAdjs)
        val btnAdverbs = view.findViewById<Button>(R.id.btnVideoAdvs)
        val btnIdioms = view.findViewById<Button>(R.id.btnVideoIdioms)

        // Enable or disable buttons based on permission scores
        setButtonEnabled(btnPhrasals, Scores.totalScore, Constants.PERMISSION_SCORE_PHRASAL)
        setButtonEnabled(btnNouns, Scores.totalScore, Constants.PERMISSION_SCORE_NOUN)
        setButtonEnabled(btnAdjectives, Scores.totalScore, Constants.PERMISSION_SCORE_ADJECTIVE)
        setButtonEnabled(btnAdverbs, Scores.totalScore, Constants.PERMISSION_SCORE_ADVERB)
        setButtonEnabled(btnIdioms, Scores.totalScore, Constants.PERMISSION_SCORE_IDIOM)

        // Set click listeners for each button to show video ads for the respective category
        btnVerbs.setOnClickListener { view13: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_VERB)
        }
        btnSentences.setOnClickListener { view12: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_SENTENCE)
        }
        btnPhrasals.setOnClickListener { view1: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_PHRASAL)
        }
        btnNouns.setOnClickListener { view1: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_NOUNS)
        }
        btnAdjectives.setOnClickListener { view1: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_ADJECTIVE)
        }
        btnAdverbs.setOnClickListener { view1: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_ADVERBS)
        }
        btnIdioms.setOnClickListener { view1: View? ->
            adListener?.playAdForCategory(Constants.CATEGORY_NAME_IDIOMS)
        }
    }

    /**
     * Enables or disables a button based on the main score and required permission score.
     *
     * @param button         The button to enable or disable
     * @param mainScore The global main score to compare against
     * @param requiredScore The required permission score for enabling the button
     */
    private fun setButtonEnabled(button: Button, mainScore: Int, requiredScore: Int) {
        button.isEnabled = mainScore >= requiredScore
    }
}
