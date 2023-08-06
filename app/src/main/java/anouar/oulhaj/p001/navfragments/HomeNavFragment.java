package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import anouar.oulhaj.p001.MainActivity;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


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


    ExtendedFloatingActionButton fab_share;
   // FloatingActionButton fab_pickImage;
    TextView fab_pickImage;
    ImageView img_profile;
    ImageView img_fc,img_whats,img_inst,img_twitter;
    private boolean isBtnshareActive = false;
    private Button btn_toLearn, btn_toQuiz;


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

    public static HomeNavFragment newInstance(int verbScore, int sentenceScore, int phrasalScore){
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
        fab_share = view.findViewById(R.id.home_fab_share);
        img_fc = view.findViewById(R.id.img_facebook);
        img_whats = view.findViewById(R.id.img_whatsapp);
        img_inst = view.findViewById(R.id.img_instagram);
        img_twitter = view.findViewById(R.id.img_twitter);
        img_profile = view.findViewById(R.id.img_profile);
        fab_pickImage = view.findViewById(R.id.fabPickImage);
        btn_toLearn = view.findViewById(R.id.home_btn_toLearn);
        btn_toQuiz = view.findViewById(R.id.home_btn_toQuiz);
       //________________ads----------------------------------------

        //------------pick Image for profile-------------------
        fab_pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeListener.onPickImage();
            }
        });

        if(MainActivity.uri_pref != null){
            img_profile.setImageURI(MainActivity.uri_pref);
        }
        else {
            img_profile.setImageResource(R.drawable.ic_person_24);
        }



        //_____shared preferences---------------------------------------
        sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        edit = sp.edit();
        String your_user_name = sp.getString("user_name","User 01");
        tv_user_name.setText(your_user_name);


        tv_VerbScore.setText(MainActivity.pref_verb_score + "/" + Utils.maxVerbsCount);
        tv_SentenceScore.setText(MainActivity.pref_sentence_score+ "/" + Utils.maxSentencesCount);
        tv_PhrasalScore.setText(MainActivity.pref_phrasal_score + "/" + Utils.maxPhrasalCount);

        int totalScore = MainActivity.pref_verb_score + MainActivity.pref_sentence_score + MainActivity.pref_phrasal_score;
        tv_totalScore.setText(totalScore + "/" + (Utils.maxVerbsCount+Utils.maxSentencesCount+Utils.maxPhrasalCount));

        //---------btn_home_getstarted--------------------------------------
        btn_toLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted(1);
            }
        });
        btn_toQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted(3);
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
        void onHomeGetStarted(int index);
        void onPickImage();
    }

}