package edu.SpaceLearning.SpaceEnglish.TablesFrags;

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

import edu.SpaceLearning.SpaceEnglish.Adapters.CategoryRecyclerAdapter;
import edu.SpaceLearning.SpaceEnglish.DB.DbAccess;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.DB.Category;
import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish._Main.TextToSpeechManager;


public class TableCategoryPagerFragment extends Fragment {

    private onFilterRecycleClickListener filterListener;
    private OnFragmentNavigationListener navigationListener;
    private String categoryType;
    private TextView tvHeadTitleCategory;
    CategoryRecyclerAdapter recylerAdapterCategory;
    DbAccess dbAccess;
    private TextToSpeechManager textToSpeechManager;
    // SearchView searchView;


    public TableCategoryPagerFragment() {
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

    public static TableCategoryPagerFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType);
        TableCategoryPagerFragment fragment = new TableCategoryPagerFragment();
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

        MainActivity mainActivity = (MainActivity) requireActivity();
        textToSpeechManager = mainActivity.getTextToSpeechManager();

        RecyclerView recyclerCategoryTable = view.findViewById(R.id.recyclerTableCategory);
        tvHeadTitleCategory = view.findViewById(R.id.headTitleForTableCategory);

        dbAccess = DbAccess.getInstance(requireActivity());


        ArrayList<Category> elements = dbAccess.getRequiredElementsList(categoryType,tvHeadTitleCategory);

        CategoryRecyclerAdapter.onShowAdsClickListener listener = (CategoryRecyclerAdapter.onShowAdsClickListener) requireActivity();
         recylerAdapterCategory = new CategoryRecyclerAdapter(elements, requireActivity(), categoryType , textToSpeechManager , listener );

        recyclerCategoryTable.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerCategoryTable.setHasFixedSize(true);
        recyclerCategoryTable.setAdapter(recylerAdapterCategory);

        navigationListener.onSetAdapterClick(recylerAdapterCategory);
        tvHeadTitleCategory.setOnClickListener(view1 -> filterListener.onFilterClick(recylerAdapterCategory));
    }


    public interface onFilterRecycleClickListener {
        void onFilterClick(CategoryRecyclerAdapter adapter);
    }
}