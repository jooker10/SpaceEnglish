package anouar.oulhaj.p001._Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;
import anouar.oulhaj.p001.AdsManager;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DialogQuizFragment;
import anouar.oulhaj.p001.InfoQuizFragment;
import anouar.oulhaj.p001.MyBottomSheet;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.databinding.ActivityMainBinding;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragment;
import anouar.oulhaj.p001.navfragments.SettingsNavFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;
import anouar.oulhaj.p001.onVideoBuyClickListener;

public class MainActivity extends AppCompatActivity implements OnFragmentNavigationListener,HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener,
        onVideoBuyClickListener,SettingsNavFragment.setOnChangeThemeListener, InfoQuizFragment.OnsetFragmentToReplaceClickListener, DialogQuizFragment.onDialogSendHomeClickListener, DialogQuizFragment.onDialogNewQuizClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;
    private DbAccess dbAccess;
    private SharedPrefsManager sharedPrefsManager;
    private SharedPreferences sharedPreferences;
    private AdsManager adsManager;
    private AdRequest adRequest;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd; // for addMob
    private CategoryRecyclerAdapter mainAdapter;
   // private Fragment activeFragment;
    private SearchView searchView;
    private Menu mainMenu;
    private MyBottomSheet myBottomSheet;

    public  int verbMainScore,sentenceMainScore,phrasalMainScore,nounMainScore,adjMainScore,advMainScore,idiomMainScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbAccess = DbAccess.getInstance(this);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPrefsManager = new SharedPrefsManager(this,sharedPreferences);
        adRequest = new AdRequest.Builder().build();
        adsManager = new AdsManager(this,adRequest,rewardedAd,mInterstitialAd);

        sharedPrefsManager.getSharedPreferencesData(); // Retrieve some data from SharedPreferences
        dbAccess.fillDataFromDBWithSomeInitialization(); // retrieve all category from DB
        adsManager.LoadAdsMob();
        adsManager.LoadVideoAds();
        Utils.FillCorrectIncorrectAnswerResponses(); // answer of speakEnglish

        binding.mainNavFab.setOnClickListener(view -> {

                    ShowBottomSheet();

                }
        );

        setBottomNavWithMenu();

        binding.bottomNav.setOnItemReselectedListener(item -> {
            Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show();
        });

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
                    setNavFragment(HomeNavFragment.newInstance(verbMainScore,sentenceMainScore,phrasalMainScore,nounMainScore
                            ,adjMainScore,advMainScore,idiomMainScore,"no category yet"));
                    return true;
                case R.id.item_nav_tables:
                    setNavFragment(new TablesNavFragments());
                    return true;
                case R.id.item_nav_quiz:
                   // setNavFragment(new QuizNavFragment());
                    setNavFragment(new QuizNavFragment());
                    return true;
                case R.id.item_nav_settings:
                    adsManager.showAdsMob();
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


    @Override //called from Home to go to a destination given
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

    //--------------------------------*****------------------------------------------

    @Override //called when the user finish the quiz to set the right points added to the right place
    public void onSetScoreClick(int verbPointsAdded, int sentencePointsAdded, int phrasalPointsAdded , int nounPointsAdded, int adjPointsAdded, int advPointsAdded, int idiomPointsAdded, String categoryType) {

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

    @Override  // called from sheetDialog to send home and finished the quiz
    public void onDialogSendHomeClick(String categoryType) {
        setNavFragment(HomeNavFragment.newInstance(verbMainScore, sentenceMainScore, phrasalMainScore, nounMainScore,  //set updated scores to HomeFrag
                adjMainScore, advMainScore, idiomMainScore ,categoryType));
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override  //called from sheetDialog to re-play the quiz
    public void onSheetDialogNewQuizClick() {
        setNavFragment(new InfoQuizFragment());
        binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
    }

    @Override  // called from InfoFragment to replace it with the quiz frag CategoryType selected
    public void onReplaceInfoToQuiz(Fragment fragment) {
        setNavFragment(fragment);
    }

    @Override    //called to set the searchView item visible id the Table Frag
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
    public void onSetAdapterClick(CategoryRecyclerAdapter adapter) {
        if(adapter != null)
            mainAdapter = adapter;

    }

    @Override // called from sheetVideoFragment in sheetDialog
    public void onShowVideoAds(String categoryType) {
        adsManager.showVideoAds(categoryType);
    }
    @Override
    public void onBuyWithPaypal() {
          adsManager.showPaypal(10f);
    }

    // CallBack and Overrides methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu = menu;
        // Get the SearchView from the menu item
        MenuItem searchItem = menu.findItem(R.id.menu_action_search);
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
            adsManager.showPaypal(2f);
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
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPrefsManager.putSharedPreferencesScores(editor);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //-------for pick image----------------------
        if (resultCode == Activity.RESULT_OK) {

                Utils.uriProfile = data.getData();
            setNavFragment(new HomeNavFragment());
        }
    }
}