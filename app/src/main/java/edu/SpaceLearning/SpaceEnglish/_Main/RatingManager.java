package edu.SpaceLearning.SpaceEnglish._Main;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class RatingManager {
    private ReviewManager reviewManager;
    private ReviewInfo reviewInfo;
    private Context context;

    public RatingManager(Context context) {
        this.context = context;
    }

    public void requestReviewInfo() {
        reviewManager = ReviewManagerFactory.create(context);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                reviewInfo = task.getResult();
            }

        });
    }
    public void showReviewFlow() {
        if(reviewInfo != null) {
            Task<Void> flow = reviewManager.launchReviewFlow((Activity)context,reviewInfo);
            flow.addOnCompleteListener(task -> {
                // do any thing here
            });
        }
    }
}
