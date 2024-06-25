package edu.SpaceLearning.SpaceEnglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;

import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

/**
 * FirstActivity is the initial activity where the user sets up preferences like language, username, and theme.
 */
public class FirstActivity extends AppCompatActivity {

    Button btnSkip, btnSubmit;
    AutoCompleteTextView autoTvLanguage;
    TextInputEditText tvUserName;
    RadioButton radioDarkMode;
    RadioButton radioLightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize views
        btnSkip = findViewById(R.id.firstBtnSkip);
        btnSubmit = findViewById(R.id.firstBtnSubmit);
        autoTvLanguage = findViewById(R.id.autoTxtLanguage);
        tvUserName = findViewById(R.id.txtInputUserName);
        radioDarkMode = findViewById(R.id.firstRadioDarkMode);
        radioLightMode = findViewById(R.id.firstRadioLightMode);

        // Set up autocomplete for language selection
        String[] nativeLanguages = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nativeLanguages);
        autoTvLanguage.setAdapter(autoAdapter);

        // Skip button click listener
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MainActivity on skip
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish current activity
            }
        });

        // Submit button click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String strLang = autoTvLanguage.getText().toString();
                String strUserName = tvUserName.getText().toString().trim();
                boolean isDarkMode = radioDarkMode.isChecked();

                // Store user preferences in Utils class
                Utils.nativeLanguage = strLang;
                Utils.userName = strUserName;
                Utils.isThemeNight = isDarkMode;

                // Navigate to MainActivity
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);

                // Apply theme change and finish current activity
                ChangeTheme(Utils.isThemeNight);
                finish();
            }
        });

        // Radio button listener for dark mode selection
        radioLightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Adjust text color based on selection
                if (isChecked) {
                    radioLightMode.setTextColor(Color.WHITE);
                    radioDarkMode.setTextColor(Color.GRAY);
                } else {
                    radioLightMode.setTextColor(Color.GRAY);
                    radioDarkMode.setTextColor(Color.WHITE);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Save user preferences to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor defaultEditor = defaultSharedPreferences.edit();

        editor.putString(Constants.KEY_PREF_USER_NAME, Utils.userName);
        editor.putString(Constants.TAG_PREF_NATIVE_LANGUAGE, Utils.nativeLanguage);
        editor.putBoolean(Constants.TAG_PREF_IS_THEME_DARK_MODE, Utils.isThemeNight);

        defaultEditor.putString(Constants.KEY_SETTINGS_SWITCH_LANGUAGE, Utils.nativeLanguage);
        defaultEditor.putString(Constants.KEY_PREF_USER_NAME, Utils.userName);
        defaultEditor.putBoolean(Constants.KEY_SETTINGS_SWITCH_THEME, Utils.isThemeNight);

        editor.apply(); // Apply changes
        defaultEditor.apply(); // Apply changes
    }

    /**
     * Changes the theme of the application based on the selected mode.
     *
     * @param isDarkMode Boolean indicating if dark mode is enabled.
     */
    public void ChangeTheme(boolean isDarkMode) {
        Utils.isThemeNight = isDarkMode;
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
