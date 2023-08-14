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
import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.databinding.QuizNavFragmentBinding;


public class QuizNavFragment extends Fragment  {
   private QuizNavFragmentBinding binding;

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
        binding = QuizNavFragmentBinding.bind(view);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(QuizCategoriesFragment.getInstance(Constants.VERB_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.SENTENCE_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.PHRASAL_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.NOUN_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.ADJ_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.ADV_NAME));
        fragments.add(QuizCategoriesFragment.getInstance(Constants.IDIOM_NAME));

        PagerAdapter pagerAdapter = new PagerAdapter(requireActivity(), fragments);
        binding.quizNavPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator( binding.quizNavTabLayout,  binding.quizNavPager2, (tab, position) -> {

            switch(position){
                case Constants.VERBS_INDEX:
                    tab.setText(Constants.VERB_NAME);
                    break;
                case Constants.SENTENCES_INDEX:
                    tab.setText(Constants.SENTENCE_NAME);
                    break;
                case Constants.PHRASAL_INDEX:
                    tab.setText(Constants.PHRASAL_NAME);
                    break;
                case Constants.NOUN_INDEX:
                    tab.setText(Constants.NOUN_NAME);
                    break;
                case Constants.ADJ_INDEX:
                    tab.setText(Constants.ADJ_NAME);
                    break;
                case Constants.ADV_INDEX:
                    tab.setText(Constants.ADV_NAME);
                    break;
                case Constants.IDIOM_INDEX:
                    tab.setText(Constants.IDIOM_NAME);
                    break;
            }

        }).attach();

    }

}