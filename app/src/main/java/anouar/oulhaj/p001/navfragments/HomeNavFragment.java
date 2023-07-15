package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import anouar.oulhaj.p001.MainActivity;
import anouar.oulhaj.p001.R;


public class HomeNavFragment extends Fragment {

    // Listener Home
    private HomeFragClickListener homeListener;
    SharedPreferences sp;
    SharedPreferences.Editor edit;


    private static final String ARG_CURRENT_VERB_SCORE = "arg_current_verb_score";
    private TextView tv_VerbScore;
    private static final String ARG_CURRENT_SENTENCE_SCORE = "arg_current_sentence_score";
    private TextView tv_SentenceScore;
    private static final String ARG_CURRENT_PHRASAL_SCORE = "arg_current_phrasal_score";
    private TextView tv_PhrasalScore;

    private TextView tv_totalScore;
    private TextView tv_user_name;

    ImageView img_started;
    FloatingActionButton fab_share;
    ImageView img_fc,img_whats,img_inst,img_twitter;
    private boolean isBtnshareActive = false;


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
            MainActivity.pref_verb_score = Math.max(MainActivity.pref_verb_score,bundle.getInt(ARG_CURRENT_VERB_SCORE));
            MainActivity.pref_sentence_score = Math.max(MainActivity.pref_sentence_score,bundle.getInt(ARG_CURRENT_SENTENCE_SCORE));
            MainActivity.pref_phrasal_score = Math.max(MainActivity.pref_phrasal_score,bundle.getInt(ARG_CURRENT_PHRASAL_SCORE));

        }

    }

    public static HomeNavFragment newInstance(int verbScore,int sentenceScore,int phrasalScore){
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
         bundle.putInt(ARG_CURRENT_VERB_SCORE, verbScore);
         bundle.putInt(ARG_CURRENT_SENTENCE_SCORE, sentenceScore);
         bundle.putInt(ARG_CURRENT_PHRASAL_SCORE, phrasalScore);

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
        tv_user_name = view.findViewById(R.id.home_tv_username);
        img_started = view.findViewById(R.id.img_getStarted);
        fab_share = view.findViewById(R.id.home_fab_share);
        img_fc = view.findViewById(R.id.img_facebook);
        img_whats = view.findViewById(R.id.img_whatsapp);
        img_inst = view.findViewById(R.id.img_instagram);
        img_twitter = view.findViewById(R.id.img_twitter);
       //________________ads----------------------------------------



        //_____shared preferences---------------------------------------
        sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        edit = sp.edit();
        String your_user_name = sp.getString("user_name","User 01");
        tv_user_name.setText(your_user_name);


        tv_VerbScore.setText(MainActivity.pref_verb_score+"");
        tv_SentenceScore.setText(""+MainActivity.pref_sentence_score);
        tv_PhrasalScore.setText(""+MainActivity.pref_phrasal_score);

        tv_totalScore.setText(""+ (MainActivity.pref_verb_score+MainActivity.pref_sentence_score+MainActivity.pref_phrasal_score));

        img_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted();
            }
        });

        //------------btns share social media---------------
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isBtnshareActive){
                    img_fc.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.enter_shared_animation_img));
                    img_whats.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.enter_shared_animation_img));
                    img_inst.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.enter_shared_animation_img));
                    img_twitter.setAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.enter_shared_animation_img));
                    img_fc.setVisibility(View.VISIBLE);
                    img_whats.setVisibility(View.VISIBLE);
                    img_inst.setVisibility(View.VISIBLE);
                    img_twitter.setVisibility(View.VISIBLE);
                }
                else {
                    img_fc.setVisibility(View.GONE);
                    img_whats.setVisibility(View.GONE);
                    img_inst.setVisibility(View.GONE);
                    img_twitter.setVisibility(View.GONE);
                }
                isBtnshareActive = !isBtnshareActive;

            }
        });

    }

    //------------------HomeListener--------------------------------------------
    public interface HomeFragClickListener {
        void onHomeGetStarted();
    }


}