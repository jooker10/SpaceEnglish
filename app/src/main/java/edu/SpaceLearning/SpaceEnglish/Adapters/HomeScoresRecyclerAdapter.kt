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
import edu.SpaceLearning.SpaceEnglish.Adapters.HomeScoresRecyclerAdapter.InfoScoreVH
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.InfoScore

// Android imports

/**
 * RecyclerView adapter for displaying home information scores.
 */
class HomeScoresRecyclerAdapter
/**
 * Constructor to initialize the adapter with a list of InfoScore items.
 * @param items List of InfoScore items to display
 */(// List of InfoScore items to display
    private val items: ArrayList<InfoScore>
) :
    RecyclerView.Adapter<InfoScoreVH>() {
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new InfoScoreRecyclerHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoScoreVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item_recycler_info_scores, parent, false)
        return InfoScoreVH(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: InfoScoreVH, position: Int) {
        val infoScore = items[position]
        holder.bind(infoScore)

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
        return items.size
    }

    /**
     * ViewHolder class for holding views of each item in the RecyclerView.
     */
    inner class InfoScoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitleLabel: TextView =
            itemView.findViewById(R.id.tvRecyclerHomeTitleLabel) // TextView for displaying title label
        private val tvScore: TextView =
            itemView.findViewById(R.id.tvRecyclerHomeScoreCounter) // TextView for displaying score
        private val divider: View =
            itemView.findViewById(R.id.dividerRecyclerColor) // View for displaying divider line

        /**
         * Bind method to bind data to views in the ViewHolder.
         * @param item The InfoScore item to bind.
         */
        fun bind(item: InfoScore) {
            tvTitleLabel.text = item.titleLabel
            tvScore.text = item.scoreCounter

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
