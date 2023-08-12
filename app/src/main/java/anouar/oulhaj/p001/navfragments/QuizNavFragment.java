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
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;


public class QuizNavFragment extends Fragment  {

    private static final int VERBS_INDEX = 0;
    private static final int SENTENCES_INDEX = 1;
    private static final int PHRASAL_INDEX = 2;
    private static final int NOUN_INDEX = 3;
    private static final int ADJ_INDEX = 4;
    private static final int ADV_INDEX = 5;
    private static final int IDIOM_INDEX = 6;
    private static final String VERBS_NAME = "Verbs";
    private static final String SENTENCES_NAME = "Sentences";
    private static final String PHRASAL_NAME = "Phrasal Verbs";
    private static final String NOUN_NAME = "Nouns";
    private static final String ADJ_NAME = "Adjectives";
    private static final String ADV_NAME = "Adverbs";
    private static final String IDIOM_NAME = "Idioms";

    public QuizNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_nav_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tab_layout = view.findViewById(R.id.tablayout_quiz);
        ViewPager2 pager2 = view.findViewById(R.id.viewPager_quiz);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.VERB.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.SENTENCE.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.PHRASAL.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.NOUN.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.ADJECTIVE.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.ADVERB.name()));
        fragments.add(QuizCategoriesFragment.getInstance(EnumCategory.IDIOM.name()));

        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity(), fragments);
        pager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tab_layout, pager2, (tab, position) -> {

            switch(position){
                case VERBS_INDEX:
                    tab.setText(VERBS_NAME);
                    break;
                case SENTENCES_INDEX:
                    tab.setText(SENTENCES_NAME);
                    break;
                case PHRASAL_INDEX:
                    tab.setText(PHRASAL_NAME);
                    break;
                case NOUN_INDEX:
                    tab.setText(NOUN_NAME);
                    break;
                case ADJ_INDEX:
                    tab.setText(ADJ_NAME);
                    break;
                case ADV_INDEX:
                    tab.setText(ADV_NAME);
                    break;
                case IDIOM_INDEX:
                    tab.setText(IDIOM_NAME);
                    break;
            }

        }).attach();

    }

}