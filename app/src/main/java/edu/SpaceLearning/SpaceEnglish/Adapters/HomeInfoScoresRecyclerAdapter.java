/*
 * File: HomeInfoScoresRecyclerAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView adapter for displaying home information scores in SpaceEnglish app.
 *          This adapter binds data to ViewHolder and applies animations.
 */

package edu.SpaceLearning.SpaceEnglish.Adapters;

// Android imports
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.InfoScore;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

/**
 * RecyclerView adapter for displaying home information scores.
 */
public class HomeInfoScoresRecyclerAdapter extends RecyclerView.Adapter<HomeInfoScoresRecyclerAdapter.InfoScoreRecyclerHolder> {

    private final ArrayList<InfoScore> items; // List of InfoScore items to display

    /**
     * Constructor to initialize the adapter with a list of InfoScore items.
     * @param items List of InfoScore items to display
     */
    public HomeInfoScoresRecyclerAdapter(ArrayList<InfoScore> items) {
        this.items = items;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new InfoScoreRecyclerHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public InfoScoreRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_recycler_info_scores, parent, false);
        return new InfoScoreRecyclerHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull InfoScoreRecyclerHolder holder, int position) {
        InfoScore infoScore = items.get(position);
        holder.bind(infoScore, position);

        // Apply animation to the item view
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_home_info_scores));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder class for holding views of each item in the RecyclerView.
     */
    class InfoScoreRecyclerHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitleLabel; // TextView for displaying title label
        private final TextView tvScore; // TextView for displaying score
        private final View divider; // View for displaying divider line

        /**
         * Constructor to initialize ViewHolder with item view.
         * @param itemView The item view for the ViewHolder.
         */
        public InfoScoreRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleLabel = itemView.findViewById(R.id.tvRecyclerHomeTitleLabel);
            tvScore = itemView.findViewById(R.id.tvRecyclerHomeScoreCounter);
            divider = itemView.findViewById(R.id.dividerRecyclerColor);
        }

        /**
         * Bind method to bind data to views in the ViewHolder.
         * @param item The InfoScore item to bind.
         * @param position The position of the item in the RecyclerView.
         */
        void bind(InfoScore item, int position) {
            tvTitleLabel.setText(item.getTitleLabel());
            tvScore.setText(item.getScoreCounter());

            // Customize appearance based on position
            if (position == 0) {
                divider.setBackgroundColor(divider.getContext().getColor(R.color.custom_secondary));
                tvTitleLabel.setTextColor(tvTitleLabel.getContext().getColor(R.color.custom_secondary));
                tvScore.setTextColor(tvScore.getContext().getColor(R.color.custom_secondary));
            } else {
                divider.setBackgroundColor(divider.getContext().getColor(R.color.gray_400));
            }
        }
    }
}
