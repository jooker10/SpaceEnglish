package anouar.oulhaj.p001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import anouar.oulhaj.p001._Main.Constants;
import anouar.oulhaj.p001._Main.MainActivity;
import anouar.oulhaj.p001._Main.Utils;

public class FirstActivity extends AppCompatActivity {

    Button btnSkip,btnSubmit;
    AutoCompleteTextView autoTvLanguage;
    TextInputEditText tvUserName;
    RadioButton radioDarkMode;
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        sharedPreferences= getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME,MODE_PRIVATE);
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        btnSkip = findViewById(R.id.firstBtnSkip);
        btnSubmit = findViewById(R.id.firstBtnSubmit);
        autoTvLanguage = findViewById(R.id.autoTxtLanguage);
        tvUserName = findViewById(R.id.txtInputUserName);
        radioDarkMode = findViewById(R.id.firstRadioDarkMode);
        String[] nativeLanguages = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,nativeLanguages);
        autoTvLanguage.setAdapter(autoAdapter);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strLang = autoTvLanguage.getText().toString();
                String strUserName = "user 1";
                strUserName = tvUserName.getText().toString().trim();
                boolean isDarkMode = radioDarkMode.isChecked();

                Utils.nativeLanguage = strLang;
                Utils.userName = strUserName;
                Utils.isThemeNight = isDarkMode;

                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                ChangeTheme(Utils.isThemeNight);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor defaultEditor = defaultSharedPreferences.edit();

        editor.putString(Constants.KEY_USER_NAME,Utils.userName);
        editor.putString(Constants.TAG_NATIVE_LANGUAGE,Utils.nativeLanguage);
        editor.putBoolean(Constants.ARG_IS_THEME_DARK_MODE,Utils.isThemeNight);

        defaultEditor.putString(Constants.KEY_SETTINGS_SWITCH_LANGUAGE,Utils.nativeLanguage);
        defaultEditor.putString(Constants.KEY_USER_NAME,Utils.userName);
        defaultEditor.putBoolean(Constants.KEY_SETTINGS_SWITCH_THEME,Utils.isThemeNight);

        editor.apply();
        defaultEditor.apply();
    }

    public void ChangeTheme(boolean isDarkMode) {
        Utils.isThemeNight = isDarkMode;
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}