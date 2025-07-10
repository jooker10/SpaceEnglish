package edu.SpaceLearning.SpaceEnglish.TablesFrags

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess.Companion.getInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.GeneratePDFFile
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import java.io.File

/**
 * Fragment to display a category-specific table using RecyclerView,
 * generate PDF files, and interact with the hosting activity.
 */
class TableCategoryPagerFragment
/**
 * Required empty public constructor for Fragment.
 */
    : Fragment() {
    private lateinit var interactionListener: InteractionActivityFragmentsListener
    private var categoryType: String = ""
    private lateinit var tvTableTitleType: TextView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var elements: ArrayList<Category>
    private lateinit var tableRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve category type from arguments, defaulting to "Verbs" if not provided
        val bundle = arguments
        if (bundle != null) {
            categoryType = bundle.getString(Constants.TAG_CATEGORY_TYPE, "Verbs")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tables_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views and set up RecyclerView
        tvTableTitleType = view.findViewById(R.id.headTitleForTableCategory)
        tableRecyclerView = view.findViewById(R.id.recyclerTableCategory)
        val btnTableSaveAsPDF = view.findViewById<Button>(R.id.btnDownLoadListPDF)

        // Initialize data required from database
        initDataBaseRequiredElementsList()

        // Initialize RecyclerView to display table elements
        initRecyclerViewForTableFragment()

        // Notify the hosting activity about the RecyclerView for potential interactions
     //   interactionListener!!.onFilterTableRecycler(recyclerViewAdapter)

        // Handle PDF download button click
        btnTableSaveAsPDF.setOnClickListener(View.OnClickListener { // Generate PDF file with table data and open it with appropriate intent
            val pdfPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val fileName = "$categoryType table.pdf"
            val pdfFile = File(pdfPath, fileName)
            val generateFilePDF = GeneratePDFFile()
            generateFilePDF.generate("$categoryType table", pdfFile, elements!!)
           // interactionListener!!.openPdfWithIntent(requireActivity(), pdfFile)
        })
    }

    /**
     * Initializes RecyclerView to display the table elements.
     * Sets up RecyclerViewAdapter with necessary parameters.
     */
    private fun initRecyclerViewForTableFragment() {
        val adsClickListener = requireActivity() as AdsClickListener
        recyclerViewAdapter = RecyclerViewAdapter(
            elements!!, requireActivity(),
            categoryType!!, MainActivity.textToSpeechManager, adsClickListener
        )

        tableRecyclerView!!.layoutManager = LinearLayoutManager(requireActivity())
        tableRecyclerView!!.setHasFixedSize(true)
        tableRecyclerView!!.adapter = recyclerViewAdapter
    }

    /**
     * Retrieves required elements from database based on category type.
     * Populates 'elements' ArrayList with fetched data.
     */
    private fun initDataBaseRequiredElementsList() {
        val dbAccess = getInstance(requireActivity())
        dbAccess.openToRead()
        elements = dbAccess.getRequiredElementsList(categoryType!!, tvTableTitleType!!)
        dbAccess.close()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Ensure the hosting activity implements InteractionActivityFragmentsListener
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // Clean up interactionListener to avoid memory leaks
       // interactionListener = null
    }

    companion object {
        /**
         * Creates a new instance of TableCategoryPagerFragment with a specified category type.
         *
         * @param categoryType The type of category to display (e.g., "Verbs").
         * @return A new instance of TableCategoryPagerFragment.
         */
        fun getInstance(categoryType: String?): TableCategoryPagerFragment {
            val bundle = Bundle()
            bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType)
            val fragment = TableCategoryPagerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
