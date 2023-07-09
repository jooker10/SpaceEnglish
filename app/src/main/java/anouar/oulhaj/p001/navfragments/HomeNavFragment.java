package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import anouar.oulhaj.p001.R;


public class HomeNavFragment extends Fragment {

    // Listener Home
    private HomeFragClickListener homeListener;

    private static final String ARG_CURRENT_VERB_SCORE = "arg_current_verb_score";
    private static int currentVerbScore;
    private TextView tv_VerbScore;
    private static final String ARG_CURRENT_SENTENCE_SCORE = "arg_current_sentence_score";
    private static int currentSentenceScore;
    private TextView tv_SentenceScore;
    private static final String ARG_CURRENT_PHRASAL_SCORE = "arg_current_phrasal_score";
    private static int currentPhrasalScore;
    private TextView tv_PhrasalScore;

    private static int totalScore;
    private TextView tv_totalScore;

    ImageView img_started;
    AutoCompleteTextView autoTxt;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof HomeFragClickListener) homeListener = (HomeFragClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            currentVerbScore = bundle.getInt(ARG_CURRENT_VERB_SCORE);
            currentSentenceScore = bundle.getInt(ARG_CURRENT_SENTENCE_SCORE);
            currentPhrasalScore = bundle.getInt(ARG_CURRENT_PHRASAL_SCORE);
        }
    }

    public static HomeNavFragment newInstance(int verbScore,int sentenceScore,int phrasalScore){
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
        if(verbScore > currentVerbScore) bundle.putInt(ARG_CURRENT_VERB_SCORE, verbScore);
        else bundle.putInt(ARG_CURRENT_VERB_SCORE, currentVerbScore);
        if(sentenceScore > currentSentenceScore) bundle.putInt(ARG_CURRENT_SENTENCE_SCORE, sentenceScore);
        else bundle.putInt(ARG_CURRENT_SENTENCE_SCORE, currentSentenceScore);
        if(phrasalScore > currentPhrasalScore) bundle.putInt(ARG_CURRENT_PHRASAL_SCORE, phrasalScore);
        else bundle.putInt(ARG_CURRENT_PHRASAL_SCORE, currentPhrasalScore);

        homeNavFragment.setArguments(bundle);
        return homeNavFragment;

    }

    public HomeNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_totalScore = view.findViewById(R.id.home_tv_totalScore);
        tv_VerbScore = view.findViewById(R.id.home_tv_verbScore);
        tv_SentenceScore = view.findViewById(R.id.home_tv_snetenceScore);
        tv_PhrasalScore = view.findViewById(R.id.home_tv_phrasalScore);
        img_started = view.findViewById(R.id.img_getStarted);
        autoTxt = view.findViewById(R.id.autoTxt_quiz);

        tv_VerbScore.setText("Verbs Score : "+currentVerbScore);
        tv_SentenceScore.setText("Sentences Score : "+currentSentenceScore);
        tv_PhrasalScore.setText("Phrasal Score : "+currentPhrasalScore);

        tv_totalScore.setText("Total : "+ (currentVerbScore+currentSentenceScore+currentPhrasalScore));

        img_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted();
            }
        });

        String[]languages = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> autolanguagesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,languages);

        autoTxt.setAdapter(autolanguagesAdapter);

    }

    //------------------HomeListener--------------------------------------------
    public interface HomeFragClickListener {
        void onHomeGetStarted();
    }
}