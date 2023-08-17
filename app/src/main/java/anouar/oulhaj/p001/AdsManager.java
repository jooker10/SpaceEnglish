package anouar.oulhaj.p001;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.MainActivity;
import anouar.oulhaj.p001._Main.Utils;

public class AdsManager {
    private MainActivity activity;
    private AdRequest adRequest;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd; // for addMob

    public AdsManager(MainActivity activity, AdRequest adRequest, RewardedAd rewardedAd, InterstitialAd mInterstitialAd) {
        this.activity = activity;
        this.adRequest = adRequest;
        this.rewardedAd = rewardedAd;
        this.mInterstitialAd = mInterstitialAd;
    }

    /*public void showPaypal(float amount) {
        // Show PayPal activity
        Intent intentPaypal = new Intent(activity, PaypalActivity.class);
        intentPaypal.putExtra("amount", amount);
        activity.startActivity(intentPaypal);
    }*/

    public void LoadVideoAds() {
        //video ads

        RewardedAd.load(activity, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG_Video_Error", loadAdError.toString());
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        Log.d("TAG", "Ad was loaded.");
                        Toast.makeText(activity, "Video loaded", Toast.LENGTH_SHORT).show();

                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d("TAG", "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("TAG", "Ad dismissed fullscreen content.");
                                rewardedAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e("TAG", "Ad failed to show fullscreen content.");
                                rewardedAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d("TAG", "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("TAG", "Ad showed fullscreen content.");
                            }
                        });
                    }
                });
    }
    public void showVideoAds(String categoryType){

        if (rewardedAd != null) {
            Activity activityContext = activity;
            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("TAG", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    Toast.makeText(activityContext, "You earned some coins" + rewardAmount, Toast.LENGTH_SHORT).show();
                    // Utils.allowedVerbsNumber +=10;
                    switch(categoryType) {
                        case Constants.VERB_NAME:
                            Utils.allowedVerbsNumber +=10;
                            break;
                        case Constants.SENTENCE_NAME:
                            Utils.allowedSentencesNumber +=5;
                            break;
                        case Constants.PHRASAL_NAME:
                            Utils.allowedPhrasalsNumber +=5;
                            break;
                        case Constants.NOUN_NAME:
                            Utils.allowedNounsNumber +=5;
                            break;
                        case Constants.ADJ_NAME:
                            Utils.allowedAdjsNumber +=5;
                            break;
                        case Constants.ADV_NAME:
                            Utils.allowedAdvsNumber +=5;
                            break;
                        case Constants.IDIOM_NAME:
                            Utils.allowedIdiomsNumber +=5;
                            break;
                    }
                    try {
                        activity.recreate();
                    } catch (Exception e) {
                        Log.d("najat3141" , e.toString());
                    }
                }
            });
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }
    }
    public void LoadAdsMob(){
        // ------------------admob-----------------
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        InterstitialAd.load(activity, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });

/*
        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-----ads___________
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
                //------Test paypal------
                Intent intentPaypal = new Intent(MainActivity.this,PaypalActivity.class);
                intentPaypal.putExtra("amount",0.99);
                startActivity(intentPaypal);

            }
        });
*/
    }
    public void showAdsMob() {
        //-----ads___________
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        }
    }
    public void showPaypal(float amount) {
        //------Test paypal------
        Intent intentPaypal = new Intent(activity, PaypalActivity.class);
        intentPaypal.putExtra("amount",amount);
        activity.startActivity(intentPaypal);
    }
}
