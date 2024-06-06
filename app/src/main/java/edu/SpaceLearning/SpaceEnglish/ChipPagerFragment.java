package edu.SpaceLearning.SpaceEnglish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.ChipPagerAdapter;
import edu.SpaceLearning.SpaceEnglish.Adapters.ChipRecyclerAdapter;
import edu.SpaceLearning.SpaceEnglish.Adapters.TablePagerAdapter;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentChipPagerBinding;
import edu.SpaceLearning.SpaceEnglish.databinding.TableContainerFragmentBinding;


public class ChipPagerFragment extends Fragment {


    private static final String ARG_CATEGORY = "category";
    private String category;
    FragmentChipPagerBinding binding;
    ArrayList<ChipItem> chipItemsTotalScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsVerbScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsSentenceScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsPhrasalScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsNounScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsAdjScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsAdvScore = new ArrayList<>();
    ArrayList<ChipItem> chipItemsIdiomScore = new ArrayList<>();


    public ChipPagerFragment() {
        // Required empty public constructor
    }

    public static ChipPagerFragment newInstance(String category) {
        ChipPagerFragment fragment = new ChipPagerFragment();
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
        return inflater.inflate(R.layout.fragment_chip_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentChipPagerBinding.bind(view);


        Scores.totalQuizCompleted = Scores.verbQuizCompleted + Scores.sentenceQuizCompleted + Scores.phrasalQuizCompleted + Scores.nounQuizCompleted
                +Scores.adjQuizCompleted + Scores.advQuizCompleted + Scores.idiomQuizCompleted;
        Scores.totalQuizCompletedCorrectly = Scores.verbQuizCompletedCorrectly + Scores.sentenceQuizCounterCompletedCorrectly + Scores.phrasalQuizCounterCompletedCorrectly
                +Scores.nounQuizCounterCompletedCorrectly + Scores.adjQuizCounterCompletedCorrectly+Scores.advQuizCounterCompletedCorrectly + Scores.idiomQuizCounterCompletedCorrectly;
        Scores.totalElementsAdded = Scores.verbAdded + Scores.sentenceAdded + Scores.phrasalAdded + Scores.nounAdded + Scores.adjAdded+Scores.advAdded+Scores.idiomAdded;
        Scores.totalElementsAddedVideo = Scores.verbAddedVideo + Scores.sentenceAddedVideo + Scores.phrasalAddedVideo + Scores.nounAddedVideo + Scores.adjAddedVideo+Scores.advAddedVideo+Scores.idiomAddedVideo;
        Scores.totalPointsAddedVideo = Scores.verbPointsAddedVideo + Scores.sentencePointsAddedVideo + Scores.phrasalPointsAddedVideo + Scores.nounPointsAddedVideo
                + Scores.adjPointsAddedVideo+ Scores.advPointsAddedVideo + Scores.idiomPointsAddedVideo;
        Scores.totalScore = Scores.verbScore + Scores.sentenceScore + Scores.phrasalScore + Scores.nounScore + Scores.adjScore + Scores.advScore + Scores.idiomScore;
        fillChipItemsList();

        ChipRecyclerAdapter chipRecyclerAdapter = new ChipRecyclerAdapter(currentChipItemsList(category));
        binding.chipRecycler.setAdapter(chipRecyclerAdapter);
        binding.chipRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));

    }

   public ArrayList<ChipItem> currentChipItemsList (String category) {
       switch (category) {
           case Constants.ALL_NAME:
               return chipItemsTotalScore;
           case Constants.VERB_NAME:
               return chipItemsVerbScore;
           case Constants.SENTENCE_NAME:
               return chipItemsSentenceScore;
           case Constants.PHRASAL_NAME:
               return chipItemsPhrasalScore;
           case Constants.NOUN_NAME:
               return chipItemsNounScore;
           case Constants.ADJ_NAME:
               return chipItemsAdjScore;
           case Constants.ADV_NAME:
               return chipItemsAdvScore;
           case Constants.IDIOM_NAME:
               return chipItemsIdiomScore;
       }
       return chipItemsTotalScore;
   }
    private void fillChipItemsList(){
        chipItemsTotalScore.clear();
        chipItemsVerbScore.clear();
        chipItemsSentenceScore.clear();
        chipItemsPhrasalScore.clear();
        chipItemsNounScore.clear();
        chipItemsAdjScore.clear();
        chipItemsAdvScore.clear();
        chipItemsIdiomScore.clear();

        chipItemsTotalScore.add(new ChipItem("Total Score",String.valueOf(Scores.totalScore)));
        chipItemsTotalScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.totalQuizCompleted)));
        chipItemsTotalScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.totalQuizCompletedCorrectly)));
        chipItemsTotalScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.totalElementsAdded)));
        chipItemsTotalScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.totalElementsAddedVideo)));
        chipItemsTotalScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.totalPointsAddedVideo)));

        chipItemsVerbScore.add(new ChipItem("Verbs Score",String.valueOf(Scores.verbScore)));
        chipItemsVerbScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.verbQuizCompleted)));
        chipItemsVerbScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.verbQuizCompletedCorrectly)));
        chipItemsVerbScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.verbAdded)));
        chipItemsVerbScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.verbAddedVideo)));
        chipItemsVerbScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.verbPointsAddedVideo)));

        chipItemsSentenceScore.add(new ChipItem("Sentences Score",String.valueOf(Scores.sentenceScore)));
        chipItemsSentenceScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.sentenceQuizCompleted)));
        chipItemsSentenceScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.sentenceQuizCounterCompletedCorrectly)));
        chipItemsSentenceScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.sentenceAdded)));
        chipItemsSentenceScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.sentenceAddedVideo)));
        chipItemsSentenceScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.sentencePointsAddedVideo)));

        chipItemsPhrasalScore.add(new ChipItem("Phrasal Verbs Score",String.valueOf(Scores.phrasalScore)));
        chipItemsPhrasalScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.phrasalQuizCompleted)));
        chipItemsPhrasalScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.phrasalQuizCounterCompletedCorrectly)));
        chipItemsPhrasalScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.phrasalAdded)));
        chipItemsPhrasalScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.phrasalAddedVideo)));
        chipItemsPhrasalScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.phrasalPointsAddedVideo)));

        chipItemsNounScore.add(new ChipItem("Nouns Score",String.valueOf(Scores.nounScore)));
        chipItemsNounScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.nounQuizCompleted)));
        chipItemsNounScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.nounQuizCounterCompletedCorrectly)));
        chipItemsNounScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.nounAdded)));
        chipItemsNounScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.nounAddedVideo)));
        chipItemsNounScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.nounPointsAddedVideo)));

        chipItemsAdjScore.add(new ChipItem("Adjectives Score",String.valueOf(Scores.adjScore)));
        chipItemsAdjScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.adjQuizCompleted)));
        chipItemsAdjScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.adjQuizCounterCompletedCorrectly)));
        chipItemsAdjScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.adjAdded)));
        chipItemsAdjScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.adjAddedVideo)));
        chipItemsAdjScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.adjPointsAddedVideo)));

        chipItemsAdvScore.add(new ChipItem("Adverbs Score",String.valueOf(Scores.advScore)));
        chipItemsAdvScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.advQuizCompleted)));
        chipItemsAdvScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.advQuizCounterCompletedCorrectly)));
        chipItemsAdvScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.advAdded)));
        chipItemsAdvScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.advAddedVideo)));
        chipItemsAdvScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.advPointsAddedVideo)));

        chipItemsIdiomScore.add(new ChipItem("Idioms Score",String.valueOf(Scores.idiomScore)));
        chipItemsIdiomScore.add(new ChipItem(Utils.QUIZ_COMPLETED_NAME,String.valueOf(Scores.idiomQuizCompleted)));
        chipItemsIdiomScore.add(new ChipItem(Utils.QUIZ_COMPLETED_CORRECTLY_NAME,String.valueOf(Scores.idiomQuizCounterCompletedCorrectly)));
        chipItemsIdiomScore.add(new ChipItem(Utils.QUIZ_ELEMENT_ADDED_NAME,String.valueOf(Scores.idiomAdded)));
        chipItemsIdiomScore.add(new ChipItem(Utils.QUIZ_QUIZ_ELEMENT_ADDED_VIDEO_NAME,String.valueOf(Scores.idiomAddedVideo)));
        chipItemsIdiomScore.add(new ChipItem(Utils.QUIZ_POINT_ADDED_VIDEO_NAME,String.valueOf(Scores.idiomPointsAddedVideo)));
    }
}