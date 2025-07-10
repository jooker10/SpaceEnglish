package edu.SpaceLearning.SpaceEnglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.SpaceLearning.SpaceEnglish.Adapters.HomeScoresRecyclerAdapter
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.InfoScore
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentHomeInfoScoreRecyclerviewBinding

/**
 * Fragment for displaying various scores and statistics in a RecyclerView.
 */
class HomeInfoScoresPager2Fragment : Fragment() {
    private var category: String? = null
    private var binding: FragmentHomeInfoScoreRecyclerviewBinding? = null

    // Lists to hold different categories of InfoScore items
    private val infoItemsTotalScore = ArrayList<InfoScore>()
    private val infoItemsVerbScore = ArrayList<InfoScore>()
    private val infoItemsSentenceScore = ArrayList<InfoScore>()
    private val infoItemsPhrasalScore = ArrayList<InfoScore>()
    private val infoItemsNounScore = ArrayList<InfoScore>()
    private val infoItemsAdjScore = ArrayList<InfoScore>()
    private val infoItemsAdvScore = ArrayList<InfoScore>()
    private val infoItemsIdiomScore = ArrayList<InfoScore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            category = requireArguments().getString(ARG_CATEGORY)
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
        initTotalScoresCategories()
        fillInfoScoresItemsList()
        setUpRecyclerViewWithAdapter()
    }

    /**
     * Sets up the RecyclerView with an adapter for displaying scores.
     */
    private fun setUpRecyclerViewWithAdapter() {
        val homeScoresRecyclerAdapter = HomeScoresRecyclerAdapter(
            currentInfoScoresItemsList(
                category!!
            )
        )
        binding!!.infoScoresRecyclerView.adapter = homeScoresRecyclerAdapter
        binding!!.infoScoresRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    /**
     * Initializes total scores categories based on various score calculations.
     */
    private fun initTotalScoresCategories() {
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
     * Retrieves the list of InfoScore items based on the selected category.
     *
     * @param category The category of scores to retrieve.
     * @return The corresponding list of InfoScore items.
     */
    fun currentInfoScoresItemsList(category: String): ArrayList<InfoScore> {
        return when (category) {
            Constants.ALL_NAME -> infoItemsTotalScore
            Constants.VERB_NAME -> infoItemsVerbScore
            Constants.SENTENCE_NAME -> infoItemsSentenceScore
            Constants.PHRASAL_NAME -> infoItemsPhrasalScore
            Constants.NOUN_NAME -> infoItemsNounScore
            Constants.ADJ_NAME -> infoItemsAdjScore
            Constants.ADV_NAME -> infoItemsAdvScore
            Constants.IDIOM_NAME -> infoItemsIdiomScore
            else -> infoItemsTotalScore // Default to total score list
        }
    }

    /**
     * Fills each category's InfoScore items list with corresponding scores and statistics.
     */
    private fun fillInfoScoresItemsList() {
        // Clear existing lists
        infoItemsTotalScore.clear()
        infoItemsVerbScore.clear()
        infoItemsSentenceScore.clear()
        infoItemsPhrasalScore.clear()
        infoItemsNounScore.clear()
        infoItemsAdjScore.clear()
        infoItemsAdvScore.clear()
        infoItemsIdiomScore.clear()

        // Add InfoScore items for each category
        infoItemsTotalScore.add(InfoScore("Total Score", Scores.totalScore.toString()))
        infoItemsTotalScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.totalQuizCompleted.toString()
            )
        )
        infoItemsTotalScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.totalQuizCompletedCorrectly.toString()
            )
        )
        infoItemsTotalScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.totalElementsAdded.toString()
            )
        )
        infoItemsTotalScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.totalElementsAddedVideo.toString()
            )
        )
        infoItemsTotalScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.totalPointsAddedVideo.toString()
            )
        )

        infoItemsVerbScore.add(InfoScore("Verbs Score", Scores.verbScore.toString()))
        infoItemsVerbScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.verbQuizCompleted.toString()
            )
        )
        infoItemsVerbScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.verbQuizCompletedCorrectly.toString()
            )
        )
        infoItemsVerbScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.verbAdded.toString()
            )
        )
        infoItemsVerbScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.verbAddedVideo.toString()
            )
        )
        infoItemsVerbScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.verbPointsAddedVideo.toString()
            )
        )

        infoItemsSentenceScore.add(InfoScore("Sentences Score", Scores.sentenceScore.toString()))
        infoItemsSentenceScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.sentenceQuizCompleted.toString()
            )
        )
        infoItemsSentenceScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.sentenceQuizCompletedCorrectly.toString()
            )
        )
        infoItemsSentenceScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.sentenceAdded.toString()
            )
        )
        infoItemsSentenceScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.sentenceAddedVideo.toString()
            )
        )
        infoItemsSentenceScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.sentencePointsAddedVideo.toString()
            )
        )

        infoItemsPhrasalScore.add(InfoScore("Phrasal Verbs Score", Scores.phrasalScore.toString()))
        infoItemsPhrasalScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.phrasalQuizCompleted.toString()
            )
        )
        infoItemsPhrasalScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.phrasalQuizCompletedCorrectly.toString()
            )
        )
        infoItemsPhrasalScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.phrasalAdded.toString()
            )
        )
        infoItemsPhrasalScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.phrasalAddedVideo.toString()
            )
        )
        infoItemsPhrasalScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.phrasalPointsAddedVideo.toString()
            )
        )

        infoItemsNounScore.add(InfoScore("Nouns Score", Scores.nounScore.toString()))
        infoItemsNounScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.nounQuizCompleted.toString()
            )
        )
        infoItemsNounScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.nounQuizCompletedCorrectly.toString()
            )
        )
        infoItemsNounScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.nounAdded.toString()
            )
        )
        infoItemsNounScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.nounAddedVideo.toString()
            )
        )
        infoItemsNounScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.nounPointsAddedVideo.toString()
            )
        )

        infoItemsAdjScore.add(InfoScore("Adjectives Score", Scores.adjScore.toString()))
        infoItemsAdjScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.adjQuizCompleted.toString()
            )
        )
        infoItemsAdjScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.adjQuizCompletedCorrectly.toString()
            )
        )
        infoItemsAdjScore.add(InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, Scores.adjAdded.toString()))
        infoItemsAdjScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.adjAddedVideo.toString()
            )
        )
        infoItemsAdjScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.adjPointsAddedVideo.toString()
            )
        )

        infoItemsAdvScore.add(InfoScore("Adverbs Score", Scores.advScore.toString()))
        infoItemsAdvScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.advQuizCompleted.toString()
            )
        )
        infoItemsAdvScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.advQuizCompletedCorrectly.toString()
            )
        )
        infoItemsAdvScore.add(InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, Scores.advAdded.toString()))
        infoItemsAdvScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.advAddedVideo.toString()
            )
        )
        infoItemsAdvScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.advPointsAddedVideo.toString()
            )
        )

        infoItemsIdiomScore.add(InfoScore("Idioms Score", Scores.idiomScore.toString()))
        infoItemsIdiomScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_NAME,
                Scores.idiomQuizCompleted.toString()
            )
        )
        infoItemsIdiomScore.add(
            InfoScore(
                Utils.QUIZ_COMPLETED_CORRECTLY_NAME,
                Scores.idiomQuizCompletedCorrectly.toString()
            )
        )
        infoItemsIdiomScore.add(
            InfoScore(
                Utils.QUIZ_ELEMENT_ADDED_NAME,
                Scores.idiomAdded.toString()
            )
        )
        infoItemsIdiomScore.add(
            InfoScore(
                Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,
                Scores.idiomAddedVideo.toString()
            )
        )
        infoItemsIdiomScore.add(
            InfoScore(
                Utils.QUIZ_POINT_ADDED_VIDEO_NAME,
                Scores.idiomPointsAddedVideo.toString()
            )
        )
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        /**
         * Creates a new instance of HomeInfoScoresPager2Fragment with a specified category.
         *
         * @param category The category of scores to display.
         * @return A new instance of HomeInfoScoresPager2Fragment.
         */
        @JvmStatic
        fun newInstance(category: String?): HomeInfoScoresPager2Fragment {
            val fragment = HomeInfoScoresPager2Fragment()
            val bundle = Bundle()
            bundle.putString(ARG_CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }
}
