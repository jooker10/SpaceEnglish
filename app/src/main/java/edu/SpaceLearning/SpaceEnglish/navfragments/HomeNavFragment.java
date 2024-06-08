package edu.SpaceLearning.SpaceEnglish.navfragments;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.HomeCategoriesPagerAdapter;
import edu.SpaceLearning.SpaceEnglish.ChipPagerFragment;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {

    public static final int REQUEST_IMAGE_PICK_PERMISSION = 300;
    private HomeNavFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private HomeFragClickListener homeListener;
    private ArrayList<Fragment> fragments;

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

        setHomeSharedPrefs();
        setButtonHomeGetStarted();
        setImageHomeProfile();
        setUpTabsWithPagers();

    }

    private void setButtonHomeGetStarted() {
        binding.btnHomeGoToLearn.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.TABLE_NAV_INDEX));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.QUIZ_NAV_INDEX));
    }

    private void setHomeSharedPrefs() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        Utils.userName = sharedPreferences.getString(Constants.KEY_USER_NAME, "User 01");
        binding.tvHomeUserName.setText("Hi, " + Utils.userName);
    }

    private void setImageHomeProfile() {

        binding.imgHomeProfile.setOnClickListener(v -> {
            homeListener.onPickImage();
        } );
        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty() ) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile);
        }

    }

    private void fillHomeCategoriesPager(){
        for (String tableName : Utils.tableListNames) {
            fragments.add(ChipPagerFragment.newInstance(tableName));
        }

    }

    private void setUpTabsWithPagers(){
        fragments = new ArrayList<>();
        fillHomeCategoriesPager();
        HomeCategoriesPagerAdapter homeCategoriesPagerAdapter = new HomeCategoriesPagerAdapter(requireActivity(), fragments);
        binding.chipViewPager2.setAdapter(homeCategoriesPagerAdapter);
        new TabLayoutMediator(binding.chipTabLayout, binding.chipViewPager2, (tab, position) -> {

          tab.setText(Utils.tableListNames.get(position));

        }).attach();
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
        if(homeListener != null) {homeListener = null;}

    }

}