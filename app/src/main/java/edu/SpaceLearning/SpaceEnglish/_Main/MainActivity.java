package edu.SpaceLearning.SpaceEnglish._Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;

import edu.SpaceLearning.SpaceEnglish.Adapters.CategoryRecyclerAdapter;
import edu.SpaceLearning.SpaceEnglish.AdsManager;
import edu.SpaceLearning.SpaceEnglish.Broadcast.NetworkChangeReceiver;
import edu.SpaceLearning.SpaceEnglish.DB.DbAccess;
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment;
import edu.SpaceLearning.SpaceEnglish.MyBottomSheet;
import edu.SpaceLearning.SpaceEnglish.Broadcast.NoConnectionFragment;
import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.navfragments.NewQuizNavFragment;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.SoundManager;
import edu.SpaceLearning.SpaceEnglish.databinding.ActivityMainBinding;
import edu.SpaceLearning.SpaceEnglish.navfragments.HomeNavFragment;
import edu.SpaceLearning.SpaceEnglish.navfragments.SettingsNavFragment;
import edu.SpaceLearning.SpaceEnglish.navfragments.TablesNavFragments;
import edu.SpaceLearning.SpaceEnglish.onVideoBuyClickListener;

public class MainActivity extends AppCompatActivity implements OnFragmentNavigationListener, HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener, CategoryRecyclerAdapter.onShowAdsClickListener,
        onVideoBuyClickListener,NetworkChangeReceiver.NetworkChangeListener, SettingsNavFragment.setOnChangeThemeListener, NewQuizNavFragment.OnsetFragmentToReplaceClickListener, DialogQuizFragment.onDialogSendHomeClickListener, DialogQuizFragment.onDialogNewQuizClickListener {

    // -------Declaration of variables------------
    private ActivityMainBinding binding;
    private DbAccess dbAccess;
    private SharedPrefsManager sharedPrefsManager;
    private SharedPreferences sharedPreferences;
    private AdsManager adsManager;
    private CategoryRecyclerAdapter mainRecyclerAdapter;
    private Menu mainMenu;
    private MenuItem searchItem;
    private MyBottomSheet myBottomSheet;
    private SoundManager soundManager;
    private RatingManager ratingManager;
    private TextToSpeechManager textToSpeechManager;
    private boolean showRatingSheet = true;
    private NetworkChangeReceiver networkChangeReceiver;


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.customToolbar);  // set the main toolbar

        textToSpeechManager = new TextToSpeechManager(this, status -> {
            // Handle initialization status if needed
        });  // handel the textToSpeech.

        // Create an IntentFilter to specify the system broadcast
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        // Register the BroadcastReceiver with the IntentFilter
        registerReceiver(networkChangeReceiver, intentFilter);

        soundManager = new SoundManager(this); // sounds for quizzes.
        ratingManager = new RatingManager(this);
        ratingManager.requestReviewInfo();

        initialiseAllAdsType();  //all ads loading
        dbAccess = DbAccess.getInstance(this);
        dbAccess.getDBListCategorySize(); // get Total size of each category

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE); // private sharedPref.
        sharedPrefsManager = new SharedPrefsManager(this, sharedPreferences);
        sharedPrefsManager.getSharedPreferencesData(); // Retrieve some data from SharedPreferences

        Utils.FillListsCorrectIncorrectAnswerResponses(); // fill the 2 lists of answer of speakEnglish

        binding.mainNavFab.setOnClickListener(view -> ShowBottomSheet());

        setBottomNavWithMenuGPT(); // settings of binding the menu with the BottomNav.

    }*/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       setSupportActionBar(binding.customToolbar);

       initTextToSpeech();
       registerNetworkChangeReceiver();
       initSoundManager();
       initRatingManager();
       initAds();
       initDatabaseAccess();
       initSharedPreferences();
       initUtils();  // a List of Correct and Incorrect Response.

       binding.mainNavFab.setOnClickListener(v -> ShowBottomSheet());
       setBottomNavWithMenuGPT();
   }

    private void initTextToSpeech() {
        textToSpeechManager = new TextToSpeechManager(this, status -> {
            // Handle initialization status if needed
        });
    }

    private void registerNetworkChangeReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void initSoundManager() {
        soundManager = new SoundManager(this);
    }

    private void initRatingManager() {
        ratingManager = new RatingManager(this);
        ratingManager.requestReviewInfo();
    }

    private void initAds() {
        initialiseAllAdsType();
    }

    private void initDatabaseAccess() {
        dbAccess = DbAccess.getInstance(this);
        dbAccess.getDBListCategorySize();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPrefsManager = new SharedPrefsManager(this, sharedPreferences);
        sharedPrefsManager.getSharedPreferencesData();
    }

    private void initUtils() {
        Utils.FillListsCorrectIncorrectAnswerResponses();
    }




    private void initialiseAllAdsType() {

        AdRequest adRequest = new AdRequest.Builder().build();
        adsManager = new AdsManager(this, adRequest);
        MobileAds.initialize(this);
        adsManager.LoadSimpleAds();
        adsManager.LoadVideoAds();
    }

    // Provide a method to get the TextToSpeechManager instance
    public TextToSpeechManager getTextToSpeechManager() {
        return textToSpeechManager;
    }


    //-----------------------for Bottom Navigation view------------------------------
    private void setBottomNavWithMenu() {
        binding.bottomNav.getMenu().findItem(R.id.item_nav_home).setIcon(R.drawable.selector_home_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_tables).setIcon(R.drawable.selector_table_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_quiz).setIcon(R.drawable.selector_quiz_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_settings).setIcon(R.drawable.selector_settings_nav_change_icon);

        //----SetMenuItem and His Frag-------------
        setNavFragment(new HomeNavFragment(), "HOME_FRAGMENT");
        binding.bottomNav.setOnItemSelectedListener(item -> {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Fragment selectedFragment = null;
            String fragmentTAG = null;
            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    searchItem.setVisible(false);
                    fragmentTAG = "HOME_FRAGMENT";
                    selectedFragment = new HomeNavFragment();
                    //setNavFragment(new HomeNavFragment());
                    break;
                case R.id.item_nav_tables:
                    searchItem.setVisible(true);
                    fragmentTAG = "TABLES_FRAGMENT";
                    selectedFragment = new TablesNavFragments();
                    //setNavFragment(new TablesNavFragments());
                    break;
                case R.id.item_nav_quiz:
                    searchItem.setVisible(false);
                    fragmentTAG = "QUIZ_FRAGMENT";
                    selectedFragment = new NewQuizNavFragment();
                    //setNavFragment(new NewQuizNavFragment());
                    break;
                case R.id.item_nav_settings:
                    searchItem.setVisible(false);
                    fragmentTAG = "SETTINGS_FRAGMENT";
                    selectedFragment = new SettingsNavFragment();
                    //setNavFragment(new SettingsNavFragment());
                    break;
            }
            if (selectedFragment != null || fragmentTAG != null) {
                setNavFragment(selectedFragment, fragmentTAG);
            }
            return true;
        });
        binding.bottomNav.setOnItemReselectedListener(item -> CustomToast.showToast(this, "Already selected"));
    }

    private void setBottomNavWithMenuGPT() {
        binding.bottomNav.getMenu().findItem(R.id.item_nav_home).setIcon(R.drawable.selector_home_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_tables).setIcon(R.drawable.selector_table_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_quiz).setIcon(R.drawable.selector_quiz_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_settings).setIcon(R.drawable.selector_settings_nav_change_icon);

        // Set initial fragment
        setNavFragment(new HomeNavFragment(), "HOME_FRAGMENT");

        binding.bottomNav.setOnItemSelectedListener(item -> {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Fragment selectedFragment = null;
            String fragmentTAG = null;

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    searchItem.setVisible(false);
                    fragmentTAG = "HOME_FRAGMENT";
                    selectedFragment = new HomeNavFragment();
                    break;
                case R.id.item_nav_tables:
                    searchItem.setVisible(true);
                    fragmentTAG = "TABLES_FRAGMENT";
                    checkNetworkAndUpdateUI(fragmentTAG);
                    return true; // Network check will handle fragment replacement
                case R.id.item_nav_quiz:
                    searchItem.setVisible(false);
                    fragmentTAG = "QUIZ_FRAGMENT";
                    checkNetworkAndUpdateUI(fragmentTAG);
                    return true; // Network check will handle fragment replacement
                case R.id.item_nav_settings:
                    searchItem.setVisible(false);
                    fragmentTAG = "SETTINGS_FRAGMENT";
                    selectedFragment = new SettingsNavFragment();
                    break;
            }

            if (selectedFragment != null && fragmentTAG != null) {
                setNavFragment(selectedFragment, fragmentTAG);
            }

            return true;
        });

        binding.bottomNav.setOnItemReselectedListener(item -> CustomToast.showToast(this, "Already selected"));
    }


    private void checkNetworkAndUpdateUI(String fragmentTag) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (fragmentTag.equals("TABLES_FRAGMENT")) {
            if (!isConnected) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                setNavFragment(new NoConnectionFragment(), "NO_CONNECTION_FRAGMENT");
            } else {
                setNavFragment(new TablesNavFragments(), "TABLES_FRAGMENT");
            }
        }
        if (fragmentTag.equals("QUIZ_FRAGMENT")) {
            if (!isConnected) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                setNavFragment(new NoConnectionFragment(), "NO_CONNECTION_FRAGMENT");
            } else {
                setNavFragment(new NewQuizNavFragment(), "QUIZ_FRAGMENT");
            }
        }
    }

    private void ShowBottomSheet() {
        if (myBottomSheet == null || !myBottomSheet.isVisible()) {
            myBottomSheet = new MyBottomSheet();
            myBottomSheet.show(getSupportFragmentManager(), MyBottomSheet.SHEET_TAG);
        }
    }

    private void setNavFragment(Fragment fragment , String fragmentTAG) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right, R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right);
        ft.replace(R.id.main_frag_container, fragment,fragmentTAG);
        ft.commit();
    }


    @Override //called from Home to go to a destination given
    public void onHomeGetStarted(int index) {
        if (index == 1) {
            setNavFragment(new TablesNavFragments(),"TABLES_FRAGMENT");
            binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
        } else {
            setNavFragment(new NewQuizNavFragment(),"");
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
        adsManager.showSimpleAds();
    }

    @Override
    public void onShowVideoAdsQuiz() {
        adsManager.showVideoAdsQuiz();
    }

    @Override
    public void onSetQuizCategoryResultClick(String categoryType, int pointsAdded, int elementsAdded , int userRightAnswerScore , String msg) {
        DialogQuizFragment dialog5 = DialogQuizFragment.newInstance(categoryType, pointsAdded, elementsAdded, userRightAnswerScore ,msg);
        dialog5.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
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
        setNavFragment(new HomeNavFragment(),"HOME_FRAGMENT");
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override  //called from sheetDialog to re-play the quiz
    public void onSheetDialogNewQuizClick() {
        setNavFragment(new NewQuizNavFragment(),"");
        binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
    }

    @Override  // called from InfoFragment to replace it with the quiz frag CategoryType selected
    public void onSetRequiredFragmentQuiz(Fragment fragment) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setNavFragment(fragment,"");
    }

    @Override    //called to set the searchView item visible id the Table Frag
    public void onFragmentSelected(Fragment fragment) {
        if (fragment instanceof TablesNavFragments) {
            // Show the SearchView for the Table fragment

        } else {
            // Hide the SearchView for other fragments

        }
    }

    @Override
    public void onSetAdapterClick(CategoryRecyclerAdapter adapter) {
        if (adapter != null)
            mainRecyclerAdapter = adapter;

    }

    @Override // called from sheetVideoFragment in sheetDialog
    public void onShowVideoAds(String categoryType) {
        adsManager.showRewardedVideoAds(categoryType);
        dbAccess.getDBListCategorySize();
        setNavFragment(new TablesNavFragments(), "TABLES_FRAGMENT");
        binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
    }


    // CallBack and Overrides methods

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mainMenu = menu;

        // Get the SearchView from the menu item
         searchItem = menu.findItem(R.id.mainmenu_search);


        // Initialize SearchView
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up search event listeners
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed

                return true; // Return true to consume the event
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                if (mainRecyclerAdapter != null) {
                    mainRecyclerAdapter.getFilter().filter(newText);
                }
                return true; // Return true to consume the event
            }
        });

        return true; // Return true to indicate menu creation is handled
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_main_contact_us) {
           contactUsEmail();

        } else if (id == R.id.menu_main_rating_app) {
            ratingManager.showReviewFlow();
        } else if (id == android.R.id.home) {
            setNavFragment(new NewQuizNavFragment(),"");
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }
    private void contactUsEmail(){
        String ourEmail = "oulhajfuturapps@gmail.com";
        String subject = "Enter the subject";
        String msg = "Enter your question please!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+ourEmail));
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            CustomToast.showToast(this,"No email app installed");
        }

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
            setNavFragment(new HomeNavFragment(), "HOME_FRAGMENT");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
        unregisterReceiver(networkChangeReceiver);
    }
    @Override
    public void onBackPressed() {

        if(!showRatingSheet) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Exit App");
            alertDialog.setMessage("Do you want to exit the app?");
            alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                // finishAffinity();
                super.onBackPressed();
            });
            alertDialog.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }
        else {
            ratingManager.showReviewFlow();
            showRatingSheet = false;
        }

    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frag_container);

        if (currentFragment instanceof TablesNavFragments || currentFragment instanceof NoConnectionFragment) {
            if (!isConnected) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_LONG).show();
                if (!(currentFragment instanceof NoConnectionFragment)) {
                    setNavFragment(new NoConnectionFragment(), "NO_CONNECTION_FRAGMENT");
                }
            } else {
                Toast.makeText(this, "Connection Active", Toast.LENGTH_SHORT).show();
                if (!(currentFragment instanceof TablesNavFragments)) {
                    setNavFragment(new TablesNavFragments(), "TABLES_FRAGMENT");
                }
            }
        }

        if (currentFragment instanceof NewQuizNavFragment ||currentFragment instanceof QuizCategoriesFragment || currentFragment instanceof NoConnectionFragment) {
            if (!isConnected) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_LONG).show();
                if (!(currentFragment instanceof NoConnectionFragment)) {
                    setNavFragment(new NoConnectionFragment(), "NO_CONNECTION_FRAGMENT");
                }
            } else {
                Toast.makeText(this, "Connection Active", Toast.LENGTH_SHORT).show();
                if (!(currentFragment instanceof NewQuizNavFragment || currentFragment instanceof QuizCategoriesFragment)) {
                    setNavFragment(new NewQuizNavFragment(), "TABLES_FRAGMENT");
                }
            }
        }
    }
}