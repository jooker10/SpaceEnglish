/*
 * File: TableNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for managing table categories with tabs and pager in the SpaceEnglish app.
 *          This fragment dynamically adds table category fragments based on user permissions
 *          and sets up tabs with a pager adapter.
 */

package edu.SpaceLearning.SpaceEnglish._Navfragments;

// Android imports
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

/**
 * Fragment for managing table categories with tabs and pager.
 * Dynamically adds table category fragments based on user permissions
 * and sets up tabs with a pager adapter.
 */
public class TableNavFragment extends Fragment {

    // View binding for this fragment
    private TableContainerFragmentBinding binding;

    // List to hold pager fragments for table categories
    private final ArrayList<Fragment> pagerListFragments = new ArrayList<>();

    /**
     * Required empty public constructor
     */
    public TableNavFragment() {
        // Required empty public constructor
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_container_fragment, container, false);
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored in to the view.
     * @param view The view returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = TableContainerFragmentBinding.bind(view);

        // Add allowed pager category fragments to the list
        addAllowedPagerCategoryFragments();

        // Set up tabs with pager for table categories
        setUpTabsTableWithPager2();
    }

    /**
     * Adds allowed pager category fragments to the pager list based on user permissions.
     */
    private void addAllowedPagerCategoryFragments() {
        // Always add these default fragments
        pagerListFragments.add(TableCategoryPagerFragment.getInstance(Constants.VERB_NAME));
        pagerListFragments.add(TableCategoryPagerFragment.getInstance(Constants.SENTENCE_NAME));

        // Add other fragments based on permission scores
        for (int i = 0; i < Constants.permissionCategoryScoreArray.length; i++) {
            addAllowedFragment(pagerListFragments, TableCategoryPagerFragment.getInstance(Constants.categoryNameArray[i + 2]),
                    Scores.totalScore, Constants.permissionCategoryScoreArray[i]);
        }
    }

    /**
     * Sets up tabs with a pager adapter for table categories.
     */
    private void setUpTabsTableWithPager2() {
        // Create and set the pager adapter for the table categories
        TablePagerAdapter tablePagerAdapter = new TablePagerAdapter(requireActivity(), pagerListFragments);
        binding.tableNavPager2.setAdapter(tablePagerAdapter);

        // Attach tabs with the pager using TabLayoutMediator
        new TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2, (tab, position) -> {
            // Customize tabs based on position
            switch (position) {
                case 0:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case 1:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                default:
                    enabledTabs(tab, Scores.totalScore, Constants.permissionCategoryScoreArray[position - 2], Constants.categoryNameArray[position]);
                    break;
            }
        }).attach();
    }

    /**
     * Customize tabs based on permission scores.
     * @param tab The tab to customize.
     * @param globalMainScore The user's total score.
     * @param requiredScore The required score for accessing the tab.
     * @param categoryType The type of category for the tab.
     */
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

    /**
     * Adds a fragment to the list if the user has sufficient permission score,
     * otherwise adds a waiting fragment.
     * @param fragments The list of fragments to add to.
     * @param fragment The fragment to add.
     * @param globalMainScore The user's total score.
     * @param permissionScore The required score for accessing the fragment.
     */
    void addAllowedFragment(ArrayList<Fragment> fragments, Fragment fragment, int globalMainScore, int permissionScore) {
        if (globalMainScore >= permissionScore) {
            fragments.add(fragment);
        } else {
            fragments.add(WaitFragment.newInstance(permissionScore));
        }
    }
}
