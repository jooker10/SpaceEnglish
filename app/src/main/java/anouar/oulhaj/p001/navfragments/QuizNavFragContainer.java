package anouar.oulhaj.p001.navfragments;

import android.content.Context;
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

import anouar.oulhaj.p001.QuizFrags.ChoicesPhrasalQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesSentencesQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesVerbsQcmFrag;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Adapters.PagerAdapter;


public class QuizNavFragContainer extends Fragment {

    PagerAdapter pagerAdapter;
    TabLayout tab_layout;
    ViewPager2 pager2;


    public QuizNavFragContainer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_frag_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         tab_layout = view.findViewById(R.id.tablayout_quiz);
         pager2 = view.findViewById(R.id.viewPager_quiz);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChoicesVerbsQcmFrag());
        fragments.add(ChoicesSentencesQcmFrag.newInstance("Category 1"));
        fragments.add(new ChoicesPhrasalQcmFrag());

         pagerAdapter = new PagerAdapter(getActivity(),fragments);
        pager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tab_layout, pager2, (tab, position) -> {

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