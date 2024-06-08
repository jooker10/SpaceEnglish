package edu.SpaceLearning.SpaceEnglish.TablesFrags;

import android.content.Context;
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

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.Category;
import edu.SpaceLearning.SpaceEnglish.OnTablesRecyclerViewClickListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;


public class TableCategoryPagerFragment extends Fragment {

    private onFilterRecycleClickListener filterListener;
    private OnTablesRecyclerViewClickListener onTablesRecyclerViewClickListener;
    private String categoryType;
    private TextView tvTitleType;
    private RecyclerViewAdapter recyclerViewAdapter;
    private DbAccess dbAccess;
    public TableCategoryPagerFragment() {
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


        RecyclerView tableRecyclerView = view.findViewById(R.id.recyclerTableCategory);
        tvTitleType = view.findViewById(R.id.headTitleForTableCategory);

        dbAccess = DbAccess.getInstance(requireActivity());


        ArrayList<Category> elements = dbAccess.getRequiredElementsList(categoryType, tvTitleType);

        RecyclerViewAdapter.onShowAdsClickListener onShowAdsClickListener = (RecyclerViewAdapter.onShowAdsClickListener) requireActivity();
         recyclerViewAdapter = new RecyclerViewAdapter(elements, requireActivity(), categoryType , MainActivity.textToSpeechManager , onShowAdsClickListener );

        tableRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tableRecyclerView.setHasFixedSize(true);
        tableRecyclerView.setAdapter(recyclerViewAdapter);

        onTablesRecyclerViewClickListener.onTableRecyclerViewClick(recyclerViewAdapter);
        tvTitleType.setOnClickListener(view1 -> filterListener.onFilterClick(recyclerViewAdapter));
    }


    public interface onFilterRecycleClickListener {
        void onFilterClick(RecyclerViewAdapter adapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFilterRecycleClickListener) {
            filterListener = (onFilterRecycleClickListener) context;
        }
        if(context instanceof OnTablesRecyclerViewClickListener){
            onTablesRecyclerViewClickListener = (OnTablesRecyclerViewClickListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (filterListener != null)
            filterListener = null;
        if (onTablesRecyclerViewClickListener != null)
            onTablesRecyclerViewClickListener = null;

    }

}