package anouar.oulhaj.p001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private void setDarkTheme(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDarkMode_main_theme = sp.getBoolean("dark_theme",false);
        if(isDarkMode_main_theme)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

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
}