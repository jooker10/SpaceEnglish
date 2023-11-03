package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001._Main.Scores;
import anouar.oulhaj.p001._Main.Utils;
import anouar.oulhaj.p001.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {

    private HomeNavFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private String categoryTypeAnim = "";
    private HomeFragClickListener homeListener;
    private OnFragmentNavigationListener navigationListener;
    private boolean isFlipped = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryTypeAnim = bundle.getString(Constants.ARG_UPDATED_CATEGORY_ANIMATION, "");

            }

    }


    public static HomeNavFragment newInstance( String categoryTypeAnim) {
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
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
        binding = HomeNavFragmentBinding.inflate(getLayoutInflater(),container,false);
       // return inflater.inflate(R.layout.home_nav_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding = HomeNavFragmentBinding.bind(view);
        Utils.nameOfFragmentSearchView = "Home";
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        animCardViewScores();

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

        //----linear info expanded
        linearMoreInfoExpandedCollapsed(binding.tvLabelTotalClick , binding.linearTotalInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelVerbsClick , binding.linearVerbsInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelSentenceClick , binding.linearSentenceInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelPhrasalClick , binding.linearPhrasalInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelNounClick , binding.linearNounInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelAdjClick , binding.linearAdjInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelAdvClick , binding.linearAdvInfoExpanded);
        linearMoreInfoExpandedCollapsed(binding.tvLabelIdiomClick , binding.linearIdiomInfoExpanded);


        //------------pick Image for profile-------------------
        binding.btnHomePickProfileImage.setOnClickListener(view1 -> homeListener.onPickImage());

        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty() ) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile);
        } else {
            binding.imgHomeProfile.setImageResource(R.drawable.ic_person_24);
        }

        //_____shared preferences---------------------------------------
        Utils.userName = sharedPreferences.getString(Constants.KEY_USER_NAME, "User 01");
        binding.TvHomeUserName.setText(Utils.userName);
        binding.homeTvWelcomeUser.setText("Welcome " + Utils.userName + " to the Learn English App . Are you ready?");
        // set Animation for ui updated
        setAnimationUpdatedScore();

       updateHomeTV();

        //---------btn_home_get started--------------------------------------
        binding.btnHomeGoToLearn.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.TABLE_NAV_INDEX));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.QUIZ_NAV_INDEX));

       /* SetEnableTvScore(binding.imgHomeLockPhrasal,Scores.totalScore,Constants.permissionPhrasalScore);
        SetEnableTvScore(binding.imgHomeLockNoun ,Scores.totalScore,Constants.permissionNounScore);
        SetEnableTvScore(binding.imgHomeLockAdj,Scores.totalScore,Constants.permissionAdjScore);
        SetEnableTvScore(binding.imgHomeLockAdv,Scores.totalScore,Constants.permissionAdvScore);
        SetEnableTvScore(binding.imgHomeLockIdiom,Scores.totalScore,Constants.permissionIdiomScore);*/

    }

    private void updateHomeTV() {
        // TV Scores
        binding.tvHomeVerbScore.setText(String.valueOf(Scores.verbScore));
        binding.tvHomeSentenceScore.setText(String.valueOf(Scores.sentenceScore));
        binding.tvHomePhrasalScore.setText(String.valueOf(Scores.phrasalScore));
        binding.tvHomeNounScore.setText(String.valueOf(Scores.nounScore));
        binding.tvHomeAdjScore.setText(String.valueOf(Scores.adjScore));
        binding.tvHomeAdvScore.setText(String.valueOf(Scores.advScore));
        binding.tvHomeIdiomScore.setText(String.valueOf(Scores.idiomScore));

        // TV quiz completed
        binding.tvQuizVerbComp.setText(String.valueOf(Scores.verbQuizCompleted));
        binding.tvQuizSentenceComp.setText(String.valueOf(Scores.sentenceQuizCompleted));
        binding.tvQuizPhrasalComp.setText(String.valueOf(Scores.phrasalQuizCompleted));
        binding.tvQuizNounComp.setText(String.valueOf(Scores.nounQuizCompleted));
        binding.tvQuizAdjComp.setText(String.valueOf(Scores.adjQuizCompleted));
        binding.tvQuizAdvComp.setText(String.valueOf(Scores.advQuizCompleted));
        binding.tvQuizIdiomComp.setText(String.valueOf(Scores.idiomQuizCompleted));
        // TV quiz completed correctly
        binding.tvQuizVerbCompCorrectly.setText(String.valueOf(Scores.verbQuizCounterCompletedCorrectly));
        binding.tvSentenceQuizCompletedCorrectly.setText(String.valueOf(Scores.sentenceQuizCounterCompletedCorrectly));
        binding.tvPhrasalCompCorrectly.setText(String.valueOf(Scores.phrasalQuizCounterCompletedCorrectly));
        binding.tvNounCompCorrectly.setText(String.valueOf(Scores.nounQuizCounterCompletedCorrectly));
        binding.tvAdjQuizCompCorrectly.setText(String.valueOf(Scores.adjQuizCounterCompletedCorrectly));
        binding.tvAdvQuizCompCorrectly.setText(String.valueOf(Scores.advQuizCounterCompletedCorrectly));
        binding.tvIdiomQuizCompCorrectly.setText(String.valueOf(Scores.idiomQuizCounterCompletedCorrectly));

        // TV elements added
        binding.tvVerbAdded.setText(String.valueOf(Scores.verbAdded));
        binding.tvSentenceAdded.setText(String.valueOf(Scores.sentenceAdded));
        binding.tvPhrasalAdded.setText(String.valueOf(Scores.phrasalAdded));
        binding.tvNounAdded.setText(String.valueOf(Scores.nounAdded));
        binding.tvAdjAdded.setText(String.valueOf(Scores.adjAdded));
        binding.tvAdvAdded.setText(String.valueOf(Scores.advAdded));
        binding.tvIdiomAdded.setText(String.valueOf(Scores.idiomAdded));

        // TV elements added video
        binding.tvVerbAddedVideo.setText(String.valueOf(Scores.verbAddedVideo));
        binding.tvSentenceAddedVideo.setText(String.valueOf(Scores.sentenceAddedVideo));
        binding.tvPhrasalAddedVideo.setText(String.valueOf(Scores.phrasalAddedVideo));
        binding.tvNounAddedVideo.setText(String.valueOf(Scores.nounAddedVideo));
        binding.tvAdjAddedVideo.setText(String.valueOf(Scores.adjAddedVideo));
        binding.tvAdvAddedVideo.setText(String.valueOf(Scores.advAddedVideo));
        binding.tvIdiomAddedVideo.setText(String.valueOf(Scores.idiomAddedVideo));

        // TV points added video
        binding.tvVerbPointsVideo.setText(String.valueOf(Scores.verbPointsAddedVideo));
        binding.tvSentencePointsAddedVideo.setText(String.valueOf(Scores.sentencePointsAddedVideo));
        binding.tvPhrasalPointsVideos.setText(String.valueOf(Scores.phrasalPointsAddedVideo));
        binding.tvNounPointsAddedVideo.setText(String.valueOf(Scores.nounPointsAddedVideo));
        binding.tvAdjPointsAddedVideo.setText(String.valueOf(Scores.adjPointsAddedVideo));
        binding.tvAdvPointsAddedVideo.setText(String.valueOf(Scores.advPointsAddedVideo));
        binding.tvIdiomPointsVideo.setText(String.valueOf(Scores.idiomPointsAddedVideo));

        // update total variables

        int totalQuizCompleted = Scores.verbQuizCompleted +Scores.sentenceQuizCompleted +Scores.phrasalQuizCompleted +Scores.nounQuizCompleted
                +Scores.adjQuizCompleted +Scores.advQuizCompleted +Scores.idiomQuizCompleted;
        binding.tvTotalQuizCompleted.setText(String.valueOf(totalQuizCompleted));

        int totalElementsAdded = Scores.verbAdded + Scores.sentenceAdded + Scores.phrasalAdded + Scores.nounAdded
                               + Scores.adjAdded + Scores.advAdded + Scores.idiomAdded;
        binding.tvTotalAdded.setText(String.valueOf(totalElementsAdded));

        int totalElementsAddedVideo = Scores.verbAddedVideo + Scores.sentenceAddedVideo + Scores.phrasalAddedVideo + Scores.nounAddedVideo
                + Scores.adjAddedVideo+ Scores.advAddedVideo + Scores.idiomAddedVideo;
        binding.tvTotalAddedVideo.setText(String.valueOf(totalElementsAddedVideo));

        int totalPointsAddedVideo = Scores.verbPointsAddedVideo + Scores.sentencePointsAddedVideo + Scores.phrasalPointsAddedVideo
                               + Scores.nounPointsAddedVideo + Scores.adjPointsAddedVideo + Scores.advPointsAddedVideo + Scores.idiomPointsAddedVideo;
        binding.tvTotalPointsVideos.setText(String.valueOf(totalPointsAddedVideo));

        int totalQuizCompletedCorrectly = Scores.verbQuizCounterCompletedCorrectly +Scores.sentenceQuizCounterCompletedCorrectly +Scores.phrasalQuizCounterCompletedCorrectly +Scores.nounQuizCounterCompletedCorrectly
                +Scores.adjQuizCounterCompletedCorrectly +Scores.advQuizCounterCompletedCorrectly +Scores.idiomQuizCounterCompletedCorrectly;
        binding.tvTotalQuizCompleted.setText(String.valueOf(totalQuizCompletedCorrectly));

        // main total score
        int totalScore = Scores.verbScore + Scores.sentenceScore + Scores.phrasalScore + Scores.nounScore
                + Scores.adjScore + Scores.advScore + Scores.idiomScore + totalPointsAddedVideo;
        binding.tvHomeTotalScore.setText(String.valueOf(totalScore));
        Scores.totalScore = totalScore;



    }

    private void linearMoreInfoExpandedCollapsed(TextView tvToClickOn , LinearLayout linearExpanded){
    tvToClickOn.setOnClickListener(v -> {

        if(linearExpanded.getVisibility() == View.VISIBLE)
            linearExpanded.setVisibility(View.GONE);
        else
            linearExpanded.setVisibility(View.VISIBLE);
    });
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
                binding.tvHomeAdjScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
                break;
            case Constants.ADV_NAME:
                binding.tvHomeAdvScore.setAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_score_tv));
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

    private void SetEnableTvScore(ImageView imgLock, int globalMainScore , int permissionScore) {
        if(globalMainScore < permissionScore){
            imgLock.setVisibility(View.VISIBLE);
        }
        else {
            imgLock.setVisibility(View.GONE);
        }
    }

}