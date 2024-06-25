/*
 * MainActivity.java
 * Space English Learning App
 *
 * Main activity of the Space English Learning App, responsible for managing navigation,
 * user interactions, and displaying various fragments based on user actions.
 * Includes functionalities like bottom navigation, fragment transactions, menu handling,
 * permissions management, and interaction with databases and utilities.
 *
 * This activity initializes necessary components such as database access, shared preferences,
 * ads management, sound management, and text-to-speech services. It also handles user
 * interactions through implemented interfaces for fragment communications and click events.
 *
 * The app supports features like image picking, displaying PDF files, showing ads, and managing
 * app themes dynamically based on user preferences.
 *
 * Authors:
 * - [Your Name]
 * - [Co-author's Name] (if applicable)
 *
 * Date: [Date of creation or last modification]
 * Version: [App version number]
 */

package edu.SpaceLearning.SpaceEnglish._Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.Objects;

import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener;
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment;
import edu.SpaceLearning.SpaceEnglish.MyBottomSheet;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.AdsManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.CustomToast;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.RatingManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;
import edu.SpaceLearning.SpaceEnglish.databinding.ActivityMainBinding;
import edu.SpaceLearning.SpaceEnglish._Navfragments.HomeNavFragment;
import edu.SpaceLearning.SpaceEnglish._Navfragments.QuizNavFragment;
import edu.SpaceLearning.SpaceEnglish._Navfragments.SettingsNavFragment;
import edu.SpaceLearning.SpaceEnglish._Navfragments.TableNavFragment;

public class MainActivity extends AppCompatActivity implements InteractionActivityFragmentsListener, AdsClickListener {

    private static final int PERMISSION_REQUEST_CODE = 200;

    // Views and UI related variables
    private ActivityMainBinding binding;
    private MenuItem searchItem;

    // Data management
    private DbAccess dbAccess;
    private SharedPrefsManager sharedPrefsManager;
    private SharedPreferences sharedPreferences;

    // Managers and utilities
    private AdsManager adsManager;
    private RecyclerViewAdapter tableRecyclerAdapter;
    private MyBottomSheet myBottomSheet;
    private SoundManager soundManager;
    private RatingManager ratingManager;
    public static TextToSpeechManager textToSpeechManager;

    // Flags and state variables
    private boolean showRatingSheet = true;
    private boolean isTableFragmentActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.customToolbar);

        // Initialize utility methods and managers
        initUtils();
        initDatabaseAccess();
        initSharedPreferences();
        initTextToSpeech();
        initSoundManager();
        initRatingManager();
        initAds();

        // Set up bottom navigation and initial fragment
        binding.mainNavFab.setOnClickListener(v -> ShowMainBottomSheet());
        setBottomMainNavWithMenu();
    }

    private void initTextToSpeech() {
        // Initialize TextToSpeechManager
        textToSpeechManager = new TextToSpeechManager(this, status -> {
            // Handle initialization status if needed
        });
    }

    private void initSoundManager() {
        // Initialize SoundManager
        soundManager = new SoundManager();
    }

    private void initRatingManager() {
        // Initialize RatingManager
        ratingManager = new RatingManager(this);
        ratingManager.requestReviewInfo();
    }

    private void initAds() {
        // Initialize AdsManager
        adsManager = new AdsManager(this);
    }

    private void initDatabaseAccess() {
        // Initialize DbAccess on a separate thread
        Thread thread = new Thread(() -> {
            dbAccess = DbAccess.getInstance(getBaseContext());
            dbAccess.open_to_read();
            dbAccess.getDBListCategorySize();
            dbAccess.close();
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("MainActivity", thread.getName() + " : Failed to join thread");
        }
    }

    private void initSharedPreferences() {
        // Initialize SharedPreferences and SharedPrefsManager
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPrefsManager = new SharedPrefsManager(sharedPreferences);
        sharedPrefsManager.getSharedPreferencesData();
    }

    private void initUtils() {
        // Initialize static utility methods in Utils class
        Utils.FillItemRecyclerQuizNavList();
        Utils.FillListHeaderTablePdf();
        Utils.FillHashMapTableName();
        Utils.FillListCategoriesNames();
        Utils.FillListsCorrectIncorrectAnswerResponses();
    }

    private void setBottomMainNavWithMenu() {
        // Set icons and initial fragment for bottom navigation
        binding.bottomNav.getMenu().findItem(R.id.item_nav_home).setIcon(R.drawable.selector_home_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_tables).setIcon(R.drawable.selector_table_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_quiz).setIcon(R.drawable.selector_quiz_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_settings).setIcon(R.drawable.selector_settings_nav_change_icon);

        // Set initial fragment
        setNavFragment(new HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT);

        // Handle bottom navigation item selection
        binding.bottomNav.setOnItemSelectedListener(item -> {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Fragment selectedFragment = null;
            String fragmentTAG = null;

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    fragmentTAG = Constants.TAG_HOME_NAV_FRAGMENT;
                    selectedFragment = new HomeNavFragment();
                    break;
                case R.id.item_nav_tables:
                    fragmentTAG = Constants.TAG_TABLES_NAV_FRAGMENT;
                    selectedFragment = new TableNavFragment();
                    break;
                case R.id.item_nav_quiz:
                    fragmentTAG = Constants.TAG_QUIZ_NAV_FRAGMENT;
                    selectedFragment = new QuizNavFragment();
                    break;
                case R.id.item_nav_settings:
                    fragmentTAG = Constants.TAG_SETTINGS_NAV_FRAGMENT;
                    selectedFragment = new SettingsNavFragment();
                    break;
            }

            if (selectedFragment != null) {
                setNavFragment(selectedFragment, fragmentTAG);
            }

            return true;
        });

        // Handle bottom navigation item reselection
        binding.bottomNav.setOnItemReselectedListener(item -> CustomToast.showToast(this, "Already selected"));
    }

    private void ShowMainBottomSheet() {
        // Show bottom sheet dialog
        if (myBottomSheet == null || !myBottomSheet.isVisible()) {
            myBottomSheet = new MyBottomSheet();
            myBottomSheet.show(getSupportFragmentManager(), MyBottomSheet.SHEET_TAG);
        }
    }

    private void setNavFragment(Fragment fragment, String fragmentTAG) {
        // Set fragment in fragment container with custom animations
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right,
                R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right);
        ft.replace(R.id.main_frag_container, fragment, fragmentTAG);
        ft.commit();

        // Update isTableFragmentActive based on the fragment TAG
        isTableFragmentActive = fragmentTAG.equals(Constants.TAG_TABLES_NAV_FRAGMENT);
    }

    // Interface methods implementations

    @Override
    public void onHomeGetStarted(int index) {
        // Handle navigation to TableNavFragment or QuizNavFragment from HomeNavFragment
        if (index == Constants.TABLE_NAV_INDEX) {
            setNavFragment(new TableNavFragment(), Constants.TAG_TABLES_NAV_FRAGMENT);
            binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
        } else {
            setNavFragment(new QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT);
            binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
        }
    }

    @Override
    public void onPickImageProfile() {
        // Handle picking image for profile
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    ImagePicker.REQUEST_CODE);
        } else {
            // Permission granted, start image picking
            startImagePicker();
        }
    }

    private void startImagePicker() {
        // Start image picker library
        ImagePicker.with(this)
                .galleryOnly()
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(ImagePicker.REQUEST_CODE);
    }

    @Override
    public void onShowInterstitialAd() {
        // Handle showing interstitial ad
        adsManager.showInterstitialAd();
        adsManager.reLoadInterstitialAd();
    }

    @Override
    public void onShowRewardedAd() {
        // Handle showing rewarded ad
        adsManager.showRewardedAd();
        adsManager.reloadRewardedAd();
    }

    @Override
    public void onSendScoresToDialog(String categoryType, int pointsAdded, int elementsAdded, int userRightAnswerScore, String msg) {
        // Handle sending scores to DialogQuizFragment
        DialogQuizFragment dialog5 = DialogQuizFragment.newInstance(categoryType, pointsAdded, elementsAdded, userRightAnswerScore, msg);
        dialog5.show(getSupportFragmentManager(), DialogQuizFragment.TAG);
    }

    @Override
    public void onChangeTheme(boolean isDarkMode) {
        // Handle changing app theme
        Utils.isThemeNight = isDarkMode;
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override
    public void onDialogSendHomeClick(String categoryType) {
        // Handle sending home from dialog
        setNavFragment(new HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT);
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override
    public void onDialogNewQuiz() {
        // Handle starting new quiz from dialog
        setNavFragment(new QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT);
        binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
    }

    @Override
    public void onSetRequiredCategoryFragmentQuiz(Fragment fragment) {
        // Handle setting required category fragment for quiz
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setNavFragment(fragment, Constants.TAG_QUIZ_CATEGORY_FRAGMENT);
    }

    @Override
    public void openPdfWithIntent(Context context, File pdfFile) {
        // Handle opening PDF file with Intent
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, proceed to open PDF
            openPdfFile(pdfFile);
        }
    }

    @Override
    public void onFilterTableRecycler(RecyclerViewAdapter tableAdapter) {
        // Handle filtering table RecyclerView
        this.tableRecyclerAdapter = tableAdapter;
    }

    @Override
    public void onShowVideoAds(String categoryType) {
        // Handle showing video ads and navigating to TableNavFragment
        dbAccess.getDBListCategorySize();
        setNavFragment(new TableNavFragment(), Constants.TAG_TABLES_NAV_FRAGMENT);
        binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
    }

    // Menu and Permissions Handling

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the SearchView from the menu item
        searchItem = menu.findItem(R.id.mainmenu_search);

        // Show or hide SearchView based on fragment visibility
        if (isTableFragmentActive) {
            searchItem.setVisible(true);
        } else {
            searchItem.setVisible(false);
        }

        // Initialize SearchView
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Set up search event listeners
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed
                return false; // Return true to consume the event
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text changes
                if (tableRecyclerAdapter != null) {
                    tableRecyclerAdapter.getFilter().filter(newText);
                }
                return true; // Return true to consume the event
            }
        });

        return true; // Return true to indicate menu creation is handled
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.menu_main_contact_us) {
            // Handle contact us action
            contactUsEmail();
        } else if (id == R.id.menu_main_rating_app) {
            // Handle rating app action
            ratingManager.showReviewFlow();
        } else if (id == android.R.id.home) {
            // Handle Up button action
            setNavFragment(new QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        }
        return super.onOptionsItemSelected(item);
    }

    private void contactUsEmail() {
        // Handle sending email for contact us
        String ourEmail = "oulhajfuturapps@gmail.com";
        String subject = "Enter the subject";
        String msg = "Enter your question please!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + ourEmail));
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            CustomToast.showToast(this, "No email app installed");
        }
    }

    @Override
    protected void onStop() {
        // Save preferences and cleanup
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPrefsManager.putSharedPreferencesScores(editor);
        sharedPrefsManager.putSharedPrefTheme(editor);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Handle permission request results
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImagePicker();
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open PDF
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Handle activity result, particularly for image picker
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Utils.uriProfile = data.getData();
            setNavFragment(new HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cleanup on activity destroy
        if (soundManager != null) {
            soundManager.release();
        }
    }

    @Override
    public void onBackPressed() {
        // Handle back button press
        if (!showRatingSheet) {
            // Show confirmation dialog to exit app
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Exit App");
            alertDialog.setMessage("Do you want to exit the app?");
            alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                super.onBackPressed();
            });
            alertDialog.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        } else {
            // Show rating flow
            ratingManager.showReviewFlow();
            showRatingSheet = false;
        }
    }

    private void openPdfFile(File pdfFile) {
        // Open PDF file with Intent
        if (pdfFile.exists()) {
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            PackageManager pm = getPackageManager();
            if (intent.resolveActivity(pm) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Downloaded, please check the following path: " + pdfFile.getPath(), Toast.LENGTH_LONG).show();
            }
        } else {
            // Handle file not found
        }
    }
}
