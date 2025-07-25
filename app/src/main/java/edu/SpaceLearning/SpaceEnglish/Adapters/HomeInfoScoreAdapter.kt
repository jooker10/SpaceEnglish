/*
 * File: HomeInfoScoresRecyclerAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView adapter for displaying home information scores in SpaceEnglish app.
 *          This adapter binds data to ViewHolder and applies animations.
 */
package edu.SpaceLearning.SpaceEnglish.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.SpaceLearning.SpaceEnglish.Adapters.HomeInfoScoreAdapter.ScoreViewHolder
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.ScoreEntry

// Android imports

/**
 * RecyclerView adapter for displaying home information scores.
 */
class HomeInfoScoreAdapter
/**
 * Constructor to initialize the adapter with a list of ScoreEntry items.
 * @param scoreItems List of ScoreEntry items to display
 */(// List of ScoreEntry items to display
    private val scoreItems: ArrayList<ScoreEntry>
) :
    RecyclerView.Adapter<ScoreViewHolder>() {
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new InfoScoreRecyclerHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item_recycler_info_scores, parent, false)
        return ScoreViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val scoreItem = scoreItems[position]
        holder.bind(scoreItem)

        // Apply animation to the item view
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_home_info_scores
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the adapter.
     */
    override fun getItemCount(): Int {
        return scoreItems.size
    }

    /**
     * ViewHolder class for holding views of each item in the RecyclerView.
     */
    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitleLabel: TextView =
            itemView.findViewById(R.id.tvRecyclerHomeTitleLabel) // TextView for displaying title label
        private val tvScore: TextView =
            itemView.findViewById(R.id.tvRecyclerHomeScoreCounter) // TextView for displaying score
        private val divider: View =
            itemView.findViewById(R.id.dividerRecyclerColor) // View for displaying divider line

        /**
         * Bind method to bind data to views in the ViewHolder.
         * @param score The ScoreEntry item to bind.
         */
        fun bind(score: ScoreEntry) {
            tvTitleLabel.text = score.label
            tvScore.text = score.value

            // Customize appearance based on position
            if (adapterPosition == 0) {
                divider.setBackgroundColor(divider.context.getColor(R.color.custom_secondary))
                tvTitleLabel.setTextColor(tvTitleLabel.context.getColor(R.color.custom_secondary))
                tvScore.setTextColor(tvScore.context.getColor(R.color.custom_secondary))
            } else {
                divider.setBackgroundColor(divider.context.getColor(R.color.gray_400))
            }
        }
    }
}
