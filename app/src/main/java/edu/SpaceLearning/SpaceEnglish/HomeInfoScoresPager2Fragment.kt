package edu.SpaceLearning.SpaceEnglish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.HomeInfoScoresRecyclerAdapter;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.InfoScore;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentHomeInfoScoreRecyclerviewBinding;

/**
 * Fragment for displaying various scores and statistics in a RecyclerView.
 */
public class HomeInfoScoresPager2Fragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private String category;
    private FragmentHomeInfoScoreRecyclerviewBinding binding;

    // Lists to hold different categories of InfoScore items
    private final ArrayList<InfoScore> infoItemsTotalScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsVerbScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsSentenceScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsPhrasalScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsNounScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsAdjScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsAdvScore = new ArrayList<>();
    private final ArrayList<InfoScore> infoItemsIdiomScore = new ArrayList<>();

    public HomeInfoScoresPager2Fragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of HomeInfoScoresPager2Fragment with a specified category.
     *
     * @param category The category of scores to display.
     * @return A new instance of HomeInfoScoresPager2Fragment.
     */
    public static HomeInfoScoresPager2Fragment newInstance(String category) {
        HomeInfoScoresPager2Fragment fragment = new HomeInfoScoresPager2Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_info_score_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeInfoScoreRecyclerviewBinding.bind(view);

        // Initialize and populate scores categories
        initTotalScoresCategories();
        fillInfoScoresItemsList();
        setUpRecyclerViewWithAdapter();
    }

    /**
     * Sets up the RecyclerView with an adapter for displaying scores.
     */
    private void setUpRecyclerViewWithAdapter() {
        HomeInfoScoresRecyclerAdapter homeInfoScoresRecyclerAdapter = new HomeInfoScoresRecyclerAdapter(currentInfoScoresItemsList(category));
        binding.infoScoresRecyclerView.setAdapter(homeInfoScoresRecyclerAdapter);
        binding.infoScoresRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    /**
     * Initializes total scores categories based on various score calculations.
     */
    private void initTotalScoresCategories() {
        Scores.totalQuizCompleted = Scores.verbQuizCompleted + Scores.sentenceQuizCompleted + Scores.phrasalQuizCompleted + Scores.nounQuizCompleted
                + Scores.adjQuizCompleted + Scores.advQuizCompleted + Scores.idiomQuizCompleted;
        Scores.totalQuizCompletedCorrectly = Scores.verbQuizCompletedCorrectly + Scores.sentenceQuizCompletedCorrectly + Scores.phrasalQuizCompletedCorrectly
                + Scores.nounQuizCompletedCorrectly + Scores.adjQuizCompletedCorrectly + Scores.advQuizCompletedCorrectly + Scores.idiomQuizCompletedCorrectly;
        Scores.totalElementsAdded = Scores.verbAdded + Scores.sentenceAdded + Scores.phrasalAdded + Scores.nounAdded + Scores.adjAdded + Scores.advAdded + Scores.idiomAdded;
        Scores.totalElementsAddedVideo = Scores.verbAddedVideo + Scores.sentenceAddedVideo + Scores.phrasalAddedVideo + Scores.nounAddedVideo + Scores.adjAddedVideo + Scores.advAddedVideo + Scores.idiomAddedVideo;
        Scores.totalPointsAddedVideo = Scores.verbPointsAddedVideo + Scores.sentencePointsAddedVideo + Scores.phrasalPointsAddedVideo + Scores.nounPointsAddedVideo
                + Scores.adjPointsAddedVideo + Scores.advPointsAddedVideo + Scores.idiomPointsAddedVideo;
        Scores.totalScore = Scores.verbScore + Scores.sentenceScore + Scores.phrasalScore + Scores.nounScore + Scores.adjScore + Scores.advScore + Scores.idiomScore;
    }

    /**
     * Retrieves the list of InfoScore items based on the selected category.
     *
     * @param category The category of scores to retrieve.
     * @return The corresponding list of InfoScore items.
     */
    public ArrayList<InfoScore> currentInfoScoresItemsList(String category) {
        switch (category) {
            case Constants.ALL_NAME:
                return infoItemsTotalScore;
            case Constants.VERB_NAME:
                return infoItemsVerbScore;
            case Constants.SENTENCE_NAME:
                return infoItemsSentenceScore;
            case Constants.PHRASAL_NAME:
                return infoItemsPhrasalScore;
            case Constants.NOUN_NAME:
                return infoItemsNounScore;
            case Constants.ADJ_NAME:
                return infoItemsAdjScore;
            case Constants.ADV_NAME:
                return infoItemsAdvScore;
            case Constants.IDIOM_NAME:
                return infoItemsIdiomScore;
            default:
                return infoItemsTotalScore; // Default to total score list
        }
    }

    /**
     * Fills each category's InfoScore items list with corresponding scores and statistics.
     */
    private void fillInfoScoresItemsList() {
        // Clear existing lists
        infoItemsTotalScore.clear();
        infoItemsVerbScore.clear();
        infoItemsSentenceScore.clear();
        infoItemsPhrasalScore.clear();
        infoItemsNounScore.clear();
        infoItemsAdjScore.clear();
        infoItemsAdvScore.clear();
        infoItemsIdiomScore.clear();

        // Add InfoScore items for each category
        infoItemsTotalScore.add(new InfoScore("Total Score", String.valueOf(Scores.totalScore)));
        infoItemsTotalScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.totalQuizCompleted)));
        infoItemsTotalScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.totalQuizCompletedCorrectly)));
        infoItemsTotalScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.totalElementsAdded)));
        infoItemsTotalScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.totalElementsAddedVideo)));
        infoItemsTotalScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.totalPointsAddedVideo)));

        infoItemsVerbScore.add(new InfoScore("Verbs Score", String.valueOf(Scores.verbScore)));
        infoItemsVerbScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.verbQuizCompleted)));
        infoItemsVerbScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.verbQuizCompletedCorrectly)));
        infoItemsVerbScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.verbAdded)));
        infoItemsVerbScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.verbAddedVideo)));
        infoItemsVerbScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.verbPointsAddedVideo)));

        infoItemsSentenceScore.add(new InfoScore("Sentences Score", String.valueOf(Scores.sentenceScore)));
        infoItemsSentenceScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.sentenceQuizCompleted)));
        infoItemsSentenceScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.sentenceQuizCompletedCorrectly)));
        infoItemsSentenceScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.sentenceAdded)));
        infoItemsSentenceScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.sentenceAddedVideo)));
        infoItemsSentenceScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.sentencePointsAddedVideo)));

        infoItemsPhrasalScore.add(new InfoScore("Phrasal Verbs Score", String.valueOf(Scores.phrasalScore)));
        infoItemsPhrasalScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.phrasalQuizCompleted)));
        infoItemsPhrasalScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.phrasalQuizCompletedCorrectly)));
        infoItemsPhrasalScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.phrasalAdded)));
        infoItemsPhrasalScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.phrasalAddedVideo)));
        infoItemsPhrasalScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.phrasalPointsAddedVideo)));

        infoItemsNounScore.add(new InfoScore("Nouns Score", String.valueOf(Scores.nounScore)));
        infoItemsNounScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.nounQuizCompleted)));
        infoItemsNounScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.nounQuizCompletedCorrectly)));
        infoItemsNounScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.nounAdded)));
        infoItemsNounScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.nounAddedVideo)));
        infoItemsNounScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.nounPointsAddedVideo)));

        infoItemsAdjScore.add(new InfoScore("Adjectives Score", String.valueOf(Scores.adjScore)));
        infoItemsAdjScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.adjQuizCompleted)));
        infoItemsAdjScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.adjQuizCompletedCorrectly)));
        infoItemsAdjScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.adjAdded)));
        infoItemsAdjScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.adjAddedVideo)));
        infoItemsAdjScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.adjPointsAddedVideo)));

        infoItemsAdvScore.add(new InfoScore("Adverbs Score", String.valueOf(Scores.advScore)));
        infoItemsAdvScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.advQuizCompleted)));
        infoItemsAdvScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.advQuizCompletedCorrectly)));
        infoItemsAdvScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.advAdded)));
        infoItemsAdvScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.advAddedVideo)));
        infoItemsAdvScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.advPointsAddedVideo)));

        infoItemsIdiomScore.add(new InfoScore("Idioms Score", String.valueOf(Scores.idiomScore)));
        infoItemsIdiomScore.add(new InfoScore(Utils.QUIZ_COMPLETED_NAME, String.valueOf(Scores.idiomQuizCompleted)));
        infoItemsIdiomScore.add(new InfoScore(Utils.QUIZ_COMPLETED_CORRECTLY_NAME, String.valueOf(Scores.idiomQuizCompletedCorrectly)));
        infoItemsIdiomScore.add(new InfoScore(Utils.QUIZ_ELEMENT_ADDED_NAME, String.valueOf(Scores.idiomAdded)));
        infoItemsIdiomScore.add(new InfoScore(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME, String.valueOf(Scores.idiomAddedVideo)));
        infoItemsIdiomScore.add(new InfoScore(Utils.QUIZ_POINT_ADDED_VIDEO_NAME, String.valueOf(Scores.idiomPointsAddedVideo)));
    }
}
