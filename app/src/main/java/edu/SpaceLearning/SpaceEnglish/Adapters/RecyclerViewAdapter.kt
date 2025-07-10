/*
 * File: RecyclerViewAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView Adapter for displaying categories with filtering and animation effects.
 */
package edu.SpaceLearning.SpaceEnglish.Adapters

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter.CategoryRecyclerHolder
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import java.util.Locale

// Android imports

// Project-specific imports
/**
 * RecyclerView Adapter for displaying categories with filtering and animation effects.
 */
class RecyclerViewAdapter(// Original list of elements
    private var originalElements: List<Category>,
    context: Context,
    categoryType: String,
    textToSpeechManager: TextToSpeechManager?,
    adsClickListener: AdsClickListener
) :
    RecyclerView.Adapter<CategoryRecyclerHolder>(), Filterable {
    private val filteredElements: MutableList<Category> // Filtered list of elements
    private val context: Context // Context reference
    private val activity: Activity // Activity reference
    private val categoryType: String // Category type identifier
    private val textToSpeechManager: TextToSpeechManager? // Text to speech manager
    private val adsClickListener: AdsClickListener // Listener for ad clicks
    private var isAdsShowed = false // Flag to track if ads have been shown

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_holder_table, parent, false)
        return CategoryRecyclerHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: CategoryRecyclerHolder, position: Int) {
        holder.bind(filteredElements[position]) // Bind data to ViewHolder
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_animation_table
        )

        // Show ad on specific position if not already shown
        if (position == 10 && !isAdsShowed) {
            adsClickListener.onShowInterstitialAd()
            isAdsShowed = true
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the RecyclerView.
     */
    override fun getItemCount(): Int {
        return filteredElements.size
    }

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     * @return A filter used to constrain data.
     */
   /* override fun getFilter(): Filter {
        return myFilter
    }*/

    /**
     * Custom filter implementation for filtering category data.
     */
    /*private val myFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredResults: MutableList<Category> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredResults.addAll(originalElements) // No filter, add all original elements
            } else {
                val filterText =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (category in originalElements) {
                    // Filter by native language text or English category name
                    if (filterTxtLanguageNative(category).lowercase(Locale.getDefault())
                            .contains(filterText) || category.categoryEng.lowercase(
                            Locale.getDefault()
                        ).contains(filterText)
                    ) {
                        filteredResults.add(category)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredResults
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredElements.clear()
            filteredElements.addAll(results.values as List<*>)
            notifyDataSetChanged()
        }
    }*/

    /**
     * Constructor to initialize the RecyclerView adapter.
     * @param originalElements List of original elements to display.
     * @param context Context of the application/activity.
     * @param categoryType Type of category being displayed.
     * @param textToSpeechManager Text to speech manager instance.
     * @param adsClickListener Listener for ad clicks.
     */
    init {
        this.originalElements = originalElements
        this.context = context
        this.categoryType = categoryType
        this.activity = context as Activity
        this.textToSpeechManager = textToSpeechManager
        this.filteredElements = ArrayList(
            originalElements
        ) // Initialize filtered list with original data
        this.adsClickListener = adsClickListener
    }

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
    inner class CategoryRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var category: Category? = null // Current category object
        private val holderVerbId: TextView =
            itemView.findViewById(R.id.holderVerbId) // TextView for category ID
        private val holderVerbEnglish: TextView =
            itemView.findViewById(R.id.holderVerbEnglish) // TextView for English category name
        private val holderVerbNativeLang: TextView =
            itemView.findViewById(R.id.holderVerbNativeLang) // TextView for native language category name
        private val tvExpandedExamples: TextView =
            itemView.findViewById(R.id.tvExpandedExamples) // TextView for expanded examples
        private val linearLayoutExpanded: LinearLayout =
            itemView.findViewById(R.id.linearLayoutExpanded) // LinearLayout for expanded view
        private val imgSongs: ImageView =
            itemView.findViewById(R.id.imgSongs) // ImageView for songs icon
        private val btnExampleExpanded: Button =
            itemView.findViewById(R.id.btnExampleExpanded) // Button to expand examples
        private val tvFlagNativeLang: TextView =
            itemView.findViewById(R.id.tvFlagNativeLang) // TextView for native language flag

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */
        init {
            // Enable layout transition for smooth appearance changes
            linearLayoutExpanded.layoutTransition.enableTransitionType(LayoutTransition.CHANGE_APPEARING)
        }

        /**
         * Helper method to capitalize the first letter of a string.
         * @param text The input string.
         * @return The input string with the first letter capitalized.
         */
        private fun capitalizeFirstLetter(text: String): String {
            return text.substring(0, 1).uppercase(Locale.getDefault()) + text.substring(1)
        }

        /**
         * Binds data to views in the ViewHolder.
         * @param category The category object to bind.
         */
        fun bind(category: Category) {
            this.category = category
            holderVerbId.text = (category.categoryID + 1).toString() // Set category ID
            holderVerbEnglish.text =
                capitalizeFirstLetter(category.categoryEng) // Set English category name
            holderVerbNativeLang.text =
                capitalizeFirstLetter(
                    ChoosingNativeLang(
                        category,
                        tvFlagNativeLang
                    )
                ) // Set native language category name

            tvExpandedExamples.text = category.categoryExamples // Set examples text
            imgSongs.setOnClickListener { v: View ->
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
                val txt = category.categoryEng
                textToSpeechManager?.speak(txt)
            }

            tvExpandedExamples.visibility = View.GONE // Initially hide expanded examples

            // Show/hide expanded examples on button click
            if (categoryType != Constants.SENTENCE_NAME && categoryType != Constants.IDIOM_NAME) {
                btnExampleExpanded.setOnClickListener { view: View? ->
                    if (tvExpandedExamples.visibility == View.GONE) {
                        tvExpandedExamples.visibility =
                            View.VISIBLE // Show expanded examples
                    } else {
                        tvExpandedExamples.visibility =
                            View.GONE // Hide expanded examples
                    }
                    TransitionManager.beginDelayedTransition(
                        linearLayoutExpanded,
                        AutoTransition()
                    )
                }
            } else {
                btnExampleExpanded.visibility = View.GONE // Hide button for certain category types
            }
        }
    }

    /**
     * Helper method to choose native language text based on application settings.
     * @param element The category element.
     * @param tvLangFlag The TextView for language flag.
     * @return The native language text.
     */
    private fun ChoosingNativeLang(element: Category, tvLangFlag: TextView): String {
        when (Utils.nativeLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> {
                tvLangFlag.text = "Sp"
                return element.categorySp
            }

            Constants.LANGUAGE_NATIVE_ARABIC -> {
                tvLangFlag.text = "Ar"
                return element.categoryAr
            }

            else -> {
                tvLangFlag.text = "Fr"
                return element.categoryFr
            }
        }
    }

    /**
     * Helper method to get the filtered text based on native language setting.
     * @param category The category element.
     * @return The filtered text based on native language.
     */
    private fun filterTxtLanguageNative(category: Category): String {
        return when (Utils.nativeLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> category.categorySp
            Constants.LANGUAGE_NATIVE_ARABIC -> category.categoryAr
            else -> category.categoryFr
        }
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}
