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
import java.util.Objects;

import anouar.oulhaj.p001.Adapters.PagerAdapter;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.TablesFrags.TableAdjectivesFragment;
import anouar.oulhaj.p001.TablesFrags.TableAdverbsFragment;
import anouar.oulhaj.p001.TablesFrags.TableIdiomsFragment;
import anouar.oulhaj.p001.TablesFrags.TableNounsFragment;
import anouar.oulhaj.p001.TablesFrags.TablePhrasalFragment;
import anouar.oulhaj.p001.TablesFrags.TableSentencesFragment;
import anouar.oulhaj.p001.TablesFrags.TableVerbsFragment;


public class TablesNavFragments extends Fragment {


    public TablesNavFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.table_fragment_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //---------Setup tabs with pagers---------------------------------
        TabLayout tabs = view.findViewById(R.id.tablayout_tables);
        ViewPager2 viewPager2 = view.findViewById(R.id.pager_tables);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TableVerbsFragment());
        fragments.add(new TableSentencesFragment());
        fragments.add(new TablePhrasalFragment());
        fragments.add(new TableNounsFragment());
        fragments.add(new TableAdjectivesFragment());
        fragments.add(new TableAdverbsFragment());
        fragments.add(new TableIdiomsFragment());


        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity(), fragments);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabs, viewPager2, (tab, position) -> {

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