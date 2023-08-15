package anouar.oulhaj.p001.TablesFrags;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

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
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableCategoryFragment extends Fragment {

    private onFilterRecycleClickListener filterListener;
    private OnFragmentNavigationListener navigationListener;
    private String categoryType;
    private TextView tvHeadTitleCategory;
    private TextToSpeech speech;
    CategoryRecyclerAdapter adapter;
   // SearchView searchView;


    public TableCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFilterRecycleClickListener) {
            filterListener = (onFilterRecycleClickListener) context;
        }
        if(context instanceof OnFragmentNavigationListener){
            navigationListener = (OnFragmentNavigationListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (filterListener != null)
            filterListener = null;
        if (navigationListener != null)
            navigationListener = null;

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
        View view = inflater.inflate(R.layout.fragment_tables_category, container, false);
        setHasOptionsMenu(true); // Inflate the fragment's menu
        return view;
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

         adapter = new CategoryRecyclerAdapter(elements, requireActivity(), categoryType, speech);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
       // recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        navigationListener.onSetAdapterClickListener(adapter);

        tvHeadTitleCategory.setOnClickListener(view1 -> filterListener.onFilterClick(adapter));
    }


    private ArrayList<Category> selectElementType(String categoryType) {
        ArrayList<Category> elements = new ArrayList<>();
        switch (categoryType) {
            case Constants.VERB_NAME:
                elements = new ArrayList<>(Utils.verbsList);
                tvHeadTitleCategory.setText("Table of Verbs (" + Utils.verbsList.size() + ")");
                break;
            case Constants.SENTENCE_NAME:
                elements = new ArrayList<>(Utils.sentencesList);
                tvHeadTitleCategory.setText("Table of Sentence-Phrases (" + Utils.sentencesList.size() + ")");
                tvHeadTitleCategory.setTextSize(22f);
                break;
            case Constants.PHRASAL_NAME:
                elements = new ArrayList<>(Utils.phrasalsList);
                tvHeadTitleCategory.setText("Table of Phrasal Verbs (" + Utils.phrasalsList.size() + ")");
                break;
            case Constants.NOUN_NAME:
                elements = new ArrayList<>(Utils.nounsList);
                tvHeadTitleCategory.setText("Table of Nouns (" + Utils.nounsList.size() + ")");
                break;
            case Constants.ADJ_NAME:
                elements = new ArrayList<>(Utils.adjsList);
                tvHeadTitleCategory.setText("Table of Adjectives (" + Utils.adjsList.size() + ")");
                break;
            case Constants.ADV_NAME:
                elements = new ArrayList<>(Utils.advsList);
                tvHeadTitleCategory.setText("Table of Adverbs (" + Utils.advsList.size() + ")");
                break;
            case Constants.IDIOM_NAME:
                elements = new ArrayList<>(Utils.idiomsList);
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

    public interface onFilterRecycleClickListener {
        void onFilterClick(CategoryRecyclerAdapter adapter);
    }
}