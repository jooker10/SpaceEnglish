package edu.SpaceLearning.SpaceEnglish.navfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.ChipPagerAdapter;
import edu.SpaceLearning.SpaceEnglish.Adapters.ChipRecyclerAdapter;
import edu.SpaceLearning.SpaceEnglish.ChipItem;
import edu.SpaceLearning.SpaceEnglish.ChipPagerFragment;
import edu.SpaceLearning.SpaceEnglish.TablesFrags.TableCategoryPagerFragment;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.HomeNavFragmentBinding;


public class HomeNavFragment extends Fragment {



    private HomeNavFragmentBinding binding;
    private SharedPreferences sharedPreferences;
    private HomeFragClickListener homeListener;
    private OnFragmentNavigationListener navigationListener;


    ChipRecyclerAdapter chipRecyclerAdapter;
    ArrayList<Fragment> fragments;

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
        Utils.fragmentNameTagSearch = "Home";
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }


        //------------pick Image for profile-------------------
        binding.imgHomeProfile.setOnClickListener(view1 -> homeListener.onPickImage());

        if (Utils.uriProfile != null && !Utils.uriProfile.toString().isEmpty() ) {
            binding.imgHomeProfile.setImageURI(Utils.uriProfile);
        } else {
            binding.imgHomeProfile.setImageResource(R.drawable.ic_person_24);
        }

        //_____shared preferences---------------------------------------
        Utils.userName = sharedPreferences.getString(Constants.KEY_USER_NAME, "User 01");
        binding.tvHomeUserName.setText("Hi, " + Utils.userName);

        //---------btn_home_get started--------------------------------------
        binding.btnHomeGoToLearn.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.TABLE_NAV_INDEX));
        binding.btnHomeGoToQuiz.setOnClickListener(v -> homeListener.onHomeGetStarted(Constants.QUIZ_NAV_INDEX));

        // pager and tabLayout
        fragments = new ArrayList<>();
        fillChipPager();
        ChipPagerAdapter chipPagerAdapter = new ChipPagerAdapter(requireActivity(), fragments);
        binding.chipViewPager2.setAdapter(chipPagerAdapter);
        setUpTabsWithPagers();


    }

    private void fillChipPager(){
        fragments.add(ChipPagerFragment.newInstance(Constants.ALL_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.VERB_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.SENTENCE_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.PHRASAL_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.NOUN_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.ADJ_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.ADV_NAME));
        fragments.add(ChipPagerFragment.newInstance(Constants.IDIOM_NAME));
    }

    private void setUpTabsWithPagers(){
        new TabLayoutMediator(binding.chipTabLayout, binding.chipViewPager2, (tab, position) -> {

            switch (position) {
                case 0 :
                    tab.setText(Constants.ALL_NAME);
                    break;
                case 1:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case 2:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                case 3:
                     tab.setText(Constants.PHRASAL_NAME);
                    //enabledTabs(tab, Scores.totalScore,Constants.permissionPhrasalScore,Constants.PHRASAL_NAME);

                    break;
                case 4:
                     tab.setText(Constants.NOUN_NAME);
                   // enabledTabs(tab,Scores.totalScore,Constants.permissionNounScore,Constants.NOUN_NAME);
                    break;
                case 5:
                     tab.setText(Constants.ADJ_NAME);
                    //enabledTabs(tab,Scores.totalScore,Constants.permissionAdjScore,Constants.ADJ_NAME);
                    break;
                case 6:
                     tab.setText(Constants.ADV_NAME);
                    //enabledTabs(tab,Scores.totalScore,Constants.permissionAdvScore,Constants.ADV_NAME);
                    break;
                case 7:
                    tab.setText(Constants.IDIOM_NAME);
                    //enabledTabs(tab,Scores.totalScore,Constants.permissionIdiomScore,Constants.IDIOM_NAME);
                    break;

            }


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