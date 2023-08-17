package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import anouar.oulhaj.p001.Adapters.PagerAdapter;
import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.TablesFrags.TableCategoryFragment;
import anouar.oulhaj.p001._Main.Utils;
import anouar.oulhaj.p001.WaitFragment;
import anouar.oulhaj.p001.databinding.TableContainerFragmentBinding;


public class TablesNavFragments extends Fragment {

    TableContainerFragmentBinding binding;
    private OnFragmentNavigationListener navigationListener;

    public TablesNavFragments() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(navigationListener != null)
            navigationListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_container_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = TableContainerFragmentBinding.bind(view);

        Utils.nameOfFragmentSearchView = "Tables";

        //---------Setup tabs with pagers---------------------------------
        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

         boolean isIdiomsUnlocked = false;
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TableCategoryFragment.getInstance(Constants.VERB_NAME));
        fragments.add(TableCategoryFragment.getInstance(Constants.SENTENCE_NAME));
        fragments.add(TableCategoryFragment.getInstance(Constants.PHRASAL_NAME));
        fragments.add(TableCategoryFragment.getInstance(Constants.NOUN_NAME));
        fragments.add(TableCategoryFragment.getInstance(Constants.ADJ_NAME));
        fragments.add(TableCategoryFragment.getInstance(Constants.ADV_NAME));
        if(isIdiomsUnlocked)
        fragments.add(TableCategoryFragment.getInstance(Constants.IDIOM_NAME));
        else
            fragments.add(new WaitFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity(), fragments);
        binding.tableNavPager2.setAdapter(pagerAdapter);


        setUpTabsWithPagers();


    }

    //------Functions----------
    // Function to change the fragment associated with a specific tab
    private void changeFragmentForTab(int tabIndex, Fragment newFragment) {
        FragmentStateAdapter adapter = (FragmentStateAdapter) binding.tableNavPager2.getAdapter();
        if (adapter != null) {
            adapter.notifyItemRemoved(1);
        }
    }
    private void setUpTabsWithPagers(){
        new TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2, (tab, position) -> {
            View customTabView = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_tab_layout, null);
            ImageView tabIcon = customTabView.findViewById(R.id.tab_idioms_img);
            TextView tabTitle = customTabView.findViewById(R.id.tab_idioms_title);
            switch (position) {
                case 0:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case 1:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                case 2:
                    tab.setText(Constants.PHRASAL_NAME);
                    break;
                case 3:
                    tab.setText(Constants.NOUN_NAME);
                    break;
                case 4:
                    tab.setText(Constants.ADJ_NAME);
                    break;
                case 5:
                    tab.setText(Constants.ADV_NAME);
                    break;
                case 6:
                    // Set the icon and title
                   /* tabIcon.setImageResource(R.drawable.ic_lock_24);
                    tabTitle.setText("Idioms");

                    tab.setCustomView(customTabView);*/
                   /* tab.setIcon(R.drawable.ic_lock_24);
                    ;*/
                    tab.setText(Constants.IDIOM_NAME);
                    break;

            }


        }).attach();
    }
}