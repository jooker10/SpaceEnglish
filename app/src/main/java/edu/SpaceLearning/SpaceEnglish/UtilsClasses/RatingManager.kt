package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * RatingManager handles requesting and displaying in-app review prompts using Play Core Library.
 */
class RatingManager
/**
 * Constructor to initialize RatingManager with a context.
 *
 * @param context The context of the calling activity or application.
 */(private val context: Context) {
    private var reviewManager: ReviewManager? = null
    private var reviewInfo: ReviewInfo? = null

    /**
     * Requests review information from the ReviewManager.
     * This method initializes the ReviewManager and requests review flow asynchronously.
     */
    fun requestReviewInfo() {
        reviewManager = ReviewManagerFactory.create(context)
        val request = reviewManager!!.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo?> ->
            if (task.isSuccessful) {
                reviewInfo = task.result
            }
        }
    }

    /**
     * Shows the review flow to the user if review information is available.
     * This method launches the review flow asynchronously.
     * Ensure requestReviewInfo() is called and reviewInfo is not null before calling this method.
     */
    fun showReviewFlow() {
        if (reviewInfo != null) {
            val flow = reviewManager!!.launchReviewFlow(
                (context as Activity),
                reviewInfo!!
            )
            flow.addOnCompleteListener { task: Task<Void?>? -> }
        }
    }
}
