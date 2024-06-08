package edu.SpaceLearning.SpaceEnglish._Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.Objects;


import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter;
import edu.SpaceLearning.SpaceEnglish.AdsManager;
import edu.SpaceLearning.SpaceEnglish.Broadcast.NetworkChangeReceiver;
import edu.SpaceLearning.SpaceEnglish.Broadcast.NoConnectionFragment;
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess;
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment;
import edu.SpaceLearning.SpaceEnglish.MyBottomSheet;
import edu.SpaceLearning.SpaceEnglish.OnTablesRecyclerViewClickListener;
import edu.SpaceLearning.SpaceEnglish.QuizFrags.QuizCategoriesFragment;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.SoundManager;
import edu.SpaceLearning.SpaceEnglish.databinding.ActivityMainBinding;
import edu.SpaceLearning.SpaceEnglish.navfragments.HomeNavFragment;
import edu.SpaceLearning.SpaceEnglish.navfragments.QuizNavFragment;
import edu.SpaceLearning.SpaceEnglish.navfragments.SettingsNavFragment;
import edu.SpaceLearning.SpaceEnglish.navfragments.TablesNavFragments;
import edu.SpaceLearning.SpaceEnglish.onVideoBuyClickListener;

public class MainActivity extends AppCompatActivity implements OnTablesRecyclerViewClickListener, HomeNavFragment.HomeFragClickListener, QuizCategoriesFragment.QuizCategoryClickListener, RecyclerViewAdapter.onShowAdsClickListener,
        onVideoBuyClickListener,NetworkChangeReceiver.NetworkChangeListener, SettingsNavFragment.setOnChangeThemeListener, QuizNavFragment.OnsetFragmentToReplaceClickListener, DialogQuizFragment.onDialogSendHomeClickListener, DialogQuizFragment.onDialogNewQuizClickListener {

    private static final int REQUEST_IMAGE_PICK_PERMISSION = 200;
    // -------Declaration of variables------------
    private ActivityMainBinding binding;
    private DbAccess dbAccess;
    private SharedPrefsManager sharedPrefsManager;
    private SharedPreferences sharedPreferences;
    private AdsManager adsManager;
    private RecyclerViewAdapter mainRecyclerAdapter;
    private Menu mainMenu;
    private MenuItem searchItem;
    private MyBottomSheet myBottomSheet;
    private SoundManager soundManager;
    private RatingManager ratingManager;
    public static TextToSpeechManager textToSpeechManager;
    private boolean showRatingSheet = true;
    private NetworkChangeReceiver networkChangeReceiver;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       setSupportActionBar(binding.customToolbar); // Main toolbar

       registerNetworkChangeReceiver();
       initUtils();
       initDatabaseAccess();
       initSharedPreferences();
       initTextToSpeech();
       initSoundManager();
       initRatingManager();
       initAds();

       binding.mainNavFab.setOnClickListener(v -> ShowBottomSheet());
       setBottomNavWithMenu();
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
        AdRequest adRequest = new AdRequest.Builder().build();
        adsManager = new AdsManager(this, adRequest);
        MobileAds.initialize(this);
        adsManager.LoadSimpleAds();
        adsManager.LoadVideoAds();
    }

    private void initDatabaseAccess() {
        dbAccess = DbAccess.getInstance(this);
        dbAccess.open_to_read();
        dbAccess.getDBListCategorySize();
        dbAccess.close();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPrefsManager = new SharedPrefsManager(sharedPreferences);
        sharedPrefsManager.getSharedPreferencesData();
    }

    private void initUtils() {
       Utils.FillHashMapTableName();
       Utils.FillTableNames();
        Utils.FillListsCorrectIncorrectAnswerResponses();
    }

    private void setBottomNavWithMenu() {
        binding.bottomNav.getMenu().findItem(R.id.item_nav_home).setIcon(R.drawable.selector_home_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_tables).setIcon(R.drawable.selector_table_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_quiz).setIcon(R.drawable.selector_quiz_nav_change_icon);
        binding.bottomNav.getMenu().findItem(R.id.item_nav_settings).setIcon(R.drawable.selector_settings_nav_change_icon);

        // Set initial fragment
        setNavFragment(new HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT);

        binding.bottomNav.setOnItemSelectedListener(item -> {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Fragment selectedFragment = null;
            String fragmentTAG = null;

            switch (item.getItemId()) {
                case R.id.item_nav_home:
                    searchItem.setVisible(false);
                    fragmentTAG = Constants.TAG_HOME_NAV_FRAGMENT;
                    selectedFragment = new HomeNavFragment();
                    break;
                case R.id.item_nav_tables:
                    searchItem.setVisible(true);
                    fragmentTAG = Constants.TAG_TABLES_NAV_FRAGMENT;
                    checkNetworkAndUpdateUI(fragmentTAG);
                    return true; // Network check will handle fragment replacement
                case R.id.item_nav_quiz:
                    searchItem.setVisible(false);
                    fragmentTAG = Constants.TAG_QUIZ_NAV_FRAGMENT;
                    checkNetworkAndUpdateUI(fragmentTAG);
                    return true; // Network check will handle fragment replacement
                case R.id.item_nav_settings:
                    searchItem.setVisible(false);
                    fragmentTAG = Constants.TAG_SETTINGS_NAV_FRAGMENT;
                    selectedFragment = new SettingsNavFragment();
                    break;
            }

            if (selectedFragment != null) {
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

        if (fragmentTag.equals(Constants.TAG_TABLES_NAV_FRAGMENT)) {
            if (!isConnected) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                setNavFragment(new NoConnectionFragment(), Constants.NO_CONNECTION_FRAGMENT);
            } else {
                setNavFragment(new TablesNavFragments(), Constants.TAG_TABLES_NAV_FRAGMENT);
            }
        }
        if (fragmentTag.equals(Constants.TAG_QUIZ_NAV_FRAGMENT)) {
            if (!isConnected) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                setNavFragment(new NoConnectionFragment(), Constants.NO_CONNECTION_FRAGMENT);
            } else {
                setNavFragment(new QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT);
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
        if (index == Constants.TABLE_NAV_INDEX) {
            setNavFragment(new TablesNavFragments(),Constants.TAG_TABLES_NAV_FRAGMENT);
            binding.bottomNav.getMenu().getItem(Constants.TABLE_NAV_INDEX).setChecked(true);
        } else {
            setNavFragment(new QuizNavFragment(),Constants.TAG_QUIZ_NAV_FRAGMENT);
            binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
        }

    }


    @Override
    public void onPickImage() {
        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    ImagePicker.REQUEST_CODE);

        } else {
            // Permission already granted, start image picking
            startImagePicker();
        }

    }


    private void startImagePicker() {

        ImagePicker.with(this)
                .galleryOnly()
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start(ImagePicker.REQUEST_CODE);
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
        setNavFragment(new HomeNavFragment(),Constants.TAG_HOME_NAV_FRAGMENT);
        binding.bottomNav.getMenu().getItem(Constants.HOME_NAV_INDEX).setChecked(true);
    }

    @Override  //called from sheetDialog to re-play the quiz
    public void onSheetDialogNewQuizClick() {
        setNavFragment(new QuizNavFragment(),Constants.TAG_QUIZ_NAV_FRAGMENT);
        binding.bottomNav.getMenu().getItem(Constants.QUIZ_NAV_INDEX).setChecked(true);
    }

    @Override  // called from InfoFragment to replace it with the quiz frag CategoryType selected
    public void onSetRequiredFragmentQuiz(Fragment fragment) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setNavFragment(fragment,Constants.TAG_QUIZ_CATEGORY_FRAGMENT);
    }

    @Override
    public void onTableRecyclerViewClick(RecyclerViewAdapter adapter) {
        if (adapter != null)
            mainRecyclerAdapter = adapter;

    }

    @Override // called from sheetVideoFragment in sheetDialog
    public void onShowVideoAds(String categoryType) {
        adsManager.showRewardedVideoAds(categoryType);
        dbAccess.getDBListCategorySize();
        setNavFragment(new TablesNavFragments(), Constants.TAG_TABLES_NAV_FRAGMENT);
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
            setNavFragment(new QuizNavFragment(),Constants.TAG_QUIZ_NAV_FRAGMENT);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if(requestCode == ImagePicker.REQUEST_CODE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startImagePicker();
             }
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //-------for pick image----------------------
        if(requestCode == ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Utils.uriProfile = data.getData();
            setNavFragment(new HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT);
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
                    setNavFragment(new NoConnectionFragment(), Constants.NO_CONNECTION_FRAGMENT);
                }
            } else {
                Toast.makeText(this, "Connection Active", Toast.LENGTH_SHORT).show();
                if (!(currentFragment instanceof TablesNavFragments)) {
                    setNavFragment(new TablesNavFragments(), Constants.TAG_TABLES_NAV_FRAGMENT);
                }
            }
        }

        if (currentFragment instanceof QuizNavFragment ||currentFragment instanceof QuizCategoriesFragment || currentFragment instanceof NoConnectionFragment) {
            if (!isConnected) {
                Toast.makeText(this, "No Connection", Toast.LENGTH_LONG).show();
                if (!(currentFragment instanceof NoConnectionFragment)) {
                    setNavFragment(new NoConnectionFragment(), Constants.NO_CONNECTION_FRAGMENT);
                }
            } else {
                Toast.makeText(this, "Connection Active", Toast.LENGTH_SHORT).show();
                if (!(currentFragment instanceof QuizNavFragment || currentFragment instanceof QuizCategoriesFragment)) {
                    setNavFragment(new QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT);
                }
            }
        }
    }
}