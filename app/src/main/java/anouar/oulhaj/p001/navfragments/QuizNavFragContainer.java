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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import anouar.oulhaj.p001.MainActivity;
import anouar.oulhaj.p001.QuizFrags.ChoicesPhrasalQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesSentencesQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesVerbsQcmFrag;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Adapters.PagerAdapter;


public class QuizNavFragContainer extends Fragment implements ChoicesSentencesQcmFrag.setLoadedFragSentencesListener {

    private static final int VERBS_INDEX = 0;
    private static final int SENTENCES_INDEX = 1;
    private static final int PHRASAL_INDEX = 2;
    private static final String VERBS_NAME = "Verbs";
    private static final String SENTENCES_NAME = "Sentences";
    private static final String PHRASAL_NAME = "Phrasal Verbs";

    PagerAdapter pagerAdapter;
    TabLayout tab_layout;
    ViewPager2 pager2;
    private AutoCompleteTextView autoTxt_category;
    private TextInputLayout txtInputLayout_spinner;


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
         autoTxt_category = view.findViewById(R.id.autoTxt_nav_Sentence_container);
         txtInputLayout_spinner = view.findViewById(R.id.choices_spinner_category);
         //-----some initialisation-----------
         txtInputLayout_spinner.setVisibility(View.GONE);
         autoTxt_category.setText(MainActivity.MAIN_CATEGORY_SENTENCES);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChoicesVerbsQcmFrag());
        fragments.add(ChoicesSentencesQcmFrag.newInstance(MainActivity.MAIN_CATEGORY_SENTENCES));
        fragments.add(new ChoicesPhrasalQcmFrag());

         pagerAdapter = new PagerAdapter(getActivity(),fragments);
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
            }

        }).attach();

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getText().toString()){
                    case VERBS_NAME:
                    case PHRASAL_NAME:
                        txtInputLayout_spinner.setVisibility(View.GONE);
                        break;
                    case SENTENCES_NAME:
                        txtInputLayout_spinner.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ArrayAdapter adapter_spinner = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categoryOfSentence));
       autoTxt_category.setAdapter(adapter_spinner);
        autoTxt_category.setOnItemClickListener((parent, view1, position, id) -> {
            fragments.set(SENTENCES_INDEX,ChoicesSentencesQcmFrag.newInstance(autoTxt_category.getText().toString()));
            pager2.setAdapter(pagerAdapter);
            pager2.setCurrentItem(SENTENCES_INDEX);
            MainActivity.MAIN_CATEGORY_SENTENCES = autoTxt_category.getText().toString();
            Toast.makeText(getActivity(), MainActivity.MAIN_CATEGORY_SENTENCES, Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public void reLoadFragSpinner(String category) {
        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();
    }
}