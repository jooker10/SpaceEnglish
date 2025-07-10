package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

/**
 * RatingManager handles requesting and displaying in-app review prompts using Play Core Library.
 */
public class RatingManager {
    private ReviewManager reviewManager;
    private ReviewInfo reviewInfo;
    private final Context context;

    /**
     * Constructor to initialize RatingManager with a context.
     *
     * @param context The context of the calling activity or application.
     */
    public RatingManager(Context context) {
        this.context = context;
    }

    /**
     * Requests review information from the ReviewManager.
     * This method initializes the ReviewManager and requests review flow asynchronously.
     */
    public void requestReviewInfo() {
        reviewManager = ReviewManagerFactory.create(context);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            }
            // Handle any failure cases if necessary
        });
    }

    /**
     * Shows the review flow to the user if review information is available.
     * This method launches the review flow asynchronously.
     * Ensure requestReviewInfo() is called and reviewInfo is not null before calling this method.
     */
    public void showReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow((Activity) context, reviewInfo);
            flow.addOnCompleteListener(task -> {
                // Handle any completion tasks if needed
            });
        }
    }
}
