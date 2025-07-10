/*
 * File: RecyclerQuizNavAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView Adapter for quiz navigation items.
 */

package edu.SpaceLearning.SpaceEnglish.Adapters;

// Android imports
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Project-specific imports
import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.Listeners.RecyclerItemQuizListener;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.ItemButtonsRecycler;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores;

/**
 * RecyclerView Adapter for quiz navigation items.
 */
public class RecyclerQuizNavAdapter extends RecyclerView.Adapter<RecyclerQuizNavAdapter.RecyclerQuizNavHolder> {

    private final ArrayList<ItemButtonsRecycler> items; // List of items to display in the RecyclerView
    private final RecyclerItemQuizListener recyclerItemQuizListener; // Listener for item click events

    /**
     * Constructor to initialize the adapter with a list of items and a listener for item click events.
     * @param items List of items to display in the RecyclerView.
     * @param recyclerItemQuizListener Listener for item click events.
     */
    public RecyclerQuizNavAdapter(ArrayList<ItemButtonsRecycler> items, RecyclerItemQuizListener recyclerItemQuizListener) {
        this.items = items;
        this.recyclerItemQuizListener = recyclerItemQuizListener;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public RecyclerQuizNavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_quiz_nav, parent, false);
        return new RecyclerQuizNavHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerQuizNavHolder holder, int position) {
        ItemButtonsRecycler item = items.get(position);
        holder.bind(item, position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_home_info_scores));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the RecyclerView.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
    class RecyclerQuizNavHolder extends RecyclerView.ViewHolder {

        private final Button btnNav; // Button for quiz navigation
        private final TextView tvRequiredPoints; // TextView displaying required points

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */
        public RecyclerQuizNavHolder(@NonNull View itemView) {
            super(itemView);
            btnNav = itemView.findViewById(R.id.btnRecyclerNavQuiz);
            tvRequiredPoints = itemView.findViewById(R.id.tvRecyclerNavQuiz);

            // Set click listener for the button
            btnNav.setOnClickListener(v -> recyclerItemQuizListener.onQuizItemClicked(getAdapterPosition()));
        }

        /**
         * Binds data to the views in the ViewHolder.
         * @param item The item data to bind.
         * @param position The position of the item in the RecyclerView.
         */
        void bind(ItemButtonsRecycler item, int position) {
            btnNav.setText(item.getBtnText()); // Set button text
            tvRequiredPoints.setText(item.getTvPointsRequired()); // Set required points text
            tvRequiredPoints.setTextColor(Color.RED); // Set text color

            // Enable/disable button based on score permission
            if (position > 1) {
                enableButton(btnNav, tvRequiredPoints, Scores.totalScore, Constants.permissionCategoryScoreArray[position - 2]);
            } else {
                tvRequiredPoints.setVisibility(View.GONE); // Hide required points for initial items
            }
        }
    }

    /**
     * Enables or disables a button based on the global score and permission score.
     * @param button The button to enable or disable.
     * @param tvRequiredLabel The TextView displaying required points.
     * @param globalMainScore The global main score.
     * @param permissionScore The permission score required for the button.
     */
    private void enableButton(Button button, TextView tvRequiredLabel, int globalMainScore, int permissionScore) {
        if (globalMainScore >= permissionScore) {
            button.setEnabled(true);
            tvRequiredLabel.setVisibility(View.GONE); // Hide required points when enabled
        } else {
            button.setEnabled(false);
            tvRequiredLabel.setVisibility(View.VISIBLE); // Show required points when disabled
        }
    }
}
