package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {

    private HomeNavFragmentBinding binding;
    // Listener Home
    private HomeFragClickListener homeListener;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    public static final String ARG_UPDATED_CATEGORY_ANIMATION = "updated_category_animation";
    private String categoryTypeAnim = "No category set yet";
    private static final String ARG_CURRENT_VERB_SCORE = "arg_current_verb_score";
    private static final String ARG_CURRENT_SENTENCE_SCORE = "arg_current_sentence_score";
    private static final String ARG_CURRENT_PHRASAL_SCORE = "arg_current_phrasal_score";
    private static final String ARG_CURRENT_NOUN_SCORE = "arg_current_noun_score";
    private static final String ARG_CURRENT_ADJ_SCORE = "arg_current_adj_score";
    private static final String ARG_CURRENT_ADV_SCORE = "arg_current_adv_score";
    private static final String ARG_CURRENT_IDIOM_SCORE = "arg_current_idiom_score";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryTypeAnim = bundle.getString(ARG_UPDATED_CATEGORY_ANIMATION, "no category set yet");
            Constants.pref_verb_score = Math.max(Constants.pref_verb_score, bundle.getInt(ARG_CURRENT_VERB_SCORE));
            Constants.pref_sentence_score = Math.max(Constants.pref_sentence_score, bundle.getInt(ARG_CURRENT_SENTENCE_SCORE));
            Constants.pref_phrasal_score = Math.max(Constants.pref_phrasal_score, bundle.getInt(ARG_CURRENT_PHRASAL_SCORE));
            Constants.pref_noun_score = Math.max(Constants.pref_noun_score, bundle.getInt(ARG_CURRENT_NOUN_SCORE));
            Constants.pref_adj_score = Math.max(Constants.pref_adj_score, bundle.getInt(ARG_CURRENT_ADJ_SCORE));
            Constants.pref_adv_score = Math.max(Constants.pref_adv_score, bundle.getInt(ARG_CURRENT_ADV_SCORE));
            Constants.pref_idiom_score = Math.max(Constants.pref_idiom_score, bundle.getInt(ARG_CURRENT_IDIOM_SCORE));

        }

    }

    public static HomeNavFragment newInstance(int verbScore, int sentenceScore, int phrasalScore, int nounScore
            , int adjScore, int advScore, int idiomScore, String categoryTypeAnim) {
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_CURRENT_VERB_SCORE, verbScore);
        bundle.putInt(ARG_CURRENT_SENTENCE_SCORE, sentenceScore);
        bundle.putInt(ARG_CURRENT_PHRASAL_SCORE, phrasalScore);
        bundle.putInt(ARG_CURRENT_NOUN_SCORE, nounScore);
        bundle.putInt(ARG_CURRENT_ADJ_SCORE, adjScore);
        bundle.putInt(ARG_CURRENT_ADV_SCORE, advScore);
        bundle.putInt(ARG_CURRENT_IDIOM_SCORE, idiomScore);
        bundle.putString(ARG_UPDATED_CATEGORY_ANIMATION, categoryTypeAnim);

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

       // binding = HomeNavFragmentBinding.inflate(getParentFragment().getLayoutInflater(),container,false);
        return inflater.inflate(R.layout.home_nav_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = HomeNavFragmentBinding.bind(view);

        //------------pick Image for profile-------------------
        binding.btnHomePickProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                homeListener.onPickImage();
            }
        });

        if (Constants.uri_pref != null) {
            binding.imgHomeProfile.setImageURI(Constants.uri_pref);
        } else {
            binding.imgHomeProfile.setImageResource(R.drawable.ic_person_24);
        }


        //_____shared preferences---------------------------------------
        sp = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        edit = sp.edit();
        String your_user_name = sp.getString("user_name", "User 01");
        binding.TvHomeUserName.setText(your_user_name);
        // set Animation for ui updated
        setAnimationUpdatedScore();

        binding.tvHomeVerbScore.setText(String.valueOf(Constants.pref_verb_score));
        binding.tvHomeSentenceScore.setText(String.valueOf(Constants.pref_sentence_score));
        binding.tvHomePhrasalScore.setText(String.valueOf(Constants.pref_phrasal_score));
        binding.tvHomeNounScore.setText(String.valueOf(Constants.pref_noun_score));
        binding.tvHomeAdjectiveScore.setText(String.valueOf(Constants.pref_adj_score));
        binding.tvHomeAdverbScore.setText(String.valueOf(Constants.pref_adv_score));
        binding.tvHomeIdiomScore.setText(String.valueOf(Constants.pref_idiom_score));

        int totalScore = Constants.pref_verb_score + Constants.pref_sentence_score + Constants.pref_phrasal_score
                + Constants.pref_noun_score + Constants.pref_adj_score + Constants.pref_adv_score + Constants.pref_idiom_score;
        binding.tvHomeTotalScore.setText(String.valueOf(totalScore));

        //---------btn_home_getstarted--------------------------------------
        binding.btnHomeGoToLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted(1);
            }
        });
        binding.btnHomeGoToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeListener.onHomeGetStarted(3);
            }
        });


    }

    //--Functions----
    private void setAnimationUpdatedScore() {
        binding.tvHomeTotalScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
        switch (categoryTypeAnim) {
            case "VERB":
                binding.tvHomeVerbScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case "SENTENCE":
                binding.tvHomeSentenceScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case "PHRASAL":
                binding.tvHomePhrasalScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case "NOUN":
                binding.tvHomeNounScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));

                break;
            case "ADJECTIVE":
                binding.tvHomeAdjectiveScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case "ADVERB":
                binding.tvHomeAdverbScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case "IDIOM":
                binding.tvHomeIdiomScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
        }
    }

    //------------------HomeListener--------------------------------------------
    public interface HomeFragClickListener {
        void onHomeGetStarted(int index);

        void onPickImage();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragClickListener)
            homeListener = (HomeFragClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeListener = null;
    }

}