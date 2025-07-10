/*
 * File: InfoScorePager2Adapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Adapter for managing fragments in a ViewPager2 for displaying information scores.
 */

package edu.SpaceLearning.SpaceEnglish.Adapters;

// Android imports
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * Adapter for managing fragments in a ViewPager2 for displaying information scores.
 */
public class InfoScorePager2Adapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragments; // List of fragments to display in ViewPager2

    /**
     * Constructor to initialize the adapter with a FragmentActivity and list of fragments.
     * @param fragmentActivity The FragmentActivity hosting the adapter.
     * @param fragments List of fragments to display in the ViewPager2.
     */
    public InfoScorePager2Adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    /**
     * Called to instantiate the fragment for the given position.
     * @param position The position of the fragment within the adapter's data set.
     * @return The Fragment instance to be displayed at the given position.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of fragments in the adapter.
     */
    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
