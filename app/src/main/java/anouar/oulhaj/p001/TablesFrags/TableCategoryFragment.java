package anouar.oulhaj.p001.TablesFrags;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;
import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableCategoryFragment extends Fragment {

    private QuizCategoriesFragment.QuizCategoryClickListener listener;
    private String categoryType;
    private TextView tvHeadTitleCategory;
    private TextToSpeech speech;


    public TableCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(Constants.TAG_CATEGORY_TYPE, "Verbs");
        }
    }

    public static TableCategoryFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType);
        TableCategoryFragment fragment = new TableCategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tables_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        speech = new TextToSpeech(requireActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    speech.setLanguage(Locale.ENGLISH);
                } else {
                    // Handle initialization failure if needed.
                }
            }
        });

        RecyclerView recycler = view.findViewById(R.id.recyclerTableCategory);
        tvHeadTitleCategory = view.findViewById(R.id.headTitleForTableCategory);

        ArrayList<Category> elements = selectElementType(categoryType);

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(elements, requireActivity(), categoryType,speech, new CategoryRecyclerAdapter.onRecyclerListener() {
            @Override
            public void onDataChanged() {

            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
    }


    private ArrayList<Category> selectElementType(String categoryType) {
        ArrayList<Category> elements = new ArrayList<>();
        switch (categoryType) {
            case Constants.VERB_NAME:
                elements = Utils.verbsList;
                tvHeadTitleCategory.setText("Table of Verbs (" + Utils.verbsList.size() + ")");
                break;
            case Constants.SENTENCE_NAME:
                elements = Utils.sentencesList;
                tvHeadTitleCategory.setText("Table of Sentence-Phrases (" + Utils.sentencesList.size() + ")");
                tvHeadTitleCategory.setTextSize(22f);
                break;
            case Constants.PHRASAL_NAME:
                elements = Utils.phrasalsList;
                tvHeadTitleCategory.setText("Table of Phrasal Verbs (" + Utils.phrasalsList.size() + ")");
                break;
            case Constants.NOUN_NAME:
                elements = Utils.nounsList;
                tvHeadTitleCategory.setText("Table of Nouns (" + Utils.nounsList.size() + ")");
                break;
            case Constants.ADJ_NAME:
                elements = Utils.adjsList;
                tvHeadTitleCategory.setText("Table of Adjectives (" + Utils.adjsList.size() + ")");
                break;
            case Constants.ADV_NAME:
                elements = Utils.advsList;
                tvHeadTitleCategory.setText("Table of Adverbs (" + Utils.advsList.size() + ")");
                break;
            case Constants.IDIOM_NAME:
                elements = Utils.idiomsList;
                tvHeadTitleCategory.setText("Table of Idioms (" + Utils.idiomsList.size() + ")");
                break;
        }

        return elements;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release the TextToSpeech resources when the activity is destroyed.
        if (speech != null) {
            speech.stop();
            speech.shutdown();
        }
    }
}