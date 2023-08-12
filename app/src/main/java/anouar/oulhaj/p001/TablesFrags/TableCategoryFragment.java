package anouar.oulhaj.p001.TablesFrags;

import android.os.Bundle;
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

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class TableCategoryFragment extends Fragment {

    private QuizCategoriesFragment.QuizCategoryClickListener listener;
    public static String TAG_CATEGORY_TYPE = "category";
    private String categoryType;
    private TextView tvHeadTitleCategory;



    public TableCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            categoryType = bundle.getString(TAG_CATEGORY_TYPE,"VERB");
        }
    }

    public static TableCategoryFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG_CATEGORY_TYPE,categoryType);
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

        RecyclerView recycler = view.findViewById(R.id.recyclerTableCategory);
         tvHeadTitleCategory = view.findViewById(R.id.headTitleForTableCategory);

        ArrayList<Category> elements = selectElementType(categoryType);

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(elements, requireActivity(),categoryType, new CategoryRecyclerAdapter.onRecyclerListener() {
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
            case "VERB":
          elements = Utils.verbsList;
                tvHeadTitleCategory.setText("Table of Verbs (" + Utils.verbsList.size() + ")");
                break;
            case "SENTENCE":
                elements = Utils.sentencesList;
                tvHeadTitleCategory.setText("Table of Sentence-Phrases (" + Utils.sentencesList.size() + ")");
                tvHeadTitleCategory.setTextSize(22f);
                break;
            case "PHRASAL":
                elements = Utils.phrasalsList;
                tvHeadTitleCategory.setText("Table of Phrasal Verbs (" + Utils.phrasalsList.size() + ")");
                break;
            case "NOUN":
                elements = Utils.nounsList;
                tvHeadTitleCategory.setText("Table of Nouns (" + Utils.nounsList.size() + ")");
                break;
            case "ADJECTIVE":
                elements = Utils.adjsList;
                tvHeadTitleCategory.setText("Table of Adjectives (" + Utils.adjsList.size() + ")");
                break;
            case "ADVERB":
                elements = Utils.advsList;
                tvHeadTitleCategory.setText("Table of Adverbs (" + Utils.advsList.size() + ")");
                break;
            case "IDIOM":
                elements = Utils.idiomsList;
                tvHeadTitleCategory.setText("Table of Idioms (" + Utils.idiomsList.size() + ")");
                break;
        }

       return elements;
    }

}