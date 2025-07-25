/*
 * File: TableNavigationFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for managing table categories with tabs and pager in the SpaceEnglish app.
 *          This fragment dynamically adds table category fragments based on user permissions
 *          and sets up tabs with a pager adapter.
 */
package edu.SpaceLearning.SpaceEnglish._Navigationfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.SpaceLearning.SpaceEnglish.Adapters.TablePager2Adapter
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.TablesFrags.CategoryTableFragment
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores
import edu.SpaceLearning.SpaceEnglish.InsufficientScoreFragment.Companion.newInstance
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.CategoryType
import edu.SpaceLearning.SpaceEnglish._RoomDatabase.Entities.CategoryViewModel
import edu.SpaceLearning.SpaceEnglish.databinding.FragmentTableNavigationBinding
import java.util.Locale

// Android imports


class TableNavigationFragment : Fragment() {
    // View binding for this fragment
    private lateinit var binding: FragmentTableNavigationBinding
    private lateinit var adapter : TablePager2Adapter
    private val viewModel : CategoryViewModel by viewModels()

    // List to hold pager fragments for table categories
    private val pager2FragmentsList = ArrayList<Fragment>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table_navigation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTableNavigationBinding.bind(view)

        // Add allowed pager category fragments to the list
        setAllowedPagerCategoryFragments()

        // Set up tabs with pager for table categories
        setUpTabsTableWithPager2()
    }

    private fun setAllowedPagerCategoryFragments() {
        // Always add these default fragments
        pager2FragmentsList.add(CategoryTableFragment.newInstance(Constants.CATEGORY_NAME_VERB))
        pager2FragmentsList.add(CategoryTableFragment.newInstance(Constants.CATEGORY_NAME_SENTENCE))

        pager2FragmentsList.add(CategoryTableFragment.newInstance(Constants.CATEGORY_NAME_VERB))
        pager2FragmentsList.add(CategoryTableFragment.newInstance(Constants.CATEGORY_NAME_SENTENCE))



        // Add other fragments based on permission scores
       /* for (i in Constants.CATEGORY_PERMISSION_SCORES.indices) {
            addAllowedFragment(
                pager2FragmentsList,
                CategoryTableFragment.newInstance(Constants.CATEGORY_NAME_LIST[i + 2]),
                Scores.totalScore,
                Constants.CATEGORY_PERMISSION_SCORES[i]
            )
        }*/
    }


    private fun setUpTabsTableWithPager2() {
        // Create and set the pager adapter for the table categories
        adapter = TablePager2Adapter(requireActivity(), pager2FragmentsList)
        binding.tableNavPager2.adapter = adapter

        viewModel.load(CategoryType.Sentence)
        lifecycleScope.launchWhenStarted {
            viewModel.categories.collect {
                    items -> adapter.submitList(items)
            }
        }

        // Attach tabs with the pager using TabLayoutMediator
        TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2)
        {
            tab: TabLayout.Tab, position: Int ->
            // Customize tabs based on position
            when (position) {
                0 -> tab.text = Constants.CATEGORY_NAME_VERB
                1 -> tab.text = Constants.CATEGORY_NAME_SENTENCE
                else -> enabledTabs(
                    tab,
                    Scores.totalScore,
                    Constants.CATEGORY_PERMISSION_SCORES[position - 2],
                    Constants.CATEGORY_NAME_LIST[position]
                )
            }
        }.attach()
    }

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
