package edu.SpaceLearning.SpaceEnglish.QuizFrags;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish.onVideoBuyClickListener;


public class SheetVideoFragment extends Fragment {

    private onVideoBuyClickListener videoBuyClickListener;

    public SheetVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onVideoBuyClickListener)
            videoBuyClickListener = (onVideoBuyClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(videoBuyClickListener != null)
            videoBuyClickListener = null;
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

        Button btnVideoVerbs = view.findViewById(R.id.btnVideoVerbs);
        Button btnVideoSentences = view.findViewById(R.id.btnVideoSentences);
        Button btnVideoPhrasals = view.findViewById(R.id.btnVideoPhrasals);
        Button btnVideoNouns = view.findViewById(R.id.btnVideoNouns);
        Button btnVideoAdjs = view.findViewById(R.id.btnVideoAdjs);
        Button btnVideoAdvs = view.findViewById(R.id.btnVideoAdvs);
        Button btnVideoIdoms = view.findViewById(R.id.btnVideoIdoms);

        // permission buttons the enabled true
        enableButton(btnVideoPhrasals, Scores.totalScore,Constants.permissionPhrasalScore);
        enableButton(btnVideoNouns, Scores.totalScore,Constants.permissionNounScore);
        enableButton(btnVideoAdjs, Scores.totalScore,Constants.permissionAdjScore);
        enableButton(btnVideoAdvs, Scores.totalScore,Constants.permissionAdvScore);
        enableButton(btnVideoIdoms, Scores.totalScore,Constants.permissionIdiomScore);

        btnVideoVerbs.setOnClickListener(view13 -> {
            videoBuyClickListener.onShowVideoAds(Constants.VERB_NAME);
        });
        btnVideoSentences.setOnClickListener(view12 -> {
            videoBuyClickListener.onShowVideoAds(Constants.SENTENCE_NAME);
        });
        btnVideoPhrasals.setOnClickListener(view1 -> {
            videoBuyClickListener.onShowVideoAds(Constants.PHRASAL_NAME);

        });
        btnVideoNouns.setOnClickListener(view1 -> {
            videoBuyClickListener.onShowVideoAds(Constants.NOUN_NAME);

        });
        btnVideoAdjs.setOnClickListener(view1 -> {
            videoBuyClickListener.onShowVideoAds(Constants.ADJ_NAME);

        });
        btnVideoAdvs.setOnClickListener(view1 -> {
            videoBuyClickListener.onShowVideoAds(Constants.ADV_NAME);

        });
        btnVideoIdoms.setOnClickListener(view1 -> {
            videoBuyClickListener.onShowVideoAds(Constants.IDIOM_NAME);

        });
    }

    private void enableButton(Button button , int globalMainScore , int permissionScore) {
        button.setEnabled(globalMainScore >= permissionScore);
    }

}