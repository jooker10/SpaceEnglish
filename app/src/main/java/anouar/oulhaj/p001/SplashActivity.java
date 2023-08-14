package anouar.oulhaj.p001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDarkTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Objects.requireNonNull(getSupportActionBar()).hide();
        ImageView iconSplash = findViewById(R.id.img_splash_icon);
        TextView titleSplash = findViewById(R.id.titleSplash);
        iconSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim));
        titleSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_title_anim));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }


    private void setDarkTheme() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkMode_main_theme = sp.getBoolean(Constants.ARG_IS_THEME_DARK_MODE,false);
        if(isDarkMode_main_theme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    }


