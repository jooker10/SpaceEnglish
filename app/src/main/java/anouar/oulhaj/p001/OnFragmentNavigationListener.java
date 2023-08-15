package anouar.oulhaj.p001;


import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;

public interface OnFragmentNavigationListener {
    void onFragmentSelected(Fragment fragment);
    void onSetAdapterClickListener(CategoryRecyclerAdapter adapter);
}
