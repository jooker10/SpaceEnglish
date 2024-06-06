package edu.SpaceLearning.SpaceEnglish;


import androidx.fragment.app.Fragment;

import edu.SpaceLearning.SpaceEnglish.Adapters.CategoryRecyclerAdapter;

public interface OnFragmentNavigationListener {
    void onFragmentSelected(Fragment fragment);
    void onSetAdapterClick(CategoryRecyclerAdapter adapter);
}
