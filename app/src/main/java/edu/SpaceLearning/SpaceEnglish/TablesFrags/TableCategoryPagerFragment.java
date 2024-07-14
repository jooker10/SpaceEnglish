package edu.SpaceLearning.SpaceEnglish.TablesFrags;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.GeneratePdf;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;

/**
 * Fragment to display a category-specific table using RecyclerView,
 * generate PDF files, and interact with the hosting activity.
 */
public class TableCategoryPagerFragment extends Fragment {

    private InteractionActivityFragmentsListener interactionListener;
    private String categoryType;
    private TextView tvTableTitleType;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Category> elements;
    private RecyclerView tableRecyclerView;

    /**
     * Required empty public constructor for Fragment.
     */
    public TableCategoryPagerFragment() {
    }

    /**
     * Creates a new instance of TableCategoryPagerFragment with a specified category type.
     *
     * @param categoryType The type of category to display (e.g., "Verbs").
     * @return A new instance of TableCategoryPagerFragment.
     */
    public static TableCategoryPagerFragment getInstance(String categoryType) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType);
        TableCategoryPagerFragment fragment = new TableCategoryPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve category type from arguments, defaulting to "Verbs" if not provided
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryType = bundle.getString(Constants.TAG_CATEGORY_TYPE, "Verbs");
        }
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

        // Initialize views and set up RecyclerView
        tvTableTitleType = view.findViewById(R.id.headTitleForTableCategory);
        tableRecyclerView = view.findViewById(R.id.recyclerTableCategory);
        Button btnTableSaveAsPDF = view.findViewById(R.id.btnDownLoadListPDF);

        // Initialize data required from database
        initDataBaseRequiredElementsList();

        // Initialize RecyclerView to display table elements
        initRecyclerViewForTableFragment();

        // Notify the hosting activity about the RecyclerView for potential interactions
        interactionListener.onFilterTableRecycler(recyclerViewAdapter);

        // Handle PDF download button click
        btnTableSaveAsPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate PDF file with table data and open it with appropriate intent
                File pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                String fileName = categoryType + " table.pdf";
                File pdfFile = new File(pdfPath, fileName);
                GeneratePdf generateFilePDF = new GeneratePdf();
                generateFilePDF.generateTheFile(categoryType + " table", pdfFile, elements);
                interactionListener.openPdfWithIntent(requireActivity(), pdfFile);
            }
        });
    }

    /**
     * Initializes RecyclerView to display the table elements.
     * Sets up RecyclerViewAdapter with necessary parameters.
     */
    private void initRecyclerViewForTableFragment() {
        AdsClickListener adsClickListener = (AdsClickListener) requireActivity();
        recyclerViewAdapter = new RecyclerViewAdapter(elements, requireActivity(), categoryType, MainActivity.textToSpeechManager, adsClickListener);

        tableRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tableRecyclerView.setHasFixedSize(true);
        tableRecyclerView.setAdapter(recyclerViewAdapter);
    }

    /**
     * Retrieves required elements from database based on category type.
     * Populates 'elements' ArrayList with fetched data.
     */
    private void initDataBaseRequiredElementsList() {
        DbAccess dbAccess = DbAccess.getInstance(requireActivity());
        dbAccess.open_to_read();
        elements = dbAccess.getRequiredElementsList(categoryType, tvTableTitleType);
        dbAccess.close();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Ensure the hosting activity implements InteractionActivityFragmentsListener
        if (context instanceof InteractionActivityFragmentsListener) {
            interactionListener = (InteractionActivityFragmentsListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clean up interactionListener to avoid memory leaks
        interactionListener = null;
    }
}
