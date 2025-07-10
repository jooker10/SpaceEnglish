package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;

/**
 * Utility class for managing Interstitial and Rewarded ads using Google AdMob.
 */
public class AdsManager {

    // Test IDs for Interstitial and Rewarded ads
    private static final String TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/8691691433"; // Test ID
    private static final String TEST_REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"; // Test ID
    // Realtime IDs for Interstitial and Rewarded ads
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-8315645297205950/3611783850"; // Real ID
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-8315645297205950/1447234066"; // Real ID

    private final static String TAG = "Ads";
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
    private final Context context;

    /**
     * Constructor to initialize AdsManager with a given context.
     *
     * @param context The context from which AdsManager is created.
     */
    public AdsManager(Context context) {
        this.context = context;
        loadInterstitialAd();
        loadRewardedAd();
    }

    /**
     * Reloads the Interstitial ad.
     */
    public void reLoadInterstitialAd() {
        loadInterstitialAd();
    }

    /**
     * Reloads the Rewarded ad.
     */
    public void reloadRewardedAd() {
        loadRewardedAd();
    }

    /**
     * Loads the Interstitial ad from AdMob.
     */
    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                mInterstitialAd = null;
            }
        });
    }

    /**
     * Loads the Rewarded ad from AdMob.
     */
    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mRewardedAd = null;
            }
        });
    }

    /**
     * Shows the Interstitial ad if it's loaded.
     * If not loaded, logs an error.
     */
    public void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show((MainActivity) context);
        } else {
            Log.e(TAG, "Interstitial ad not ready");
        }
    }

    /**
     * Shows the Rewarded ad if it's loaded.
     * If not loaded, logs an error.
     */
    public void showRewardedAd() {
        if (mRewardedAd != null) {
            mRewardedAd.show((MainActivity) context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle reward if needed
                }
            });
        } else {
            Log.e(TAG, "Rewarded ad not ready");
        }
    }
}
