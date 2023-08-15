package anouar.oulhaj.p001;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;


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
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.TablesFrags.TableCategoryFragment;
import anouar.oulhaj.p001.databinding.ActivityMainBinding;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragmentReplace;
import anouar.oulhaj.p001.navfragments.SettingsNavFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;

public class MainActivity extends AppCompatActivity implements  OnFragmentNavigationListener,HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener,TableCategoryFragment.onFilterRecycleClickListener,
                                           SettingsNavFragment.setOnChangeThemeListener,InfoQuizFragment.setFragmentToReplaceOnClickListener,DialogQuizFragment.onDialogSendHomeClickListener, DialogQuizFragment.onDialogNewQuizClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;
    private InterstitialAd mInterstitialAd; // for addMob
    private SharedPreferences sharedPreferences;
    private CategoryRecyclerAdapter mainAdapter;
    private Fragment activeFragment;
    private SearchView searchView;
    MenuItem searchItem;
    Menu mainMenu;
    MyBottomSheet myBottomSheet;


    private int verbMainScore,sentenceMainScore,phrasalMainScore,nounMainScore,adjMainScore,advMainScore,idiomMainScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activeFragment = new HomeNavFragment();
        fillDataFromDB(); // retrieve all category from DB
        Utils.FillCorrectIncorrectAnswerResponses(); // answer of speakEnglish
        retrieveDataSharedPreferences(); // Retrieve some data from SharedPreferences

        adsMobSetUp();
        binding.mainNavFab.setOnClickListener(view -> {


                    ShowBottomSheet();

                }
        );

        setBottomNavWithMenu();
        if(searchItem != null) searchItem.setVisible(false);

        binding.bottomNav.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_nav_home)
                    Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show();
                if(item.getItemId() == R.id.item_nav_quiz)
                    Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show();
                if(item.getItemId() == R.id.item_nav_tables)
                    Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show();
                if(item.getItemId() == R.id.item_nav_settings)
                    Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void showPaypal(float amount) {
        //------Test paypal------
        Intent intentPaypal = new Intent(MainActivity.this,PaypalActivity.class);
        intentPaypal.putExtra("amount",amount);
        startActivity(intentPaypal);
    }

    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu() {
        //----SetMenuItem and His Frag-------------
        setNavFragment(HomeNavFragment.newInstance(verbMainScore,sentenceMainScore,phrasalMainScore,nounMainScore
                ,adjMainScore,advMainScore,idiomMainScore,"no category yet"));
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    activeFragment = new HomeNavFragment();
                    setNavFragment(HomeNavFragment.newInstance(verbMainScore,sentenceMainScore,phrasalMainScore,nounMainScore
                            ,adjMainScore,advMainScore,idiomMainScore,"no category yet"));
                    return true;
                case R.id.item_nav_tables:
                    activeFragment = new TableCategoryFragment();
                    setNavFragment(new TablesNavFragments());
                    return true;
                case R.id.item_nav_quiz:
                    activeFragment = new QuizNavFragmentReplace();
                   // setNavFragment(new QuizNavFragment());
                    setNavFragment(new QuizNavFragmentReplace());
                    return true;
                case R.id.item_nav_settings:
                    activeFragment = new SettingsNavFragment();
                    setNavFragment(new SettingsNavFragment());
                    return true;
            }
            return false;
        });
    }

    private void ShowBottomSheet() {
        if (myBottomSheet == null || !myBottomSheet.isVisible()) {
            myBottomSheet = new MyBottomSheet();
            myBottomSheet.show(getSupportFragmentManager(), MyBottomSheet.SHEET_TAG);
        }
    }

    private void setNavFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        Utils.verbsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.VERB_NAME, true));
        Utils.sentencesList = new ArrayList<>(db.getAllElementsOfCategory(Constants.SENTENCE_NAME, false));
        Utils.phrasalsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.PHRASAL_NAME, true));
        Utils.nounsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.NOUN_NAME, true));
        Utils.adjsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.ADJ_NAME, true));
        Utils.advsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.ADV_NAME, true));
        Utils.idiomsList = new ArrayList<>(db.getAllElementsOfCategory(Constants.IDIOM_NAME, false));
        db.close();
    }

    //--------------------------------*****------------------------------------------
    private void retrieveDataSharedPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uriImg = sharedPreferences.getString("uri_profile", "");
        Constants.uri_pref = Uri.parse(uriImg);

        Utils.nativeLanguage = sharedPreferences.getString(Constants.TAG_NATIVE_LANGUAGE,"French");
        getElementsScoresFromSharedPreferences(sharedPreferences);
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TAG_NATIVE_LANGUAGE,Utils.nativeLanguage); // set preferences of current Native Language
        storeScoresSharedPreferences(editor);
        editor.apply();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //-------for pick image----------------------
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            //-----editor shared for img profile test------
            SharedPreferences.Editor editor = sharedPreferences.edit();
           editor.putString("uri_profile", String.valueOf(uri));
           editor.apply();

            Constants.uri_pref = uri;
            setNavFragment(new HomeNavFragment());
        }
    }

    @Override
    public void setScoreOnClick(int verbPointsAdded, int sentencePointsAdded, int phrasalPointsAdded , int nounPointsAdded, int adjPointsAdded, int advPointsAdded, int idiomPointsAdded,String categoryType) {

        verbMainScore+=verbPointsAdded;sentenceMainScore+=sentencePointsAdded; // add points to the main scores
        phrasalMainScore+=phrasalPointsAdded;nounMainScore+=nounPointsAdded;
        adjMainScore+=adjPointsAdded;advMainScore+=advPointsAdded;idiomMainScore+=idiomPointsAdded;

        String verbMsg = "Verbs --> " + verbMainScore + " (" + verbPointsAdded+ " points added).";
        String sentenceMsg = "Sentences --> " + sentenceMainScore + " (" + sentencePointsAdded+ " points added).";
        String phrasalMsg = "Phrasal verbs --> " + phrasalMainScore + " (" + phrasalPointsAdded+ " points added).";
        String nounMsg = "Nouns --> " + nounMainScore + " (" + nounPointsAdded+ " points added).";
        String adjMsg = "Adjectives --> " + adjMainScore + " (" + adjPointsAdded+ " points added).";
        String advMsg = "Adverbs --> " + advMainScore + " (" + advPointsAdded+ " points added).";
        String idiomMsg = "Idioms --> " + idiomMainScore + " (" + idiomPointsAdded+ " points added).";

        DialogQuizFragment dialog = DialogQuizFragment.newInstance("Your Score :",verbMsg,sentenceMsg,phrasalMsg,nounMsg,adjMsg,advMsg,idiomMsg,R.drawable.ic_person_24,categoryType);
        dialog.show(getSupportFragmentManager(),DialogQuizFragment.TAG);
    }


    public void adsMobSetUp(){
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
        private void getElementsScoresFromSharedPreferences(SharedPreferences sharedPreferences) {
        verbMainScore = sharedPreferences.getInt(Constants.TAG_PREF_VERB_SCORE,0);
        sentenceMainScore = sharedPreferences.getInt(Constants.TAG_PREF_SENTENCE_SCORE,0);
        phrasalMainScore = sharedPreferences.getInt(Constants.TAG_PREF_PHRASAL_SCORE,0);
        nounMainScore = sharedPreferences.getInt(Constants.TAG_PREF_NOUN_SCORE,0);
        adjMainScore =sharedPreferences.getInt(Constants.TAG_PREF_ADJ_SCORE,0);
        advMainScore =sharedPreferences.getInt(Constants.TAG_PREF_ADV_SCORE,0);
        idiomMainScore = sharedPreferences.getInt(Constants.TAG_PREF_IDIOM_SCORE,0);

        Utils.isThemeNight = sharedPreferences.getBoolean(Constants.ARG_IS_THEME_DARK_MODE,false);
    }
    public void storeScoresSharedPreferences(SharedPreferences.Editor editor) {
        editor.putInt(Constants.TAG_PREF_VERB_SCORE,verbMainScore);
        editor.putInt(Constants.TAG_PREF_SENTENCE_SCORE,sentenceMainScore);
        editor.putInt(Constants.TAG_PREF_PHRASAL_SCORE,phrasalMainScore);
        editor.putInt(Constants.TAG_PREF_NOUN_SCORE,nounMainScore);
        editor.putInt(Constants.TAG_PREF_ADJ_SCORE,adjMainScore);
        editor.putInt(Constants.TAG_PREF_ADV_SCORE,advMainScore);
        editor.putInt(Constants.TAG_PREF_IDIOM_SCORE,idiomMainScore);

        editor.putBoolean(Constants.ARG_IS_THEME_DARK_MODE , Utils.isThemeNight);
    }

    @Override
    public void onChangeTheme(boolean isDarkMode) {
        Utils.isThemeNight = isDarkMode;
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override
    public void onDialogSendHomeClick(String categoryType) {
        setNavFragment(HomeNavFragment.newInstance(verbMainScore, sentenceMainScore, phrasalMainScore, nounMainScore,  //set updated scores to HomeFrag
                adjMainScore, advMainScore, idiomMainScore ,categoryType));
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override
    public void onDialogNewQuizClick() {
        setNavFragment(new InfoQuizFragment());
        binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
    }

    @Override
    public void Replace(Fragment fragment) {
        setNavFragment(fragment);
    }

    private void performSearch(String query) {
        // Implement your search logic here
        Toast.makeText(this, "text Submit", Toast.LENGTH_SHORT).show();
        if (mainAdapter != null) {
            /*mainAdapter.filterStartedWith(query); // Call the filter method on the adapter
            mainAdapter.notifyDataSetChanged();*/
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
          mainMenu = menu;
        // Get the SearchView from the menu item
         searchItem = menu.findItem(R.id.menu_action_search);
         if(Utils.nameOfFragmentSearchView.equals("Home")) {
             searchItem.setVisible(false);
         }

         searchView = (SearchView) searchItem.getActionView();
        // Set up search event listeners
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                if(mainAdapter != null) {
                    mainAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_action_search) {
            // Handle search icon click here
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id ==R.id.menu_main_buy) {
            Toast.makeText(this, "Buy", Toast.LENGTH_SHORT).show();
            //-----paypal___________
            showPaypal(2f);
        } else if (id ==R.id.menu_main_ads) {
            Toast.makeText(this, "Buy", Toast.LENGTH_SHORT).show();
            //-----ads___________
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFilterClick(CategoryRecyclerAdapter adapter) {

       // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        mainAdapter = adapter;
       // mainAdapter.sayHello();

    }

    @Override
    public void onFragmentSelected(Fragment fragment) {
        if (fragment instanceof TablesNavFragments) {
            // Show the SearchView for the Table fragment
            mainMenu.findItem(R.id.menu_action_search).setVisible(true);

        } else {
            // Hide the SearchView for other fragments
            if (searchView != null) {
                mainMenu.findItem(R.id.menu_action_search).setVisible(false);
            }
        }
    }

    @Override
    public void onSetAdapterClickListener(CategoryRecyclerAdapter adapter) {
        if(adapter != null)
            mainAdapter = adapter;

    }

}