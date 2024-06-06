package edu.SpaceLearning.SpaceEnglish.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.TablePagerAdapter;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.TablesFrags.TableCategoryPagerFragment;
import edu.SpaceLearning.SpaceEnglish._Main.Scores;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;
import edu.SpaceLearning.SpaceEnglish.WaitFragment;
import edu.SpaceLearning.SpaceEnglish.databinding.TableContainerFragmentBinding;


public class TablesNavFragments extends Fragment {

    TableContainerFragmentBinding binding;
    private OnFragmentNavigationListener navigationListener;
    ArrayList<Fragment> pagerFragments;

    public TablesNavFragments() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(navigationListener != null)
            navigationListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_container_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = TableContainerFragmentBinding.bind(view);

        Utils.fragmentNameTagSearch = "Tables";  // a TAG name for showing the search icon.

        //---------Setup tabs with pagers---------------------------------
        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

       //  boolean isIdiomsUnlocked = false;
         pagerFragments = new ArrayList<>();
        addAllowedPagerCategoryFragments(); // fill the pagerFragments.
        TablePagerAdapter tablePagerAdapter = new TablePagerAdapter(requireActivity(), pagerFragments);
        binding.tableNavPager2.setAdapter(tablePagerAdapter);
        setUpTabsWithPagers();

    }

    private void addAllowedPagerCategoryFragments() {
        pagerFragments.add(TableCategoryPagerFragment.getInstance(Constants.VERB_NAME));
        pagerFragments.add(TableCategoryPagerFragment.getInstance(Constants.SENTENCE_NAME));

        addPermittedFragment(pagerFragments, TableCategoryPagerFragment.getInstance(Constants.PHRASAL_NAME)
                , Scores.totalScore,Constants.permissionPhrasalScore,Constants.PHRASAL_NAME);

        addPermittedFragment(pagerFragments, TableCategoryPagerFragment.getInstance(Constants.NOUN_NAME)
                ,Scores.totalScore,Constants.permissionNounScore,Constants.NOUN_NAME);

        addPermittedFragment(pagerFragments, TableCategoryPagerFragment.getInstance(Constants.ADJ_NAME)
                ,Scores.totalScore,Constants.permissionAdjScore,Constants.ADJ_NAME);

        addPermittedFragment(pagerFragments, TableCategoryPagerFragment.getInstance(Constants.ADV_NAME)
                ,Scores.totalScore,Constants.permissionAdvScore,Constants.ADV_NAME);

        addPermittedFragment(pagerFragments, TableCategoryPagerFragment.getInstance(Constants.IDIOM_NAME)
                ,Scores.totalScore,Constants.permissionIdiomScore,Constants.IDIOM_NAME);
    }
    private void setUpTabsWithPagers(){
        new TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case 1:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                case 2:
                   // tab.setText(Constants.PHRASAL_NAME);
                    enabledTabs(tab,Scores.totalScore,Constants.permissionPhrasalScore,Constants.PHRASAL_NAME);

                    break;
                case 3:
                   // tab.setText(Constants.NOUN_NAME);
                    enabledTabs(tab,Scores.totalScore,Constants.permissionNounScore,Constants.NOUN_NAME);
                    break;
                case 4:
                   // tab.setText(Constants.ADJ_NAME);
                    enabledTabs(tab,Scores.totalScore,Constants.permissionAdjScore,Constants.ADJ_NAME);
                    break;
                case 5:
                   // tab.setText(Constants.ADV_NAME);
                    enabledTabs(tab,Scores.totalScore,Constants.permissionAdvScore,Constants.ADV_NAME);
                    break;
                case 6:

                   enabledTabs(tab,Scores.totalScore,Constants.permissionIdiomScore,Constants.IDIOM_NAME);
                    break;

            }


        }).attach();
    }

    void enabledTabs(TabLayout.Tab tab , int globalMainScore , int perpermissionScore, String categoryType) {
        if(globalMainScore <= perpermissionScore ) {
            View customTabView = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_tab_layout, null);
            ImageView tabIcon = customTabView.findViewById(R.id.tab_idioms_img);
            TextView tabTitle = customTabView.findViewById(R.id.tab_idioms_title);
            tabTitle.setText(categoryType);
            tabTitle.setAllCaps(true);
            tab.setCustomView(customTabView);
        } else {
            tab.setText(categoryType);
        }
    }

    void addPermittedFragment(ArrayList<Fragment> fragments,Fragment fragment ,int globalMainScore,int permissionScore , String categoryType){

        if(globalMainScore >= permissionScore) {
            fragments.add(fragment);
        }
        else {
            fragments.add(WaitFragment.newInstance(permissionScore));
        }

    }
}