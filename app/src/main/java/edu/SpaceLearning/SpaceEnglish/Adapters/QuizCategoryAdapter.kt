/*
 * File: QuizCategoryAdapter.java
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
import edu.SpaceLearning.SpaceEnglish.Adapters.QuizCategoryAdapter.QuizCategoryViewHolder
import edu.SpaceLearning.SpaceEnglish.Listeners.QuizNavItemClickListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.ButtonItem
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Scores

// Android imports

// Project-specific imports
/**
 * RecyclerView Adapter for quiz navigation items.
 */
class QuizCategoryAdapter(// List of items to display in the RecyclerView
    private val categoryItems: ArrayList<ButtonItem>, // TimerListener for item click events
    private var quizItemClickListener: QuizNavItemClickListener
) :
    RecyclerView.Adapter<QuizCategoryViewHolder>() {
    /**
     * Constructor to initialize the adapter with a list of items and a listener for item click events.
     * @param categoryItems List of items to display in the RecyclerView.
     * @param quizItemClickListener TimerListener for item click events.
     */

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_quiz_nav, parent, false)
        return QuizCategoryViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: QuizCategoryViewHolder, position: Int) {
        val currentItem = categoryItems[position]
        holder.bind(currentItem, position)
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
        return categoryItems.size
    }

    /**
     * ViewHolder class to hold references to views for each item in the RecyclerView.
     */
   inner  class QuizCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnCategory: Button =
            itemView.findViewById(R.id.btnCategory) // Button for quiz navigation
        private val tvRequiredPoints: TextView =
            itemView.findViewById(R.id.tvRequiredPoints) // TextView displaying required points

        /**
         * Constructor to initialize views in the ViewHolder.
         * @param itemView The root view of the item layout.
         */


        /**
         * Binds data to the views in the ViewHolder.
         * @param item The item data to bind.
         * @param position The position of the item in the RecyclerView.
         */
        fun bind(item: ButtonItem, position: Int) {
            btnCategory.text = item.label // Set button text
            tvRequiredPoints.text = item.requiredPointsText // Set required points text
            tvRequiredPoints.setTextColor(Color.RED) // Set text color
            btnCategory.setOnClickListener{
                quizItemClickListener.onQuizNavItemClicked(position)
            }
            // Enable/disable button based on score permission
            if (position > 1) {
                evaluateButtonState(
                    btnCategory, tvRequiredPoints, Scores.totalScore,
                    Constants.CATEGORY_PERMISSION_SCORES[position - 2]
                )
            } else {
                tvRequiredPoints.visibility = View.GONE // Hide required points for initial items
            }
        }
    }

    /**
     * Enables or disables a button based on the global score and permission score.
     * @param button The button to enable or disable.
     * @param label The TextView displaying required points.
     * @param userScore The global main score.
     * @param requiredScore The permission score required for the button.
     */
    private fun evaluateButtonState(
        button: Button,
        label: TextView,
        userScore: Int,
        requiredScore: Int
    ) {
        if (userScore >= requiredScore) {
            button.isEnabled = true
            label.visibility = View.GONE // Hide required points when enabled
        } else {
            button.isEnabled = false
            label.visibility = View.VISIBLE // Show required points when disabled
        }
    }
}
