package edu.SpaceLearning.SpaceEnglish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.google.android.gms.ads.MobileAds;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences defaultSharedPrefs;
    private SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Mobile Ads SDK in a background thread
        new Thread(() -> {
            MobileAds.initialize(this, initializationStatus -> {
                // Initialization complete, can handle any callback if needed
            });
        }).start();

        // Initialize SharedPreferences and SharedPrefsManager
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefsManager = new SharedPrefsManager(sharedPreferences);

        // Retrieve and apply the saved theme
        sharedPrefsManager.getSharedPrefTheme();
        onChangeTheme(Utils.isThemeNight);

        // Start animation for splash icon
        ImageView iconSplash = findViewById(R.id.img_splash_icon);
        iconSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim));

        // Check if initial configuration is completed
        boolean isInitialConfigurationCompleted = defaultSharedPrefs.getBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY, false);

        // Delay for 2 seconds before proceeding to next activity
        new Handler().postDelayed(() -> {
            if (isInitialConfigurationCompleted) {
                // If initial configuration is completed, load MainActivity
                loadMainActivity();
            } else {
                // If initial configuration is not completed, load FirstActivity
                SharedPreferences.Editor editor = defaultSharedPrefs.edit();
                editor.putBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY, true);
                editor.apply();
                loadFirstActivity();
            }
        }, 2000);
    }

    private void loadFirstActivity() {
        Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onChangeTheme(boolean isDarkMode) {
        Utils.isThemeNight = isDarkMode;
        if (isDarkMode) {
            // Apply night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // Apply day mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
