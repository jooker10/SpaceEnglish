/*
 * File: RecyclerQuizNavAdapter.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: RecyclerView Adapter for quiz navigation items.
 */
package edu.SpaceLearning.SpaceEnglish.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerQuizNavAdapter.RecyclerQuizNavHolder
import edu.SpaceLearning.SpaceEnglish.Listeners.RecyclerItemQuizListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.ItemButtonsRecycler
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores

// Android imports

// Project-specific imports
/**
 * RecyclerView Adapter for quiz navigation items.
 */
class RecyclerQuizNavAdapter(// List of items to display in the RecyclerView
    private val items: ArrayList<ItemButtonsRecycler>, // Listener for item click events
    private var recyclerItemQuizListener: RecyclerItemQuizListener
) :
    RecyclerView.Adapter<RecyclerQuizNavHolder>() {
    /**
     * Constructor to initialize the adapter with a list of items and a listener for item click events.
     * @param items List of items to display in the RecyclerView.
     * @param recyclerItemQuizListener Listener for item click events.
     */
    init {
        this.recyclerItemQuizListener = recyclerItemQuizListener
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerQuizNavHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_quiz_nav, parent, false)
        return RecyclerQuizNavHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: RecyclerQuizNavHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.recycler_home_info_scores
        )
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in the RecyclerView.
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
    inner class RecyclerQuizNavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnNav: Button =
            itemView.findViewById(R.id.btnRecyclerNavQuiz) // Button for quiz navigation
        private val tvRequiredPoints: TextView =
            itemView.findViewById(R.id.tvRecyclerNavQuiz) // TextView displaying required points

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */
        init {
            // Set click listener for the button
            btnNav.setOnClickListener { v: View? ->
                recyclerItemQuizListener.onQuizItemClicked(
                    adapterPosition
                )
            }
        }

        /**
         * Binds data to the views in the ViewHolder.
         * @param item The item data to bind.
         * @param position The position of the item in the RecyclerView.
         */
        fun bind(item: ItemButtonsRecycler, position: Int) {
            btnNav.text = item.btnText // Set button text
            tvRequiredPoints.text = item.tvPointsRequired // Set required points text
            tvRequiredPoints.setTextColor(Color.RED) // Set text color

            // Enable/disable button based on score permission
            if (position > 1) {
                enableButton(
                    btnNav, tvRequiredPoints, Scores.totalScore,
                    Constants.permissionCategoryScoreArray[position - 2]
                )
            } else {
                tvRequiredPoints.visibility = View.GONE // Hide required points for initial items
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
    private fun enableButton(
        button: Button,
        tvRequiredLabel: TextView,
        globalMainScore: Int,
        permissionScore: Int
    ) {
        if (globalMainScore >= permissionScore) {
            button.isEnabled = true
            tvRequiredLabel.visibility = View.GONE // Hide required points when enabled
        } else {
            button.isEnabled = false
            tvRequiredLabel.visibility = View.VISIBLE // Show required points when disabled
        }
    }
}
