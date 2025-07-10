/*
 * File: AdsClickListener.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Listener interface for handling ad click events.
 */
package edu.SpaceLearning.SpaceEnglish.Listeners

/**
 * Interface definition for a listener to handle ad click events.
 */
interface AdsClickListener {
    /**
     * Called when an interstitial ad should be shown.
     */
    fun onShowInterstitialAd()

    /**
     * Called when a rewarded ad should be shown.
     */
    fun onShowRewardedAd()

    /**
     * Called when a video ad should be shown for a specific category type.
     * @param categoryType The category type for which the video ad should be shown.
     */
    fun onShowVideoAds(categoryType: String)
}
