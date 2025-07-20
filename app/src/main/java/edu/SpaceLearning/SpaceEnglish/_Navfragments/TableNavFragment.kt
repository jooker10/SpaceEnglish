/*
 * File: TableNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for managing table categories with tabs and pager in the SpaceEnglish app.
 *          This fragment dynamically adds table category fragments based on user permissions
 *          and sets up tabs with a pager adapter.
 */
package edu.SpaceLearning.SpaceEnglish._Navfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.SpaceLearning.SpaceEnglish.Adapters.TablePager2Adapter
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.TablesFrags.TableCategoryInnerFragment
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.WaitFragment.Companion.newInstance
import edu.SpaceLearning.SpaceEnglish.databinding.TableContainerFragmentBinding
import java.util.Locale

// Android imports

/**
 * Fragment for managing table categories with tabs and pager.
 * Dynamically adds table category fragments based on user permissions
 * and sets up tabs with a pager adapter.
 */
class TableNavFragment
/**
 * Required empty public constructor
 */
    : Fragment() {
    // View binding for this fragment
    private lateinit var binding: TableContainerFragmentBinding

    // List to hold pager fragments for table categories
    private val pager2FragmentsList = ArrayList<Fragment>()

    /**
     * Called to create the view hierarchy associated with the fragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_container_fragment, container, false)
    }

    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored in to the view.
     * @param view The view returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TableContainerFragmentBinding.bind(view)

        // Add allowed pager category fragments to the list
        setAllowedPagerCategoryFragments()

        // Set up tabs with pager for table categories
        setUpTabsTableWithPager2()
    }

    /**
     * Adds allowed pager category fragments to the pager list based on user permissions.
     */
    private fun setAllowedPagerCategoryFragments() {
        // Always add these default fragments
        pager2FragmentsList.add(TableCategoryInnerFragment.getInstance(Constants.VERB_NAME))
        pager2FragmentsList.add(TableCategoryInnerFragment.getInstance(Constants.SENTENCE_NAME))

        // Add other fragments based on permission scores
        for (i in Constants.permissionCategoryScoreArray.indices) {
            addAllowedFragment(
                pager2FragmentsList,
                TableCategoryInnerFragment.getInstance(Constants.categoryNamesList[i + 2]),
                Scores.totalScore,
                Constants.permissionCategoryScoreArray[i]
            )
        }
    }

    /**
     * Sets up tabs with a pager adapter for table categories.
     */
    private fun setUpTabsTableWithPager2() {
        // Create and set the pager adapter for the table categories
        val tablePager2Adapter = TablePager2Adapter(requireActivity(), pager2FragmentsList)
        binding.tableNavPager2.adapter = tablePager2Adapter

        // Attach tabs with the pager using TabLayoutMediator
        TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2)
        {
            tab: TabLayout.Tab, position: Int ->
            // Customize tabs based on position
            when (position) {
                0 -> tab.text = Constants.VERB_NAME
                1 -> tab.text = Constants.SENTENCE_NAME
                else -> enabledTabs(
                    tab,
                    Scores.totalScore,
                    Constants.permissionCategoryScoreArray[position - 2],
                    Constants.categoryNamesList[position]
                )
            }
        }.attach()
    }

    /**
     * Customize tabs based on permission scores.
     * @param tab The tab to customize.
     * @param globalMainScore The user's total score.
     * @param requiredScore The required score for accessing the tab.
     * @param categoryType The type of category for the tab.
     */
    fun enabledTabs(
        tab: TabLayout.Tab,
        globalMainScore: Int,
        requiredScore: Int,
        categoryType: String
    ) {
        if (globalMainScore <= requiredScore) {
            // Inflate the custom tab layout
            val customTabView = LayoutInflater.from(requireActivity())
                .inflate(R.layout.custom_tab_layout, binding.tableNavTabLayout, false)

            // Find views within the custom tab layout
            //  ImageView tabIcon = customTabView.findViewById(R.id.tab_idioms_img);
            val tabTitle = customTabView.findViewById<TextView>(R.id.tab_idioms_title)

            // Set the tab title and ensure it's in all caps
            tabTitle.text = categoryType.uppercase(Locale.getDefault())

            // Set the custom view for the tab
            tab.customView = customTabView
        } else {
            // If the score is higher than the required score, simply set the tab text
            tab.text = categoryType.uppercase(Locale.getDefault())
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
    fun addAllowedFragment(
        fragments: ArrayList<Fragment>,
        fragment: Fragment,
        globalMainScore: Int,
        permissionScore: Int
    ) {
        if (globalMainScore >= permissionScore) {
            fragments.add(fragment)
        } else {
            fragments.add(newInstance(permissionScore))
        }
    }
}
