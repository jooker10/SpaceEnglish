/*
 * File: TablePagerAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Adapter for ViewPager2 to display fragments in a table layout.
 */

package edu.SpaceLearning.SpaceEnglish.Adapters;

// Android imports
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// Java imports
import java.util.ArrayList;

/**
 * Adapter for ViewPager2 to display fragments in a table layout.
 */
public class TablePagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragments; // List of fragments to display

    /**
     * Constructor to initialize the TablePagerAdapter.
     * @param fragmentActivity The FragmentActivity associated with the adapter.
     * @param fragments List of fragments to be displayed in ViewPager2.
     */
    public TablePagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments; // Initialize list of fragments
    }

    /**
     * Called to instantiate the fragment for the given position.
     * @param position The position of the fragment in the ViewPager2.
     * @return The fragment at the specified position.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position); // Return fragment at specified position
    }

    /**
     * Returns the number of fragments to be displayed.
     * @return The total number of fragments.
     */
    @Override
    public int getItemCount() {
        return fragments.size(); // Return total number of fragments
    }
}
