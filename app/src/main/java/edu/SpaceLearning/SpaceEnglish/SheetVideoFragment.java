package edu.SpaceLearning.SpaceEnglish;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;

/**
 * Fragment to display video buttons for different categories.
 */
public class SheetVideoFragment extends Fragment {

    private AdsClickListener adsClickListener; // Listener for handling ad clicks

    public SheetVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Check if the context implements AdsClickListener interface
        if (context instanceof AdsClickListener) {
            adsClickListener = (AdsClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Detach the AdsClickListener to prevent memory leaks
        adsClickListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sheet_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize buttons for each video category
        Button btnVideoVerbs = view.findViewById(R.id.btnVideoVerbs);
        Button btnVideoSentences = view.findViewById(R.id.btnVideoSentences);
        Button btnVideoPhrasals = view.findViewById(R.id.btnVideoPhrasals);
        Button btnVideoNouns = view.findViewById(R.id.btnVideoNouns);
        Button btnVideoAdjs = view.findViewById(R.id.btnVideoAdjs);
        Button btnVideoAdvs = view.findViewById(R.id.btnVideoAdvs);
        Button btnVideoIdoms = view.findViewById(R.id.btnVideoIdoms);

        // Enable or disable buttons based on permission scores
        enableButton(btnVideoPhrasals, Scores.totalScore, Constants.permissionPhrasalScore);
        enableButton(btnVideoNouns, Scores.totalScore, Constants.permissionNounScore);
        enableButton(btnVideoAdjs, Scores.totalScore, Constants.permissionAdjScore);
        enableButton(btnVideoAdvs, Scores.totalScore, Constants.permissionAdvScore);
        enableButton(btnVideoIdoms, Scores.totalScore, Constants.permissionIdiomScore);

        // Set click listeners for each button to show video ads for the respective category
        btnVideoVerbs.setOnClickListener(view13 -> {
            adsClickListener.onShowVideoAds(Constants.VERB_NAME);
        });
        btnVideoSentences.setOnClickListener(view12 -> {
            adsClickListener.onShowVideoAds(Constants.SENTENCE_NAME);
        });
        btnVideoPhrasals.setOnClickListener(view1 -> {
            adsClickListener.onShowVideoAds(Constants.PHRASAL_NAME);
        });
        btnVideoNouns.setOnClickListener(view1 -> {
            adsClickListener.onShowVideoAds(Constants.NOUN_NAME);
        });
        btnVideoAdjs.setOnClickListener(view1 -> {
            adsClickListener.onShowVideoAds(Constants.ADJ_NAME);
        });
        btnVideoAdvs.setOnClickListener(view1 -> {
            adsClickListener.onShowVideoAds(Constants.ADV_NAME);
        });
        btnVideoIdoms.setOnClickListener(view1 -> {
            adsClickListener.onShowVideoAds(Constants.IDIOM_NAME);
        });
    }

    /**
     * Enables or disables a button based on the main score and required permission score.
     *
     * @param button         The button to enable or disable
     * @param globalMainScore The global main score to compare against
     * @param permissionScore The required permission score for enabling the button
     */
    private void enableButton(Button button, int globalMainScore, int permissionScore) {
        button.setEnabled(globalMainScore >= permissionScore);
    }

}
