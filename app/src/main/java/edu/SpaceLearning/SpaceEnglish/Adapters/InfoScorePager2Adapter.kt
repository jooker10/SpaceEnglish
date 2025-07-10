/*
 * File: InfoScorePager2Adapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Adapter for managing fragments in a ViewPager2 for displaying information scores.
 */
package edu.SpaceLearning.SpaceEnglish.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Android imports

/**
 * Adapter for managing fragments in a ViewPager2 for displaying information scores.
 */
class InfoScorePager2Adapter
/**
 * Constructor to initialize the adapter with a FragmentActivity and list of fragments.
 * @param fragmentActivity The FragmentActivity hosting the adapter.
 * @param fragments List of fragments to display in the ViewPager2.
 */(
    fragmentActivity: FragmentActivity, // List of fragments to display in ViewPager2
    private val fragments: ArrayList<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {
    /**
     * Called to instantiate the fragment for the given position.
     * @param position The position of the fragment within the adapter's data set.
     * @return The Fragment instance to be displayed at the given position.
     */
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of fragments in the adapter.
     */
    override fun getItemCount(): Int {
        return fragments.size
    }
}
