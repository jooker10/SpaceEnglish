package anouar.oulhaj.p001.navfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.TablesFrags.PagerAdapter;
import anouar.oulhaj.p001.TablesFrags.TablePhrasalFragment;
import anouar.oulhaj.p001.TablesFrags.TableSentencesFragment;
import anouar.oulhaj.p001.TablesFrags.TableVerbsFragment;


public class TablesNavFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TablesNavFragments() {
        // Required empty public constructor
    }


    public static TablesNavFragments newInstance(String param1, String param2) {
        TablesNavFragments fragment = new TablesNavFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        TabLayout tabs = view.findViewById(R.id.tablayout_tables);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager_tables);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TableVerbsFragment());
        fragments.add(new TableSentencesFragment());
        fragments.add(new TablePhrasalFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getActivity(),fragments);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabs, viewPager2, (tab, position) -> {

            switch(position){
                case 0:
                    tab.setText("Verbs");
                    break;
                case 1:
                    tab.setText("Sentences");
                    break;
                case 2:
                    tab.setText("Phrasal verbs");
                    break;
            }

        }).attach();


    }
}