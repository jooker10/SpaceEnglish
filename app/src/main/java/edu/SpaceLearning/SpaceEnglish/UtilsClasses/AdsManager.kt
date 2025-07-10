package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity

/**
 * Utility class for managing Interstitial and Rewarded ads using Google AdMob.
 */
class AdsManager(private val context: Context) {
    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null

    /**
     * Constructor to initialize AdsManager with a given context.
     *
     * @param context The context from which AdsManager is created.
     */
    init {
        loadInterstitialAd()
        loadRewardedAd()
    }

    /**
     * Reloads the Interstitial ad.
     */
    fun reLoadInterstitialAd() {
        loadInterstitialAd()
    }

    /**
     * Reloads the Rewarded ad.
     */
    fun reloadRewardedAd() {
        loadRewardedAd()
    }

    /**
     * Loads the Interstitial ad from AdMob.
     */
    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }
            })
    }

    /**
     * Loads the Rewarded ad from AdMob.
     */
    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                mRewardedAd = null
            }
        })
    }

    /**
     * Shows the Interstitial ad if it's loaded.
     * If not loaded, logs an error.
     */
    fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show((context as MainActivity))
        } else {
            Log.e(TAG, "Interstitial ad not ready")
        }
    }

    /**
     * Shows the Rewarded ad if it's loaded.
     * If not loaded, logs an error.
     */
    fun showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd!!.show((context as MainActivity)) {
                // Handle reward if needed
            }
        } else {
            Log.e(TAG, "Rewarded ad not ready")
        }
    }

    companion object {
        // Test IDs for Interstitial and Rewarded ads
        private const val TEST_INTERSTITIAL_AD_UNIT_ID =
            "ca-app-pub-3940256099942544/8691691433" // Test ID
        private const val TEST_REWARDED_AD_UNIT_ID =
            "ca-app-pub-3940256099942544/5224354917" // Test ID

        // Realtime IDs for Interstitial and Rewarded ads
        private const val INTERSTITIAL_AD_UNIT_ID =
            "ca-app-pub-8315645297205950/3611783850" // Real ID
        private const val REWARDED_AD_UNIT_ID = "ca-app-pub-8315645297205950/1447234066" // Real ID

        private const val TAG = "Ads"
    }
}
