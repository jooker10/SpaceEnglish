package anouar.oulhaj.p001.navfragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;
import anouar.oulhaj.p001.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {

    private HomeNavFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private String categoryTypeAnim = "No category set yet";
    private HomeFragClickListener homeListener;
    private OnFragmentNavigationListener navigationListener;
    private int verbHomeScore = 12, sentenceHomeScore, phrasalHomeScore, nounHomeScore, adjHomeScore, advHomeScore, idiomHomeScore;
    private boolean isFlipped = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryTypeAnim = bundle.getString(Constants.ARG_UPDATED_CATEGORY_ANIMATION, "no category set yet");

            verbHomeScore =  bundle.getInt(Constants.ARG_CURRENT_VERB_SCORE,1);

            sentenceHomeScore =  bundle.getInt(Constants.ARG_CURRENT_SENTENCE_SCORE,0);
            phrasalHomeScore =  bundle.getInt(Constants.ARG_CURRENT_PHRASAL_SCORE,0);
            nounHomeScore =  bundle.getInt(Constants.ARG_CURRENT_NOUN_SCORE,0);
            adjHomeScore =  bundle.getInt(Constants.ARG_CURRENT_ADJ_SCORE,0);
            advHomeScore =  bundle.getInt(Constants.ARG_CURRENT_ADV_SCORE,0);
            idiomHomeScore =  bundle.getInt(Constants.ARG_CURRENT_IDIOM_SCORE,0);
            }

    }


    public static HomeNavFragment newInstance(int verbUserScore,int sentenceUserScore,int phrasalUserScore,int nounUserScore,int adjUserScore,int advUserScore,int idiomUserScore , String categoryTypeAnim) {
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_CURRENT_VERB_SCORE,verbUserScore);
        bundle.putInt(Constants.ARG_CURRENT_SENTENCE_SCORE,sentenceUserScore);
        bundle.putInt(Constants.ARG_CURRENT_PHRASAL_SCORE,phrasalUserScore);
        bundle.putInt(Constants.ARG_CURRENT_NOUN_SCORE,nounUserScore);
        bundle.putInt(Constants.ARG_CURRENT_ADJ_SCORE,adjUserScore);
        bundle.putInt(Constants.ARG_CURRENT_ADV_SCORE,advUserScore);
        bundle.putInt(Constants.ARG_CURRENT_IDIOM_SCORE,idiomUserScore);
        bundle.putString(Constants.ARG_UPDATED_CATEGORY_ANIMATION, categoryTypeAnim);
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
        Utils.nameOfFragmentSearchView = "Home";
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        animCardViewScores();

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }


        //------------pick Image for profile-------------------
        binding.btnHomePickProfileImage.setOnClickListener(view1 -> homeListener.onPickImage());

        if (Constants.uri_pref != null) {
            binding.imgHomeProfile.setImageURI(Constants.uri_pref);
        } else {
            binding.imgHomeProfile.setImageResource(R.drawable.ic_person_24);
        }


        //_____shared preferences---------------------------------------
        String yourUserName = sharedPreferences.getString(Constants.ARG_USER_NAME, "User 01");
        binding.TvHomeUserName.setText(yourUserName);
        // set Animation for ui updated
        setAnimationUpdatedScore();

        binding.tvHomeVerbScore.setText(String.valueOf(verbHomeScore));
        binding.tvHomeSentenceScore.setText(String.valueOf(sentenceHomeScore));
        binding.tvHomePhrasalScore.setText(String.valueOf(phrasalHomeScore));
        binding.tvHomeNounScore.setText(String.valueOf(nounHomeScore));
        binding.tvHomeAdjectiveScore.setText(String.valueOf(adjHomeScore));
        binding.tvHomeAdverbScore.setText(String.valueOf(advHomeScore));
        binding.tvHomeIdiomScore.setText(String.valueOf(idiomHomeScore));

        int totalScore = verbHomeScore + sentenceHomeScore + phrasalHomeScore + nounHomeScore + adjHomeScore + advHomeScore + idiomHomeScore;
        binding.tvHomeTotalScore.setText(String.valueOf(totalScore));

        //---------btn_home_get started--------------------------------------
        binding.btnHomeGoToLearn.setOnClickListener(v -> homeListener.onHomeGetStarted(1));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> homeListener.onHomeGetStarted(3));

    }


    private void animCardViewScores() {
        Thread animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Animation pageFlipAnimation = AnimationUtils.loadAnimation(requireActivity(), R.anim.flip_in);

                // You need to update the UI elements in the UI thread, so use runOnUiThread
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewFlipper.setInAnimation(pageFlipAnimation);
                        binding.viewFlipper.setOutAnimation(pageFlipAnimation);
                        binding.viewFlipper.showNext();
                    }
                });
            }
        });

        animationThread.start();
    }


    //--Functions----
    private void setAnimationUpdatedScore() {
        binding.tvHomeTotalScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
        switch (categoryTypeAnim) {
            case Constants.VERB_NAME:
                binding.tvHomeVerbScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.SENTENCE_NAME:
                binding.tvHomeSentenceScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.PHRASAL_NAME:
                binding.tvHomePhrasalScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.NOUN_NAME:
                binding.tvHomeNounScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));

                break;
            case Constants.ADJ_NAME:
                binding.tvHomeAdjectiveScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.ADV_NAME:
                binding.tvHomeAdverbScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.IDIOM_NAME:
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
        if(context instanceof OnFragmentNavigationListener)
            navigationListener = (OnFragmentNavigationListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeListener = null;
        if(navigationListener != null)
            navigationListener = null;
    }



}