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
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;


public class TableCategoryPagerFragment extends Fragment {

    private InteractionActivityFragmentsListener interactionListener;
    private String categoryType;
    private TextView tvTableTitleType;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Category> elements;
    private RecyclerView tableRecyclerView;

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


         tvTableTitleType = view.findViewById(R.id.headTitleForTableCategory);
         tableRecyclerView = view.findViewById(R.id.recyclerTableCategory);


        initDataBaseRequiredElementsList(); // getRequiredElementsList used to fetch data required type list.

        initRecyclerViewForTableFragment(); // init the recyclerView of the required table.

        interactionListener.onFilterTableRecycler(recyclerViewAdapter);
       // tvTableTitleType.setOnClickListener(view1 -> tableCategoryClickListener.onFilterClick(recyclerViewAdapter));
    }

    private void initRecyclerViewForTableFragment() {
        AdsClickListener adsClickListener = (AdsClickListener) requireActivity();
        recyclerViewAdapter = new RecyclerViewAdapter(elements, requireActivity(), categoryType , MainActivity.textToSpeechManager , adsClickListener );

        tableRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tableRecyclerView.setHasFixedSize(true);
        tableRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initDataBaseRequiredElementsList() {
        DbAccess dbAccess = DbAccess.getInstance(requireActivity());
        dbAccess.open_to_read();
        elements = dbAccess.getRequiredElementsList(categoryType, tvTableTitleType);
        dbAccess.close();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof InteractionActivityFragmentsListener){
            interactionListener = (InteractionActivityFragmentsListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (interactionListener != null)
            interactionListener = null;

    }

}