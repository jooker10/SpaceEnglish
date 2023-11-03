package anouar.oulhaj.p001;

import android.app.Activity;
import android.content.Context;
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
import anouar.oulhaj.p001._Main.Scores;
import anouar.oulhaj.p001._Main.Utils;

public class AdsManager {
    private static final String ADS_INTERSTITIAL_ID = "ca-app-pub-1653992568721047/1421484847"; //reel
   // private static final String ADS_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/8691691433"; //test
    private static final String ADS_VIDEO_ID = "ca-app-pub-1653992568721047/8188991604"; //reel
    //private static final String ADS_VIDEO_ID = "ca-app-pub-3940256099942544/5224354917"; //test

    private Context context;
    private AdRequest adRequest;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd; // for addMob
    private final int pointsAdded = 10;
    private final int elemenetsAdded = 5;

    public AdsManager(Context context, AdRequest adRequest) {
        this.context = context;
        this.adRequest = adRequest;

    }


    public void LoadVideoAds() {
        //video ads

        RewardedAd.load(context, ADS_VIDEO_ID,
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
                        Toast.makeText(context, "new video loaded", Toast.LENGTH_SHORT).show();

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
    public void LoadAdsMob(){
        // ------------------admob-----------------
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        InterstitialAd.load(context, ADS_INTERSTITIAL_ID, adRequest,
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

    }
    public void showRewardedVideoAds(String categoryType){

        if (rewardedAd != null) {


            rewardedAd.show((Activity) context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("TAG_video", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    updateUIVideoAdsClicked(categoryType);
    }
            });
            LoadVideoAds();
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }
    }
    public void showVideoAdsQuiz(){

        if (rewardedAd != null) {

            rewardedAd.show((Activity) context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("TAG", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    Toast.makeText(context, "Thanks you for your support." + rewardAmount, Toast.LENGTH_SHORT).show();


                }
            });
            LoadVideoAds();
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }

    }
    public void showAdsMob() {
        //-----ads___________
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) context);
            LoadAdsMob();
        }
    }
    public void showPaypal(double amount) {
        //------Test paypal------
        Intent intentPaypal = new Intent(context, PaypalActivity.class);
        intentPaypal.putExtra(PaypalActivity.ARG_PAYMENT_AMOUNT,amount);
        ((Activity)context).startActivity(intentPaypal);
    }

    private void updateUIVideoAdsClicked(String categoryType){
        String txtMsg = "";
        switch(categoryType) {
            case Constants.VERB_NAME:
                Utils.allowedVerbsNumber = getCurrentElementsNewSize(Utils.totalVerbsNumber,Utils.allowedVerbsNumber,elemenetsAdded);
                Scores.verbAddedVideo +=elemenetsAdded;
                Scores.verbPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " verbs." ;
                break;
            case Constants.SENTENCE_NAME:
                Utils.allowedSentencesNumber = getCurrentElementsNewSize(Utils.totalSentencesNumber,Utils.allowedSentencesNumber,elemenetsAdded);
                Scores.sentenceAddedVideo +=elemenetsAdded;
                Scores.sentencePointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " sentences." ;
                break;
            case Constants.PHRASAL_NAME:
                Utils.allowedPhrasalsNumber = getCurrentElementsNewSize(Utils.totalPhrasalsNumber,Utils.allowedPhrasalsNumber,elemenetsAdded);
                Scores.phrasalAddedVideo +=elemenetsAdded;
                Scores.phrasalPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " Phrasal Verbs." ;
                break;
            case Constants.NOUN_NAME:
                Utils.allowedNounsNumber = getCurrentElementsNewSize(Utils.totalNounsNumber,Utils.allowedNounsNumber,elemenetsAdded);
                Scores.nounAddedVideo +=elemenetsAdded;
                Scores.nounPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " nouns." ;
                break;
            case Constants.ADJ_NAME:
                Utils.allowedAdjsNumber = getCurrentElementsNewSize(Utils.totalAdjsNumber,Utils.allowedAdjsNumber,elemenetsAdded);
                Scores.adjAddedVideo +=elemenetsAdded;
                Scores.adjPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " adjectives." ;
                break;
            case Constants.ADV_NAME:
                Utils.allowedAdvsNumber = getCurrentElementsNewSize(Utils.totalAdvsNumber,Utils.allowedAdvsNumber,elemenetsAdded);
                Scores.advAddedVideo +=elemenetsAdded;
                Scores.advPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " adverbs." ;
                break;
            case Constants.IDIOM_NAME:
                Utils.allowedIdiomsNumber = getCurrentElementsNewSize(Utils.totalIdiomsNumber,Utils.allowedIdiomsNumber,elemenetsAdded);
                Scores.idiomAddedVideo +=elemenetsAdded;
                Scores.idiomPointsAddedVideo += pointsAdded;
                txtMsg = "Greet you earned " + pointsAdded + " points, and "+elemenetsAdded + " Idioms." ;
                break;
        }

        Toast.makeText(context, txtMsg, Toast.LENGTH_SHORT).show();

        LoadVideoAds();
    }

    private int getCurrentElementsNewSize(int maxElementsCategory,int currentElementsCategory,int elementsAdded ){
        int result = 0;
        if((currentElementsCategory + elementsAdded) <= maxElementsCategory) {
            result = currentElementsCategory  + elementsAdded;
        } else{
            result = maxElementsCategory;
        }
        return result;

    }
}
