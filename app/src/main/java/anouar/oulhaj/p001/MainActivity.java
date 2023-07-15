package anouar.oulhaj.p001;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DB.Verb;
import anouar.oulhaj.p001.QuizFrags.ChoicesPhrasalQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesSentencesQcmFrag;
import anouar.oulhaj.p001.QuizFrags.ChoicesVerbsQcmFrag;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragContainer;
import anouar.oulhaj.p001.navfragments.SettingsFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;

public class MainActivity extends AppCompatActivity implements DialogFragment.onDialogPositiveClickListener
, DialogFragment.onDialogNegativeClickListener, SettingsFragment.setOnChangeThemeListener,
        HomeNavFragment.HomeFragClickListener, DialogFragment.onDialogNeutralClickListener, MyBottomSheet.SheetItemClickListener,
        ChoicesSentencesQcmFrag.setOnChoicesFragClickListener, ChoicesVerbsQcmFrag.OnChoicesFragClickListener, ChoicesPhrasalQcmFrag.setOnChoicesFragClickListener {

    // -------Declaration of variables------------

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

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    private BottomNavigationView bottom_nav;

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
        edit.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----view inflate-------
        bottom_nav = findViewById(R.id.bottom_nav);
        FloatingActionButton main_fab = findViewById(R.id.main_nav_fab);

        //------------------admob-----------------
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

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
            }
        });


        //--------pref-------------
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sp.edit();
        pref_verb_score = sp.getInt(TAG_PREF_VERB_SCORE,0);
        pref_sentence_score = sp.getInt(TAG_PREF_SENTENCE_SCORE,0);
        pref_phrasal_score = sp.getInt(TAG_PREF_PHRASAL_SCORE,0);

        setBottomNavWithMenu();


        //------------Theme-----------------
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkMode_main_theme = sp.getBoolean("dark_theme",false);
        changeTheme(isDarkMode_main_theme);

    }

    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu(){
        //----SetMenuItem and His Frag-------------
        setNavFragment(new HomeNavFragment());
        bottom_nav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
        bottom_nav.setOnItemSelectedListener(item -> {


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
          MyBottomSheet myBottomSheet = MyBottomSheet.newInstance();
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
        bottom_nav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
    }



    @Override
    public void onHomeGetStarted() {
        setNavFragment(new TablesNavFragments());
        bottom_nav.getMenu().getItem(TABLE_NAV_INDEX).setChecked(true);
    }
    @Override
    public void changeTheme(boolean isDarkMode) {
        bottom_nav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);

        if(isDarkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
       // setNavFragment(new SettingsFragment());
        bottom_nav.getMenu().getItem(HOME_NAV_INDEX).setChecked(true);
    }


    //--------------------------------*****------------------------------------------

    void admob(){

    }
}