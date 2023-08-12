package anouar.oulhaj.p001.navfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import anouar.oulhaj.p001.Adapters.PagerAdapter;
import anouar.oulhaj.p001.EnumCategory;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.TablesFrags.TableCategoryFragment;
import anouar.oulhaj.p001.databinding.TableContainerFragmentBinding;


public class TablesNavFragments extends Fragment {

    TableContainerFragmentBinding binding;

    public TablesNavFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_container_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = TableContainerFragmentBinding.bind(view);
        //---------Setup tabs with pagers---------------------------------


        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.VERB.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.SENTENCE.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.PHRASAL.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.NOUN.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.ADJECTIVE.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.ADVERB.name()));
        fragments.add(TableCategoryFragment.getInstance(EnumCategory.IDIOM.name()));


        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity(), fragments);
        binding.tableNavPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(binding.tableNavTabLayout, binding.tableNavPager2, (tab, position) -> {

            switch (position) {
                case 0:
                    tab.setText("Verbs");
                    break;
                case 1:
                    tab.setText("Sentences-Phrases");
                    break;
                case 2:
                    tab.setText("Phrasal verbs");
                    break;
                case 3:
                    tab.setText("Nouns");
                    break;
                case 4:
                    tab.setText("Adjectives");
                    break;
                case 5:
                    tab.setText("Adverbs");
                    break;
                case 6:
                    tab.setText("Idioms");
                    break;

            }

        }).attach();
        //------fin setup tabs with pagers----------------------------------------------
    }
}