package anouar.oulhaj.p001.TablesFrags;

import android.content.Context;
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
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001.DB.Category;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;


public class TableCategoryFragment extends Fragment {

    private onFilterRecycleClickListener filterListener;
    private OnFragmentNavigationListener navigationListener;
    private String categoryType;
    private TextView tvHeadTitleCategory;
    private TextToSpeech speech;
    CategoryRecyclerAdapter adapter;
    DbAccess dbAccess;
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
        return inflater.inflate(R.layout.fragment_tables_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHeadTitleCategory = view.findViewById(R.id.headTitleForTableCategory);
        RecyclerView recycler = view.findViewById(R.id.recyclerTableCategory);
        dbAccess = DbAccess.getInstance(requireActivity());
        speech = new TextToSpeech(requireActivity(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                speech.setLanguage(Locale.ENGLISH);
            }
        });

        ArrayList<Category> elements = dbAccess.getRequiredElementsList(categoryType,tvHeadTitleCategory);

         adapter = new CategoryRecyclerAdapter(elements, requireActivity(), categoryType, speech);

        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
        navigationListener.onSetAdapterClick(adapter);

       // tvHeadTitleCategory.setOnClickListener(view1 -> filterListener.onFilterClick(adapter));
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