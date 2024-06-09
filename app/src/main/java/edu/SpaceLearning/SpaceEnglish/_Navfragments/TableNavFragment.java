package edu.SpaceLearning.SpaceEnglish._Navfragments;

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
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.TablesFrags.TableCategoryPagerFragment;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;
import edu.SpaceLearning.SpaceEnglish.WaitFragment;
import edu.SpaceLearning.SpaceEnglish.databinding.TableContainerFragmentBinding;


public class TableNavFragment extends Fragment {

    private TableContainerFragmentBinding binding;
    private final ArrayList<Fragment> pagerListFragments = new ArrayList<>();;

    public TableNavFragment() {
        // Required empty public constructor
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


        addAllowedPagerCategoryFragments(); // fill the required pagerFragments in TableFragment.

        setUpTabsTableWithPager2(); // combine Tabs with pager2 in TableFragment.

    }

    private void addAllowedPagerCategoryFragments() {
        pagerListFragments.add(TableCategoryPagerFragment.getInstance(Constants.VERB_NAME));
        pagerListFragments.add(TableCategoryPagerFragment.getInstance(Constants.SENTENCE_NAME));

        for(int i = 0 ; i < Constants.permissionCategoryScoreArray.length ; i++) {
            addAllowedFragment(pagerListFragments, TableCategoryPagerFragment.getInstance(Constants.categoryNameArray[i + 2])
                    , Scores.totalScore,Constants.permissionCategoryScoreArray[i]);
        }

    }
    private void setUpTabsTableWithPager2(){
        TablePagerAdapter tablePagerAdapter = new TablePagerAdapter(requireActivity(), pagerListFragments);
        binding.tableNavPager2.setAdapter(tablePagerAdapter);
        new TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case 1:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                default:
                    enabledTabs(tab,Scores.totalScore,Constants.permissionCategoryScoreArray[position - 2],Constants.categoryNameArray[position]);
                    break;
            }


        }).attach();
    }

    void enabledTabs(TabLayout.Tab tab, int globalMainScore, int requiredScore, String categoryType) {
        if (globalMainScore <= requiredScore) {
            // Inflate the custom tab layout
            View customTabView = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_tab_layout, null);

            // Find views within the custom tab layout
            ImageView tabIcon = customTabView.findViewById(R.id.tab_idioms_img);
            TextView tabTitle = customTabView.findViewById(R.id.tab_idioms_title);

            // Set the tab title and ensure it's in all caps
            tabTitle.setText(categoryType.toUpperCase());

            // Set the custom view for the tab
            tab.setCustomView(customTabView);
        } else {
            // If the score is higher than the required score, simply set the tab text
            tab.setText(categoryType.toUpperCase());
        }
    }


    void addAllowedFragment(ArrayList<Fragment> fragments, Fragment fragment , int globalMainScore, int permissionScore){

        if(globalMainScore >= permissionScore) {
            fragments.add(fragment);
        }
        else {
            fragments.add(WaitFragment.newInstance(permissionScore));
        }

    }
}