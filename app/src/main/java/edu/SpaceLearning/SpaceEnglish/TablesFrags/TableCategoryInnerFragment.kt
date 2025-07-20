package edu.SpaceLearning.SpaceEnglish.TablesFrags

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerTableAdapter
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbManager
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.GeneratePdfFile
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity
import java.io.File

/**
 * Fragment to display a category-specific table using RecyclerView,
 * generate PDF files, and interact with the hosting activity.
 */
class TableCategoryInnerFragment
/**
 * Required empty public constructor for Fragment.
 */
    : Fragment() {
    private  var interactionListener: InteractionActivityFragmentsListener? = null
    private var categoryType: String = ""
    private lateinit var tvTableTitle: TextView
    private lateinit var recyclerTableAdapter: RecyclerTableAdapter
    private lateinit var mCategoryList: ArrayList<Category>
    private lateinit var recyclerViewTable: RecyclerView

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
        tvTableTitle = view.findViewById(R.id.headTitleForTableCategory)
        recyclerViewTable = view.findViewById(R.id.recyclerTableCategory)
        val btnTableSaveAsPDF = view.findViewById<Button>(R.id.btnDownLoadListPDF)

        // Initialize data required from database
        initDbRequiredCategoryTypeList()

        // Initialize RecyclerView to display table elements
        initRecyclerViewTableFragment()

        // Notify the hosting activity about the RecyclerView for potential interactions
        interactionListener?.onFilterTable(recyclerTableAdapter)

        // Handle PDF download button click
        btnTableSaveAsPDF.setOnClickListener { downloadPdfFile() }

    }

    private fun downloadPdfFile() {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "$categoryType table.pdf"
        val pdfFile = File(pdfPath, fileName)
        val generatePdfFile = GeneratePdfFile()

        generatePdfFile.generate("$categoryType table", pdfFile, mCategoryList) { success, file ->
            if (success && file != null && file.exists()) {
                // ✅ Only open PDF if it was actually saved
                interactionListener?.openPdfWithIntent(requireActivity(), file)
            } else {
                Toast.makeText(requireContext(), "Failed to generate PDF", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Initializes RecyclerView to display the table elements.
     * Sets up RecyclerViewAdapter with necessary parameters.
     */
    private fun initRecyclerViewTableFragment() {
        val adsClickListener = requireActivity() as AdsClickListener
        recyclerTableAdapter = RecyclerTableAdapter(
            mCategoryList, requireActivity(),
            categoryType, MainActivity.textToSpeechManager, adsClickListener
        )
        recyclerViewTable.also {
            it.layoutManager = LinearLayoutManager(requireActivity())
            it.setHasFixedSize(true)
            it.adapter = recyclerTableAdapter
        }

    }

    /**
     * Retrieves required elements from database based on category type.
     * Populates 'elements' ArrayList with fetched data.
     */
    private fun initDbRequiredCategoryTypeList() {
        DbManager.getInstance(requireActivity()).apply {
            this.openToRead()
            mCategoryList = this.getRequiredCategoryList(categoryType, tvTableTitle)
            this.close()
        }
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
           interactionListener = null
    }

    companion object {
        /**
         * Creates a new instance of TableCategoryPagerFragment with a specified category type.
         *
         * @param categoryType The type of category to display (e.g., "Verbs").
         * @return A new instance of TableCategoryPagerFragment.
         */
        fun getInstance(categoryType: String?): TableCategoryInnerFragment {
            val bundle = Bundle()
            bundle.putString(Constants.TAG_CATEGORY_TYPE, categoryType)
            val fragment = TableCategoryInnerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
