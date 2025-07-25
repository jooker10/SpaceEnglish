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
import edu.SpaceLearning.SpaceEnglish.Adapters.CategoryAdapter.CategoryViewHolder
import edu.SpaceLearning.SpaceEnglish.Listeners.AdEventListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.QuizUtils
import java.util.Locale
import androidx.core.view.isGone
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.CategoryFilter

// Android imports

// Project-specific imports

/**
 * RecyclerView Adapter for displaying categories with filtering and animation effects.
 */
class CategoryAdapter(
    private var originalCategories: List<Category>,
    context: Context,
    categoryType: String,
    textToSpeechHelper: TextToSpeechManager?,
    adEventListener: AdEventListener
) :
    RecyclerView.Adapter<CategoryViewHolder>() , Filterable { 
    private var categoryFilter : CategoryFilter? = null
    private val filteredCategories: MutableList<Category> // Filtered list of elements
    private val context: Context // Context reference
    private val activity: Activity // Activity reference
    private val categoryType: String // Category type identifier
    private val textToSpeechManager: TextToSpeechManager? // Text to speech manager
    private val adEventListener: AdEventListener // TimerListener for ad clicks
    private var isAdShown = false // Flag to track if ads have been shown
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_holder_table, parent, false)
        return CategoryViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(filteredCategories[position]) // Bind data to ViewHolder
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_animation_table
        )

        // Show ad on specific position if not already shown
        if (position == 10 && !isAdShown) {
            adEventListener.showInterstitialAd()
            isAdShown = true
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the RecyclerView.
     */
    override fun getItemCount(): Int {
        return filteredCategories.size
    }

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     * @return A filter used to constrain data.
     */



    /**
     * Constructor to initialize the RecyclerView adapter.
     * @param originalCategories List of original elements to display.
     * @param context Context of the application/activity.
     * @param categoryType Type of category being displayed.
     * @param textToSpeechHelper Text to speech manager instance.
     * @param adEventListener TimerListener for ad clicks.
     */
    init {
        this.originalCategories = originalCategories
        this.context = context
        this.categoryType = categoryType
        this.activity = context as Activity
        this.textToSpeechManager = textToSpeechHelper
        this.filteredCategories = ArrayList(
            originalCategories
        ) // Initialize filtered list with original data
        this.adEventListener = adEventListener
    }

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var category: Category? = null // Current category object
        private val tvCategoryId: TextView =
            itemView.findViewById(R.id.tvCategoryId) // TextView for category ID
        private val tvEnglishName: TextView =
            itemView.findViewById(R.id.tvEnglishName) // TextView for English category name
        private val tvTranslatedName: TextView =
            itemView.findViewById(R.id.tvTranslatedName) // TextView for native language category name
        private val tvExample: TextView =
            itemView.findViewById(R.id.tvExample) // TextView for expanded examples
        private val layoutExpandedSection: LinearLayout =
            itemView.findViewById(R.id.layoutExpandedSection) // LinearLayout for expanded view
        private val imgSpeaker: ImageView =
            itemView.findViewById(R.id.imgSpeaker) // ImageView for songs icon
        private val btnToggleExample: Button =
            itemView.findViewById(R.id.btnToggleExample) // Button to expand examples
        private val tvLangFlag: TextView =
            itemView.findViewById(R.id.tvLangFlag) // TextView for native language flag

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */
        init {
            // Enable layout transition for smooth appearance changes
            layoutExpandedSection.layoutTransition.enableTransitionType(LayoutTransition.CHANGE_APPEARING)
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
            tvCategoryId.text = (category.id + 1).toString() // Set category ID
            tvEnglishName.text =
                capitalizeFirstLetter(category.englishName) // Set English category name
            tvTranslatedName.text =
                capitalizeFirstLetter(
                    ChoosingNativeLang(
                        category,
                        tvLangFlag
                    )
                ) // Set native language category name

            tvExample.text = category.exampleSentence // Set examples text
            imgSpeaker.setOnClickListener { v: View ->
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
                val txt = category.englishName
                textToSpeechManager?.speak(txt)
            }

            tvExample.visibility = View.GONE // Initially hide expanded examples

            // Show/hide expanded examples on button click
            if (categoryType != Constants.CATEGORY_NAME_SENTENCE && categoryType != Constants.CATEGORY_NAME_IDIOMS) {
                btnToggleExample.setOnClickListener { view: View? ->
                    if (tvExample.isGone) {
                        tvExample.visibility =
                            View.VISIBLE // Show expanded examples
                    } else {
                        tvExample.visibility =
                            View.GONE // Hide expanded examples
                    }
                    TransitionManager.beginDelayedTransition(
                        layoutExpandedSection,
                        AutoTransition()
                    )
                }
            } else {
                btnToggleExample.visibility = View.GONE // Hide button for certain category types
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
        when (QuizUtils.translationLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> {
                tvLangFlag.text = context.getString(R.string.label_flag_sp)
                return element.spanishName
            }

            Constants.LANGUAGE_NATIVE_ARABIC -> {
                tvLangFlag.text = context.getString(R.string.label_flag_ar)
                return element.arabicName
            }

            else -> {
                tvLangFlag.text = context.getString(R.string.label_flag_fr)
                return element.frenchName
            }
        }
    }

    /**
     * Helper method to get the filtered text based on native language setting.
     * @param category The category element.
     * @return The filtered text based on native language.
     */
    private fun filterTxtLanguageNative(category: Category): String {
        return when (QuizUtils.translationLanguage) {
            Constants.LANGUAGE_NATIVE_SPANISH -> category.spanishName
            Constants.LANGUAGE_NATIVE_ARABIC -> category.arabicName
            else -> category.frenchName
        }
    }

   /* override fun getFilter(): Filter {
        return myFilter
    }
*/
   override fun getFilter(): Filter {
       if (categoryFilter == null) {
           categoryFilter = CategoryFilter(originalCategories) { filtered ->
               filteredCategories.clear()
               filteredCategories.addAll(filtered)
               notifyDataSetChanged()
           }
       }
       return categoryFilter!!
   }
}
