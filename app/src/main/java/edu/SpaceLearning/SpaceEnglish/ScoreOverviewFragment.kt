package edu.SpaceLearning.SpaceEnglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.SpaceLearning.SpaceEnglish.Adapters.HomeInfoScoreAdapter
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.ScoreEntry
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentHomeInfoScoreRecyclerviewBinding

/**
 * Fragment for displaying various scores and statistics in a RecyclerView.
 */
class ScoreOverviewFragment : Fragment() {
    private var selectedCategory: String? = null
    private var binding: FragmentHomeInfoScoreRecyclerviewBinding? = null

    // Lists to hold different categories of ScoreEntry items
    private val totalScoreItems = ArrayList<ScoreEntry>()
    private val verbScoreItems = ArrayList<ScoreEntry>()
    private val sentenceScoreItems = ArrayList<ScoreEntry>()
    private val phrasalScoreItems = ArrayList<ScoreEntry>()
    private val nounScoreItems = ArrayList<ScoreEntry>()
    private val adjectiveScoreItems = ArrayList<ScoreEntry>()
    private val adverbScoreItems = ArrayList<ScoreEntry>()
    private val idiomScoreItems = ArrayList<ScoreEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedCategory = requireArguments().getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_info_score_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeInfoScoreRecyclerviewBinding.bind(view)

        // Initialize and populate scores categories
        initializeAggregatedScores()
        populateScoreItemLists()
        setupRecyclerView()
    }

    /**
     * Sets up the RecyclerView with an adapter for displaying scores.
     */
    private fun setupRecyclerView() {
        val homeInfoScoreAdapter = HomeInfoScoreAdapter(
            getScoreItemsForCategory(
                selectedCategory!!
            )
        )
        binding!!.infoScoresRecyclerView.adapter = homeInfoScoreAdapter
        binding!!.infoScoresRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    /**
     * Initializes total scores categories based on various score calculations.
     */
    private fun initializeAggregatedScores() {
        Scores.totalQuizCompleted =
            (Scores.verbQuizCompleted + Scores.sentenceQuizCompleted + Scores.phrasalQuizCompleted + Scores.nounQuizCompleted
                    + Scores.adjQuizCompleted + Scores.advQuizCompleted + Scores.idiomQuizCompleted)
        Scores.totalQuizCompletedCorrectly =
            (Scores.verbQuizCompletedCorrectly + Scores.sentenceQuizCompletedCorrectly + Scores.phrasalQuizCompletedCorrectly
                    + Scores.nounQuizCompletedCorrectly + Scores.adjQuizCompletedCorrectly + Scores.advQuizCompletedCorrectly + Scores.idiomQuizCompletedCorrectly)
        Scores.totalElementsAdded =
            Scores.verbAdded + Scores.sentenceAdded + Scores.phrasalAdded + Scores.nounAdded + Scores.adjAdded + Scores.advAdded + Scores.idiomAdded
        Scores.totalElementsAddedVideo =
            Scores.verbAddedVideo + Scores.sentenceAddedVideo + Scores.phrasalAddedVideo + Scores.nounAddedVideo + Scores.adjAddedVideo + Scores.advAddedVideo + Scores.idiomAddedVideo
        Scores.totalPointsAddedVideo =
            (Scores.verbPointsAddedVideo + Scores.sentencePointsAddedVideo + Scores.phrasalPointsAddedVideo + Scores.nounPointsAddedVideo
                    + Scores.adjPointsAddedVideo + Scores.advPointsAddedVideo + Scores.idiomPointsAddedVideo)
        Scores.totalScore =
            Scores.verbScore + Scores.sentenceScore + Scores.phrasalScore + Scores.nounScore + Scores.adjScore + Scores.advScore + Scores.idiomScore
    }

    /**
     * Retrieves the list of ScoreEntry items based on the selected category.
     *
     * @param category The category of scores to retrieve.
     * @return The corresponding list of ScoreEntry items.
     */
    fun getScoreItemsForCategory(category: String): ArrayList<ScoreEntry> {
        return when (category) {
            Constants.CATEGORY_NAME_ALL -> totalScoreItems
            Constants.CATEGORY_NAME_VERB -> verbScoreItems
            Constants.CATEGORY_NAME_SENTENCE -> sentenceScoreItems
            Constants.CATEGORY_NAME_PHRASAL -> phrasalScoreItems
            Constants.CATEGORY_NAME_NOUNS -> nounScoreItems
            Constants.CATEGORY_NAME_ADJECTIVE -> adjectiveScoreItems
            Constants.CATEGORY_NAME_ADVERBS -> adverbScoreItems
            Constants.CATEGORY_NAME_IDIOMS -> idiomScoreItems
            else -> totalScoreItems // Default to total score list
        }
    }

    /**
     * Fills each category's ScoreEntry items list with corresponding scores and statistics.
     */
    private fun populateScoreItemLists() {
        // Clear existing lists
        totalScoreItems.clear()
        verbScoreItems.clear()
        sentenceScoreItems.clear()
        phrasalScoreItems.clear()
        nounScoreItems.clear()
        adjectiveScoreItems.clear()
        adverbScoreItems.clear()
        idiomScoreItems.clear()

        // Add ScoreEntry items for each category
        totalScoreItems.add(ScoreEntry("Total Score", Scores.totalScore.toString()))
        totalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.totalQuizCompleted.toString()
            )
        )
        totalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.totalQuizCompletedCorrectly.toString()
            )
        )
        totalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.totalElementsAdded.toString()
            )
        )
        totalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.totalElementsAddedVideo.toString()
            )
        )
        totalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.totalPointsAddedVideo.toString()
            )
        )

        verbScoreItems.add(ScoreEntry("Verbs Score", Scores.verbScore.toString()))
        verbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.verbQuizCompleted.toString()
            )
        )
        verbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.verbQuizCompletedCorrectly.toString()
            )
        )
        verbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.verbAdded.toString()
            )
        )
        verbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.verbAddedVideo.toString()
            )
        )
        verbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.verbPointsAddedVideo.toString()
            )
        )

        sentenceScoreItems.add(ScoreEntry("Sentences Score", Scores.sentenceScore.toString()))
        sentenceScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.sentenceQuizCompleted.toString()
            )
        )
        sentenceScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.sentenceQuizCompletedCorrectly.toString()
            )
        )
        sentenceScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.sentenceAdded.toString()
            )
        )
        sentenceScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.sentenceAddedVideo.toString()
            )
        )
        sentenceScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.sentencePointsAddedVideo.toString()
            )
        )

        phrasalScoreItems.add(ScoreEntry("Phrasal Verbs Score", Scores.phrasalScore.toString()))
        phrasalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.phrasalQuizCompleted.toString()
            )
        )
        phrasalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.phrasalQuizCompletedCorrectly.toString()
            )
        )
        phrasalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.phrasalAdded.toString()
            )
        )
        phrasalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.phrasalAddedVideo.toString()
            )
        )
        phrasalScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.phrasalPointsAddedVideo.toString()
            )
        )

        nounScoreItems.add(ScoreEntry("Nouns Score", Scores.nounScore.toString()))
        nounScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.nounQuizCompleted.toString()
            )
        )
        nounScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.nounQuizCompletedCorrectly.toString()
            )
        )
        nounScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.nounAdded.toString()
            )
        )
        nounScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.nounAddedVideo.toString()
            )
        )
        nounScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.nounPointsAddedVideo.toString()
            )
        )

        adjectiveScoreItems.add(ScoreEntry("Adjectives Score", Scores.adjScore.toString()))
        adjectiveScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.adjQuizCompleted.toString()
            )
        )
        adjectiveScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.adjQuizCompletedCorrectly.toString()
            )
        )
        adjectiveScoreItems.add(ScoreEntry(Constants.QUIZ_ELEMENT_ADDED, Scores.adjAdded.toString()))
        adjectiveScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.adjAddedVideo.toString()
            )
        )
        adjectiveScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.adjPointsAddedVideo.toString()
            )
        )

        adverbScoreItems.add(ScoreEntry("Adverbs Score", Scores.advScore.toString()))
        adverbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.advQuizCompleted.toString()
            )
        )
        adverbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.advQuizCompletedCorrectly.toString()
            )
        )
        adverbScoreItems.add(ScoreEntry(Constants.QUIZ_ELEMENT_ADDED, Scores.advAdded.toString()))
        adverbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.advAddedVideo.toString()
            )
        )
        adverbScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.advPointsAddedVideo.toString()
            )
        )

        idiomScoreItems.add(ScoreEntry("Idioms Score", Scores.idiomScore.toString()))
        idiomScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED,
                Scores.idiomQuizCompleted.toString()
            )
        )
        idiomScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_COMPLETED_SUCCESSFULLY,
                Scores.idiomQuizCompletedCorrectly.toString()
            )
        )
        idiomScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED,
                Scores.idiomAdded.toString()
            )
        )
        idiomScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_ELEMENT_ADDED_ADS,
                Scores.idiomAddedVideo.toString()
            )
        )
        idiomScoreItems.add(
            ScoreEntry(
                Constants.QUIZ_POINT_ADDED_ADS,
                Scores.idiomPointsAddedVideo.toString()
            )
        )
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        /**
         * Creates a new instance of ScoreOverviewFragment with a specified category.
         *
         * @param category The category of scores to display.
         * @return A new instance of ScoreOverviewFragment.
         */
        @JvmStatic
        fun newInstance(category: String?): ScoreOverviewFragment {
            val fragment = ScoreOverviewFragment()
            val bundle = Bundle()
            bundle.putString(ARG_CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }
}
