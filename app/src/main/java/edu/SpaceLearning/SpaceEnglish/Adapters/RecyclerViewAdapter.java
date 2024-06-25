/*
 * File: RecyclerViewAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView Adapter for displaying categories with filtering and animation effects.
 */

package edu.SpaceLearning.SpaceEnglish.Adapters;

// Android imports
import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Project-specific imports
import java.util.ArrayList;
import java.util.List;

import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Category;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

/**
 * RecyclerView Adapter for displaying categories with filtering and animation effects.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CategoryRecyclerHolder> implements Filterable {

    private List<Category> originalElements; // Original list of elements
    private List<Category> filteredElements; // Filtered list of elements
    private final Context context; // Context reference
    private final Activity activity; // Activity reference
    private final String categoryType; // Category type identifier
    private final TextToSpeechManager textToSpeechManager; // Text to speech manager
    private final AdsClickListener adsClickListener; // Listener for ad clicks
    private boolean isAdsShowed = false; // Flag to track if ads have been shown

    /**
     * Constructor to initialize the RecyclerView adapter.
     * @param originalElements List of original elements to display.
     * @param context Context of the application/activity.
     * @param categoryType Type of category being displayed.
     * @param textToSpeechManager Text to speech manager instance.
     * @param adsClickListener Listener for ad clicks.
     */
    public RecyclerViewAdapter(List<Category> originalElements, Context context, String categoryType, TextToSpeechManager textToSpeechManager, AdsClickListener adsClickListener) {
        this.originalElements = originalElements;
        this.context = context;
        this.categoryType = categoryType;
        this.activity = (Activity) context;
        this.textToSpeechManager = textToSpeechManager;
        this.filteredElements = new ArrayList<>(originalElements); // Initialize filtered list with original data
        this.adsClickListener = adsClickListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CategoryRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_holder_table, parent, false);
        return new CategoryRecyclerHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerHolder holder, int position) {
        holder.bind(filteredElements.get(position)); // Bind data to ViewHolder
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_animation_table));

        // Show ad on specific position if not already shown
        if (position == 10 && !isAdsShowed) {
            adsClickListener.onShowInterstitialAd();
            isAdsShowed = true;
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the RecyclerView.
     */
    @Override
    public int getItemCount() {
        return filteredElements.size();
    }

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     * @return A filter used to constrain data.
     */
    @Override
    public Filter getFilter() {
        return myFilter;
    }

    /**
     * Custom filter implementation for filtering category data.
     */
    private final Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredResults = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredResults.addAll(originalElements); // No filter, add all original elements
            } else {
                String filterText = constraint.toString().toLowerCase().trim();
                for (Category category : originalElements) {
                    // Filter by native language text or English category name
                    if (filterTxtLanguageNative(category).toLowerCase().contains(filterText) || category.getCategoryEng().toLowerCase().contains(filterText)) {
                        filteredResults.add(category);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredResults;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredElements.clear();
            filteredElements.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
    class CategoryRecyclerHolder extends RecyclerView.ViewHolder {

        private Category category; // Current category object
        private final TextView holderVerbId; // TextView for category ID
        private final TextView holderVerbEnglish; // TextView for English category name
        private final TextView holderVerbNativeLang; // TextView for native language category name
        private final TextView tvExpandedExamples; // TextView for expanded examples
        private final LinearLayout linearLayoutExpanded; // LinearLayout for expanded view
        private final ImageView imgSongs; // ImageView for songs icon
        private final Button btnExampleExpanded; // Button to expand examples
        private final TextView tvFlagNativeLang; // TextView for native language flag

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */
        public CategoryRecyclerHolder(View itemView) {
            super(itemView);
            holderVerbId = itemView.findViewById(R.id.holderVerbId);
            holderVerbEnglish = itemView.findViewById(R.id.holderVerbEnglish);
            holderVerbNativeLang = itemView.findViewById(R.id.holderVerbNativeLang);
            tvExpandedExamples = itemView.findViewById(R.id.tvExpandedExamples);
            linearLayoutExpanded = itemView.findViewById(R.id.linearLayoutExpanded);
            imgSongs = itemView.findViewById(R.id.imgSongs);
            btnExampleExpanded = itemView.findViewById(R.id.btnExampleExpanded);
            tvFlagNativeLang = itemView.findViewById(R.id.tvFlagNativeLang);

            // Enable layout transition for smooth appearance changes
            linearLayoutExpanded.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGE_APPEARING);
        }

        /**
         * Helper method to capitalize the first letter of a string.
         * @param text The input string.
         * @return The input string with the first letter capitalized.
         */
        private String capitalizeFirstLetter(String text) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }

        /**
         * Binds data to views in the ViewHolder.
         * @param category The category object to bind.
         */
        void bind(Category category) {
            this.category = category;
            holderVerbId.setText(String.valueOf(category.getCategoryID() + 1)); // Set category ID
            holderVerbEnglish.setText(capitalizeFirstLetter(category.getCategoryEng())); // Set English category name
            holderVerbNativeLang.setText(capitalizeFirstLetter(ChoosingNativeLang(category, tvFlagNativeLang))); // Set native language category name

            tvExpandedExamples.setText(category.getCategoryExamples()); // Set examples text
            imgSongs.setOnClickListener(v -> {
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100).withEndAction(() -> v.animate().scaleX(1f).scaleY(1f).setDuration(100).start());
                String txt = category.getCategoryEng();
                if (textToSpeechManager != null) {
                    textToSpeechManager.speak(txt); // Speak English category name
                }
                // Additional functionality for speech, if any
            });

            tvExpandedExamples.setVisibility(View.GONE); // Initially hide expanded examples

            // Show/hide expanded examples on button click
            if (!categoryType.equals(Constants.SENTENCE_NAME) && !categoryType.equals(Constants.IDIOM_NAME)) {
                btnExampleExpanded.setOnClickListener(view -> {
                    if (tvExpandedExamples.getVisibility() == View.GONE) {
                        tvExpandedExamples.setVisibility(View.VISIBLE); // Show expanded examples
                    } else {
                        tvExpandedExamples.setVisibility(View.GONE); // Hide expanded examples
                    }
                    TransitionManager.beginDelayedTransition(linearLayoutExpanded, new AutoTransition());
                });
            } else {
                btnExampleExpanded.setVisibility(View.GONE); // Hide button for certain category types
            }
        }
    }

    /**
     * Helper method to choose native language text based on application settings.
     * @param element The category element.
     * @param tvLangFlag The TextView for language flag.
     * @return The native language text.
     */
    private String ChoosingNativeLang(Category element, TextView tvLangFlag) {
        switch (Utils.nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_SPANISH:
                tvLangFlag.setText("Sp");
                return element.getCategorySp();
            case Constants.LANGUAGE_NATIVE_ARABIC:
                tvLangFlag.setText("Ar");
                return element.getCategoryAr();
            default:
                tvLangFlag.setText("Fr");
                return element.getCategoryFr();
        }
    }

    /**
     * Helper method to get the filtered text based on native language setting.
     * @param category The category element.
     * @return The filtered text based on native language.
     */
    private String filterTxtLanguageNative(Category category) {
        switch (Utils.nativeLanguage) {
            case Constants.LANGUAGE_NATIVE_SPANISH:
                return category.getCategorySp();
            case Constants.LANGUAGE_NATIVE_ARABIC:
                return category.getCategoryAr();
            default:
                return category.getCategoryFr();
        }
    }
}
