/*
 * File: TablePagerAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Adapter for ViewPager2 to display fragments in a table layout.
 */
package edu.SpaceLearning.SpaceEnglish.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Android imports

// Java imports
/**
 * Adapter for ViewPager2 to display fragments in a table layout.
 */
class TablePager2Adapter// Initialize list of fragments
/**
 * Constructor to initialize the TablePagerAdapter.
 * @param fragmentActivity The FragmentActivity associated with the adapter.
 * @param fragments List of fragments to be displayed in ViewPager2.
 */(
    fragmentActivity: FragmentActivity, // List of fragments to display
    private val fragments: ArrayList<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {
    /**
     * Called to instantiate the fragment for the given position.
     * @param position The position of the fragment in the ViewPager2.
     * @return The fragment at the specified position.
     */
    override fun createFragment(position: Int): Fragment {
        return fragments[position] // Return fragment at specified position
    }

    /**
     * Returns the number of fragments to be displayed.
     * @return The total number of fragments.
     */
    override fun getItemCount(): Int {
        return fragments.size // Return total number of fragments
    }
}
