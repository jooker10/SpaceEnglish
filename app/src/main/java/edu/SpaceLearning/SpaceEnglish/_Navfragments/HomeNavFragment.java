package edu.SpaceLearning.SpaceEnglish._Navfragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;


import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.InfoScorePager2Adapter;
import edu.SpaceLearning.SpaceEnglish.HomeInfoScoresPager2Fragment;
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionMainActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {

    private HomeNavFragmentBinding binding;
    private InteractionMainActivityFragmentsListener interactionListener;
    private final ArrayList<Fragment> fragmentsForPager2InfoScores = new ArrayList<>();

    public HomeNavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = HomeNavFragmentBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHomeSharedPrefs(); // set all prefs scores in HomeFragment.
        initUIHome();    // buttons - imageProfile - Title.
        setUpTabsWithPager2InfoScores(); // Tabs scores with pager2 in HomeFragment.

    }

    private void initUIHome() {
        setImageHomeProfile();
        binding.btnHomeGoToLearn.setOnClickListener(v -> interactionListener.onHomeGetStarted(Constants.TABLE_NAV_INDEX));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> interactionListener.onHomeGetStarted(Constants.QUIZ_NAV_INDEX));
        binding.tvHomeUserName.setText("Hi, " + Utils.userName);
    }

    private void setHomeSharedPrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Utils.userName = sharedPreferences.getString(Constants.KEY_PREF_USER_NAME, "User 01");

    }

    private void setImageHomeProfile() {

        binding.imgHomeProfile.setOnClickListener(v -> {
            interactionListener.onPickImage();
        } );
        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty() ) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile);
        }

    }



    private void setUpTabsWithPager2InfoScores(){
        for (String categoryName : Utils.tableListNames) {
            fragmentsForPager2InfoScores.add(HomeInfoScoresPager2Fragment.newInstance(categoryName));
        }
        InfoScorePager2Adapter infoScorePager2Adapter = new InfoScorePager2Adapter(requireActivity(), fragmentsForPager2InfoScores);
        binding.infoScoresPager2.setAdapter(infoScorePager2Adapter);
        new TabLayoutMediator(binding.infoTabLayout, binding.infoScoresPager2, (tab, position) -> {

          tab.setText(Utils.tableListNames.get(position));

        }).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InteractionMainActivityFragmentsListener)
            interactionListener = (InteractionMainActivityFragmentsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(interactionListener != null) {
            interactionListener = null;}

    }

}