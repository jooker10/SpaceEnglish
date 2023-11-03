package anouar.oulhaj.p001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Objects;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.MainActivity;
import anouar.oulhaj.p001._Main.SharedPrefsManager;
import anouar.oulhaj.p001._Main.Utils;

public class SplashActivity extends AppCompatActivity {

SharedPreferences sharedPreferences;
SharedPreferences defaultSharedPrefs;
SharedPrefsManager sharedPrefsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME,MODE_PRIVATE);
        defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefsManager = new SharedPrefsManager(this,sharedPreferences);
        sharedPrefsManager.getSharedPrefTheme();
        onChangeTheme(Utils.isThemeNight);
        setContentView(R.layout.activity_splash);


        ImageView iconSplash = findViewById(R.id.img_splash_icon);
        TextView titleSplash = findViewById(R.id.titleSplash);
        iconSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim));
        titleSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_title_anim));

        boolean isInitialConfigurationCompleted = defaultSharedPrefs.getBoolean(Constants.ARG_IS_FIRST_TIME_ACTIVITY, false);
        Toast.makeText(this, isInitialConfigurationCompleted + "", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isInitialConfigurationCompleted) {
                    // If initial configuration is completed, load MainActivity
                    loadMainActivity();
                } else {
                    // If initial configuration is not completed, load FirstActivity
                    SharedPreferences.Editor editor = defaultSharedPrefs.edit();
                    editor.putBoolean(Constants.ARG_IS_FIRST_TIME_ACTIVITY, true);
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


