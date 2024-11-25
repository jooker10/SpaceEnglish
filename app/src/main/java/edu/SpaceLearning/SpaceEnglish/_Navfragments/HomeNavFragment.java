/*
 * File: HomeNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: This file contains the implementation of the HomeNavFragment class,
 *          which represents the home screen fragment of the SpaceEnglish app.
 *          It includes UI setup, interaction handling with shared preferences,
 *          image profile management, and integration with ViewPager2 for displaying
 *          information scores categorized by tabs.
 */
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
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.HomeNavFragmentBinding;

public class HomeNavFragment extends Fragment {

    private HomeNavFragmentBinding binding;
    private InteractionActivityFragmentsListener interactionListener;
    private final ArrayList<Fragment> fragmentsForPager2InfoScores = new ArrayList<>();

    public HomeNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = HomeNavFragmentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components and set up interactions
        setHomeSharedPrefs(); // Set up shared preferences related to HomeNavFragment
        initUIHome();         // Initialize UI elements like buttons, imageProfile, and title
        setUpTabsWithPager2InfoScores(); // Set up tabs with ViewPager2 for info scores
    }

    private void initUIHome() {
        // Set up image profile click listener and display user name
        setImageHomeProfile();
        binding.btnHomeGoToLearn.setOnClickListener(v -> interactionListener.onHomeGetStarted(Constants.TABLE_NAV_INDEX));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> interactionListener.onHomeGetStarted(Constants.QUIZ_NAV_INDEX));
        binding.tvHomeUserName.setText("Hi, " + Utils.userName);
    }

    private void setHomeSharedPrefs() {
        // Retrieve user name from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Utils.userName = sharedPreferences.getString(Constants.KEY_PREF_USER_NAME, "User 01");
    }

    private void setImageHomeProfile() {
        // Set click listener for image profile and display image if available
        binding.imgHomeProfile.setOnClickListener(v -> interactionListener.onPickImageProfile());
        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty()) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile);
        }
    }

    private void setUpTabsWithPager2InfoScores() {
        // Initialize fragments for ViewPager2 and set up adapter with TabLayoutMediator
        for (String categoryName : Utils.tableListNames) {
            fragmentsForPager2InfoScores.add(HomeInfoScoresPager2Fragment.newInstance(categoryName));
        }
        InfoScorePager2Adapter infoScorePager2Adapter = new InfoScorePager2Adapter(requireActivity(), fragmentsForPager2InfoScores);
        binding.infoScoresPager2.setAdapter(infoScorePager2Adapter);
        new TabLayoutMediator(binding.infoTabLayout, binding.infoScoresPager2, (tab, position) -> {
            // Set tab text based on category names
            tab.setText(Utils.tableListNames.get(position));
        }).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context instanceof InteractionActivityFragmentsListener) {
            interactionListener = (InteractionActivityFragmentsListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Release the interaction listener to avoid memory leaks
        interactionListener = null;
    }

}
