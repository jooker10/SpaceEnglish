package anouar.oulhaj.p001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.databinding.ActivityMainBinding;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragment;
import anouar.oulhaj.p001.navfragments.SettingsNavFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;

public class MainActivity extends AppCompatActivity implements HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;

    private InterstitialAd mInterstitialAd;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferencesEditor.putInt(Constants.TAG_PREF_VERB_SCORE, Constants.pref_verb_score);
        sharedPreferencesEditor.putInt(Constants.TAG_PREF_SENTENCE_SCORE, Constants.pref_sentence_score);
        sharedPreferencesEditor.putInt(Constants.TAG_PREF_PHRASAL_SCORE, Constants.pref_phrasal_score);
        sharedPreferencesEditor.putString(Constants.TAG_PREF_CHOOSING_LANG, Utils.language.toString());
        sharedPreferencesEditor.apply();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //----- set Lists from DB----------

        // fitch all category from DB
        fillDataFromDB();

        // ------------------admob-----------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
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

       /* main_fab.setOnClickListener(new View.OnClickListener() {
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
        });*/
        binding.mainNavFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowBottomSheet();
            }
        });


        //--------pref-------------
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferencesEditor = sharedPreferences.edit();
        Constants.pref_verb_score = sharedPreferences.getInt(Constants.TAG_PREF_VERB_SCORE, 0);
        Constants.pref_sentence_score = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_SCORE, 0);
        Constants.pref_phrasal_score = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_SCORE, 0);
        String choosedLang = sharedPreferences.getString(Constants.TAG_PREF_CHOOSING_LANG, "French");
        if (choosedLang.equals(Language.SPANISH.toString())) {
            Utils.language = Language.SPANISH;
        } else if (choosedLang.equals(Language.ARABIC.toString())) {
            Utils.language = Language.ARABIC;
        } else Utils.language = Language.FRENCH;

        setBottomNavWithMenu();


        //------------Theme-----------------


        //-----Shared preferences for img profile-------
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String uriImg = sharedPreferences.getString("uri_profile", "");
        Constants.uri_pref = Uri.parse(uriImg);

    }

    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu() {
        //----SetMenuItem and His Frag-------------
        setNavFragment(new HomeNavFragment());
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    setNavFragment(HomeNavFragment.newInstance(Constants.pref_verb_score, Constants.pref_sentence_score, Constants.pref_phrasal_score, Constants.pref_noun_score
                            , Constants.pref_adj_score, Constants.pref_adv_score, Constants.pref_idiom_score , "no category yet"));
                    return true;
                case R.id.item_nav_tables:
                    setNavFragment(new TablesNavFragments());
                    return true;
                case R.id.item_nav_quiz:
                    setNavFragment(new QuizNavFragment());
                    return true;
                case R.id.item_nav_settings:
                    setNavFragment(new SettingsNavFragment());
                    return true;
            }
            return false;
        });
    }

    private void ShowBottomSheet() {

        MyBottomSheet myBottomSheet = new MyBottomSheet();
        myBottomSheet.show(getSupportFragmentManager(), MyBottomSheet.SHEET_TAG);
    }

    private void setNavFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right, R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right);
        ft.replace(R.id.main_frag_container, fragment);
        ft.commit();
    }


    @Override
    public void onHomeGetStarted(int index) {
        if (index == 1) {
            setNavFragment(new TablesNavFragments());
            binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
        } else {
            setNavFragment(new QuizNavFragment());
            binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
        }

    }

    @Override
    public void onPickImage() {

        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();


    }

    private void fillDataFromDB() {
        DbAccess db = DbAccess.getInstance(this);
        db.open_to_read();
        Utils.verbsList = db.getAllElementsOfCategory(EnumCategory.VERB.name(), true);
        Utils.sentencesList = db.getAllElementsOfCategory(EnumCategory.SENTENCE.name(), false);
        Utils.phrasalsList = db.getAllElementsOfCategory(EnumCategory.PHRASAL.name(), true);
        Utils.nounsList = db.getAllElementsOfCategory(EnumCategory.NOUN.name(), true);
        Utils.adjsList = db.getAllElementsOfCategory(EnumCategory.ADJECTIVE.name(), true);
        Utils.advsList = db.getAllElementsOfCategory(EnumCategory.ADVERB.name(), true);
        Utils.idiomsList = db.getAllElementsOfCategory(EnumCategory.IDIOM.name(), false);
        db.close();
    }


    //--------------------------------*****------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //-------for pick image----------------------
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            //-----editor shared for img profile test------
            sharedPreferencesEditor.putString("uri_profile", String.valueOf(uri));
            sharedPreferencesEditor.commit();

            Constants.uri_pref = uri;
            setNavFragment(new HomeNavFragment());
        }
    }

    @Override
    public void setScoreOnClick(int verbScore, int sentenceScore, int phrasalScore, int nounScore, int adjScore, int advScore, int idiomScore,String categoryType) {
        setNavFragment(HomeNavFragment.newInstance(verbScore, sentenceScore, phrasalScore, nounScore, adjScore, advScore, idiomScore ,categoryType));
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }
}