package anouar.oulhaj.p001;


import androidx.fragment.app.Fragment;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;

public interface OnFragmentNavigationListener {
    void onFragmentSelected(Fragment fragment);
    void onSetAdapterClick(CategoryRecyclerAdapter adapter);
}
