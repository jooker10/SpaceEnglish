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

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

public class SplashActivity extends AppCompatActivity {

SharedPreferences sharedPreferences;
SharedPreferences defaultSharedPrefs;
SharedPrefsManager sharedPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME,MODE_PRIVATE);
        defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefsManager = new SharedPrefsManager(sharedPreferences);
        sharedPrefsManager.getSharedPrefTheme();
        onChangeTheme(Utils.isThemeNight);
        setContentView(R.layout.activity_splash);


        ImageView iconSplash = findViewById(R.id.img_splash_icon);
        iconSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim));

        boolean isInitialConfigurationCompleted = defaultSharedPrefs.getBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


    }


