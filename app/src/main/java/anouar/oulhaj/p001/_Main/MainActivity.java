package anouar.oulhaj.p001._Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import anouar.oulhaj.p001.Adapters.CategoryRecyclerAdapter;
import anouar.oulhaj.p001.AdsManager;
import anouar.oulhaj.p001.DB.DbAccess;
import anouar.oulhaj.p001.DialogQuizFragment;
import anouar.oulhaj.p001.MyBottomSheet;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.QuizFrags.InfoQuizFragment;
import anouar.oulhaj.p001.QuizFrags.QuizCategoriesFragment;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.SoundManager;
import anouar.oulhaj.p001.databinding.ActivityMainBinding;
import anouar.oulhaj.p001.navfragments.HomeNavFragment;
import anouar.oulhaj.p001.navfragments.QuizNavFragment;
import anouar.oulhaj.p001.navfragments.SettingsNavFragment;
import anouar.oulhaj.p001.navfragments.TablesNavFragments;
import anouar.oulhaj.p001.onVideoBuyClickListener;

public class MainActivity extends AppCompatActivity implements OnFragmentNavigationListener, HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener,
        onVideoBuyClickListener, SettingsNavFragment.setOnChangeThemeListener, InfoQuizFragment.OnsetFragmentToReplaceClickListener, DialogQuizFragment.onDialogSendHomeClickListener, DialogQuizFragment.onDialogNewQuizClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;
    private DbAccess dbAccess;
    private SharedPrefsManager sharedPrefsManager;
    private SharedPreferences sharedPreferences;
    private AdsManager adsManager;
    private CategoryRecyclerAdapter mainAdapter;
    private SearchView searchView;
    private Menu mainMenu;
    private MyBottomSheet myBottomSheet;
    private SoundManager soundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.customToolbar);
        soundManager = new SoundManager(this);

        initialiseAllAdsType();  //all ads loading
        dbAccess = DbAccess.getInstance(this);
        dbAccess.getDBListCategorySize(); // get Total size of each category

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPrefsManager = new SharedPrefsManager(this, sharedPreferences);
        sharedPrefsManager.getSharedPreferencesData(); // Retrieve some data from SharedPreferences

        Utils.FillCorrectIncorrectAnswerResponses(); // answer of speakEnglish

        binding.mainNavFab.setOnClickListener(view -> ShowBottomSheet()
        );

        setBottomNavWithMenu();

        binding.bottomNav.setOnItemReselectedListener(item -> Toast.makeText(MainActivity.this, "Already selected", Toast.LENGTH_SHORT).show());


    }

    private void initialiseAllAdsType() {

        AdRequest adRequest = new AdRequest.Builder().build();
        adsManager = new AdsManager(this, adRequest);
        MobileAds.initialize(this);
        adsManager.LoadAdsMob();
        adsManager.LoadVideoAds();
    }


    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu() {
        binding.bottomNav.getMenu().findItem(R.id.item_nav_home).setIcon(R.drawable.selector_home_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_tables).setIcon(R.drawable.selector_table_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_quiz).setIcon(R.drawable.selector_quiz_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_settings).setIcon(R.drawable.selector_settings_nav_change_icon);

        //----SetMenuItem and His Frag-------------
        setNavFragment(HomeNavFragment.newInstance(""));
        // binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    setNavFragment(HomeNavFragment.newInstance(""));
                    return true;
                case R.id.item_nav_tables:
                    setNavFragment(new TablesNavFragments());
                    return true;
                case R.id.item_nav_quiz:
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


    @Override
    public void onShowSimpleAdsQuiz() {
        adsManager.showAdsMob();
    }

    @Override
    public void onShowVideoAdsQuiz() {
        adsManager.showVideoAdsQuiz();
    }

    @Override
    public void onSetQuizCategoryResultClick(String categoryType, int pointsAdded, int elementsAdded) {
        switch (categoryType) {
            case Constants.VERB_NAME:
                DialogQuizFragment dialog1 = DialogQuizFragment.newInstance(categoryType, Scores.verbScore, pointsAdded, elementsAdded, Scores.verbQuizCompleted);
                dialog1.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.SENTENCE_NAME:
                DialogQuizFragment dialog2 = DialogQuizFragment.newInstance(categoryType, Scores.sentenceScore, pointsAdded, elementsAdded, Scores.sentenceQuizCompleted);
                dialog2.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.PHRASAL_NAME:
                DialogQuizFragment dialog3 = DialogQuizFragment.newInstance(categoryType, Scores.phrasalScore, pointsAdded, elementsAdded, Scores.phrasalQuizCompleted);
                dialog3.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.NOUN_NAME:
                DialogQuizFragment dialog4 = DialogQuizFragment.newInstance(categoryType, Scores.nounScore, pointsAdded, elementsAdded, Scores.nounQuizCompleted);
                dialog4.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.ADJ_NAME:
                DialogQuizFragment dialog5 = DialogQuizFragment.newInstance(categoryType, Scores.adjScore, pointsAdded, elementsAdded, Scores.adjQuizCompleted);
                dialog5.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.ADV_NAME:
                DialogQuizFragment dialog6 = DialogQuizFragment.newInstance(categoryType, Scores.advScore, pointsAdded, elementsAdded, Scores.advQuizCompleted);
                dialog6.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;
            case Constants.IDIOM_NAME:
                DialogQuizFragment dialog7 = DialogQuizFragment.newInstance(categoryType, Scores.idiomScore, pointsAdded, elementsAdded, Scores.idiomQuizCompleted);
                dialog7.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
                break;

        }
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
        setNavFragment(HomeNavFragment.newInstance(categoryType));
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
        if (adapter != null)
            mainAdapter = adapter;

    }

    @Override // called from sheetVideoFragment in sheetDialog
    public void onShowVideoAds(String categoryType) {
        adsManager.showRewardedVideoAds(categoryType);
        dbAccess.getDBListCategorySize();
        setNavFragment(new TablesNavFragments());
        binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
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
        if (Utils.nameOfFragmentSearchView.equals("Home")) {
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
                if (mainAdapter != null) {
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
        } else if (id == R.id.menu_main_buy) {
            Toast.makeText(this, "Buy", Toast.LENGTH_SHORT).show();
            //-----paypal___________
            adsManager.showPaypal(2f);
        } else if (id == R.id.menu_main_ads) {
            Toast.makeText(this, "Ads", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPrefsManager.putSharedPreferencesScores(editor);
        sharedPrefsManager.putSharedPrefTheme(editor);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //-------for pick image----------------------
        if (resultCode == Activity.RESULT_OK) {

            if (data != null)
                Utils.uriProfile = data.getData();
            setNavFragment(new HomeNavFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }
}