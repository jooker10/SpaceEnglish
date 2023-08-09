package anouar.oulhaj.p001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Phrasal;
import anouar.oulhaj.p001.DB.Sentence;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.QuizFrags.ChoicesPhrasalQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesSentencesQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesVerbsQcmFrag;
import anouar.oulhaj.p001.databinding.ActivityMainBinding;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragContainer;
import anouar.oulhaj.p001.navfragments.SettingsFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;

public class MainActivity extends AppCompatActivity implements DialogFragment.onDialogPositiveClickListener
, DialogFragment.onDialogNegativeClickListener, SettingsFragment.setOnChangeThemeListener,
        HomeNavFragment.HomeFragClickListener, DialogFragment.onDialogNeutralClickListener, MyBottomSheet.SheetItemClickListener,
        ChoicesSentencesQcmFrag.setOnChoicesFragClickListener, ChoicesVerbsQcmFrag.OnChoicesFragClickListener, ChoicesPhrasalQcmFrag.setOnChoicesFragClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;

    private static final int HOME_NAV_INDEX = 0;
    private static final int TABLE_NAV_INDEX = 1;
    private static final int QUIZ_NAV_INDEX = 3;
    private static final int SETTINGS_NAV_INDEX = 4;

    private InterstitialAd mInterstitialAd;

    public static int pref_verb_score;
    public static int pref_sentence_score;
    public static int pref_phrasal_score;

    public static String MAIN_CATEGORY_SENTENCES = "category 0";
    public static String TAG_PREF_VERB_SCORE = "verb_score";
    public static String TAG_PREF_SENTENCE_SCORE = "sentence_score";
    public static String TAG_PREF_PHRASAL_SCORE = "phrasal_score";
    public static String TAG_PREF_CHOOSING_LANG = "choosing_lang";

    public static Uri uri_pref;

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        edit.putInt(TAG_PREF_VERB_SCORE,pref_verb_score);
        edit.putInt(TAG_PREF_SENTENCE_SCORE,pref_sentence_score);
        edit.putInt(TAG_PREF_PHRASAL_SCORE,pref_phrasal_score);
        edit.putString(TAG_PREF_CHOOSING_LANG,Utils.language.toString());
        edit.apply();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    //_______Max and Authorized Count of verbs,sentences,phrasal-----------------
        DbAccess db = DbAccess.getInstance(this);
        db.open_to_read();
        List<Verb> allVerbs = db.getAllVerbs();
        List<Sentence> allSentences = db.getAllSentences();
        List<Phrasal> allPhrasals= db.getAllPhrasal();
        db.close();
        Utils.maxVerbsCount = allVerbs.size();
        Utils.maxSentencesCount = allSentences.size();
        Utils.maxPhrasalCount = allPhrasals.size();

    // ------------------admob-----------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
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
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sp.edit();
        pref_verb_score = sp.getInt(TAG_PREF_VERB_SCORE,0);
        pref_sentence_score = sp.getInt(TAG_PREF_SENTENCE_SCORE,0);
        pref_phrasal_score = sp.getInt(TAG_PREF_PHRASAL_SCORE,0);
        String choosedLang = sp.getString(TAG_PREF_CHOOSING_LANG,"French");
        if(choosedLang.equals(Language.SPANISH.toString())) {
            Utils.language = Language.SPANISH;
        } else if (choosedLang.equals(Language.ARABIC.toString())) {
            Utils.language = Language.ARABIC;
        }
        else  Utils.language = Language.FRENCH;

        setBottomNavWithMenu();


        //------------Theme-----------------


        //-----Shared preferences for img profile-------
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sp.edit();
        String uriImg = sp.getString("uri_profile","");
        uri_pref = Uri.parse(uriImg);

    }

    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu(){
        //----SetMenuItem and His Frag-------------
        setNavFragment(new HomeNavFragment());
        binding.bottomNav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.item_nav_home:
                    setNavFragment(HomeNavFragment.newInstance(pref_verb_score
                    ,pref_sentence_score,pref_phrasal_score));
                    return true;
                case R.id.item_nav_tables:
                    setNavFragment(new TablesNavFragments());
                    return true;
                case R.id.item_nav_quiz:
                    setNavFragment(new QuizNavFragContainer());
                    return true;
                case R.id.item_nav_settings:
                    setNavFragment(new SettingsFragment());
                    return true;
            }
            return false;
        });
    }

    private void ShowBottomSheet() {
        /*  MyBottomSheet myBottomSheet = MyBottomSheet.newInstance();

          myBottomSheet.show(getSupportFragmentManager(),MyBottomSheet.SHEET_TAG);*/
        MyBottomSheet myBottomSheet = new MyBottomSheet();
        myBottomSheet.show(getSupportFragmentManager(),MyBottomSheet.SHEET_TAG);
    }

    private void setNavFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter_to_right,R.anim.fragment_exit_to_right,R.anim.fragment_enter_to_right,R.anim.fragment_exit_to_right);
        ft.replace(R.id.main_frag_container,fragment);
        ft.commit();
    }

    @Override
    public void onDialogPositiveClick(String fr, String eng) {
       //------Btn to Insert Data to DB----------------
        DbAccess db = DbAccess.getInstance(this);
        db.open_to_write();
        db.InsertVerbs(new Verb(fr,eng));
        db.close();
    }

    @Override
    public void onDialogNegativeClick() {

    }

    @Override
    public void onDialogNeutralClick() {

    }

    @Override
    public void sheetBtnClick() {
        Toast.makeText(this, "Button Sheet clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sheetItemClicked(String str) {
        Toast.makeText(this, "sheet "+str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setScoreClick(int s0,int s1,int s2) {

        HomeNavFragment homeNavFragment = HomeNavFragment.newInstance(s0,s1,s2);
        setNavFragment(homeNavFragment);
        binding.bottomNav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
    }



    @Override
    public void onHomeGetStarted(int index) {
        if(index == 1){
            setNavFragment(new TablesNavFragments());
            binding.bottomNav.getMenu().getItem(TABLE_NAV_INDEX).setChecked(true);
        }
        else {
            setNavFragment(new QuizNavFragContainer());
            binding.bottomNav.getMenu().getItem(QUIZ_NAV_INDEX).setChecked(true);
        }

    }

    @Override
    public void onPickImage() {

        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();


    }

    @Override
    public void changeTheme(boolean isDarkMode) {
        binding.bottomNav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);

        if(isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
       // setNavFragment(new SettingsFragment());
        binding.bottomNav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
    }


    //--------------------------------*****------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //-------for pick image----------------------
        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            //-----editor shared for img profile test------
            edit.putString("uri_profile", String.valueOf(uri));
            edit.commit();

            uri_pref = uri;
            setNavFragment(new HomeNavFragment());
        }
    }
}