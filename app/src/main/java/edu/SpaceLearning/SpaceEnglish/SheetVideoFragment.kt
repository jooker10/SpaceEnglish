package edu.SpaceLearning.SpaceEnglish

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores

/**
 * Fragment to display video buttons for different categories.
 */
class SheetVideoFragment : Fragment() {
    private var adsClickListener: AdsClickListener? = null // Listener for handling ad clicks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Check if the context implements AdsClickListener interface
        if (context is AdsClickListener) {
            adsClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Detach the AdsClickListener to prevent memory leaks
        adsClickListener = null
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
        val btnVideoVerbs = view.findViewById<Button>(R.id.btnVideoVerbs)
        val btnVideoSentences = view.findViewById<Button>(R.id.btnVideoSentences)
        val btnVideoPhrasals = view.findViewById<Button>(R.id.btnVideoPhrasals)
        val btnVideoNouns = view.findViewById<Button>(R.id.btnVideoNouns)
        val btnVideoAdjs = view.findViewById<Button>(R.id.btnVideoAdjs)
        val btnVideoAdvs = view.findViewById<Button>(R.id.btnVideoAdvs)
        val btnVideoIdoms = view.findViewById<Button>(R.id.btnVideoIdoms)

        // Enable or disable buttons based on permission scores
        enableButton(btnVideoPhrasals, Scores.totalScore, Constants.permissionPhrasalScore)
        enableButton(btnVideoNouns, Scores.totalScore, Constants.permissionNounScore)
        enableButton(btnVideoAdjs, Scores.totalScore, Constants.permissionAdjScore)
        enableButton(btnVideoAdvs, Scores.totalScore, Constants.permissionAdvScore)
        enableButton(btnVideoIdoms, Scores.totalScore, Constants.permissionIdiomScore)

        // Set click listeners for each button to show video ads for the respective category
        btnVideoVerbs.setOnClickListener { view13: View? ->
            adsClickListener!!.onShowVideoAds(Constants.VERB_NAME)
        }
        btnVideoSentences.setOnClickListener { view12: View? ->
            adsClickListener!!.onShowVideoAds(Constants.SENTENCE_NAME)
        }
        btnVideoPhrasals.setOnClickListener { view1: View? ->
            adsClickListener!!.onShowVideoAds(Constants.PHRASAL_NAME)
        }
        btnVideoNouns.setOnClickListener { view1: View? ->
            adsClickListener!!.onShowVideoAds(Constants.NOUN_NAME)
        }
        btnVideoAdjs.setOnClickListener { view1: View? ->
            adsClickListener!!.onShowVideoAds(Constants.ADJ_NAME)
        }
        btnVideoAdvs.setOnClickListener { view1: View? ->
            adsClickListener!!.onShowVideoAds(Constants.ADV_NAME)
        }
        btnVideoIdoms.setOnClickListener { view1: View? ->
            adsClickListener!!.onShowVideoAds(Constants.IDIOM_NAME)
        }
    }

    /**
     * Enables or disables a button based on the main score and required permission score.
     *
     * @param button         The button to enable or disable
     * @param globalMainScore The global main score to compare against
     * @param permissionScore The required permission score for enabling the button
     */
    private fun enableButton(button: Button, globalMainScore: Int, permissionScore: Int) {
        button.isEnabled = globalMainScore >= permissionScore
    }
}
