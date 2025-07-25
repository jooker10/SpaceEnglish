
package edu.SpaceLearning.SpaceEnglish.Listeners

/**
 * Interface definition for a listener to handle ad click events.
 */
interface AdEventListener {
    /**
     * Called when an interstitial ad should be shown.
     */
    fun showInterstitialAd()

    /**
     * Called when a rewarded ad should be shown.
     */
    fun showRewardedAd()

    /**
     * Called when a video ad should be shown for a specific category type.
     * @param categoryType The category type for which the video ad should be shown.
     */
    fun playAdForCategory(categoryType: String)
}
