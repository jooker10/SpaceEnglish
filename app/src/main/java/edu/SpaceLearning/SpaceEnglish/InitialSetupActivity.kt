package edu.SpaceLearning.SpaceEnglish

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.QuizUtils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity

/**
 * InitialSetupActivity is the initial activity where the user sets up preferences like language, username, and theme.
 */
class InitialSetupActivity : AppCompatActivity() {
    private var skipButton: Button? = null
    var submitButton: Button? = null
    var languageInput: AutoCompleteTextView? = null
    var userNameInput: TextInputEditText? = null
    var darkModeRadio: RadioButton? = null
    var lightModeRadio: RadioButton? = null
    var prefs: SharedPreferences? = null
    var defaultPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // Initialize SharedPreferences
        prefs = getSharedPreferences(Constants.SHARED_PREFS_FILE, MODE_PRIVATE)
        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(
            this
        )

        // Initialize views
        skipButton = findViewById(R.id.firstBtnSkip)
        submitButton = findViewById(R.id.firstBtnSubmit)
        languageInput = findViewById(R.id.autoTxtLanguage)
        userNameInput = findViewById(R.id.txtInputUserName)
        darkModeRadio = findViewById(R.id.firstRadioDarkMode)
        lightModeRadio = findViewById(R.id.firstRadioLightMode)

        // Set up autocomplete for language selection
        val nativeLanguages = resources.getStringArray(R.array.settings_options_languages)
        val autoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nativeLanguages)
        languageInput?.setAdapter(autoAdapter)

        // Skip button click listener
        skipButton?.setOnClickListener{
            // Navigate to MainActivity on skip
            val intent : Intent = Intent(this@InitialSetupActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish current activity
        }

        // Submit button click listener
        submitButton?.setOnClickListener(View.OnClickListener { // Retrieve user input
           // val strLang = languageInput?.getText().toString()
            val strUserName = userNameInput?.getText().toString().trim { it <= ' ' }
            val isDarkMode = darkModeRadio?.isChecked()

            // Store user preferences in QuizUtils class
           // QuizUtils.translationLanguage = strLang
            QuizUtils.userDisplayName = strUserName
           //--- QuizUtils.isThemeNight = isDarkMode

            // Navigate to MainActivity
            val intent = Intent(this@InitialSetupActivity, MainActivity::class.java)
            startActivity(intent)

            // Apply theme change and finish current activity
            changeAppTheme(QuizUtils.isDarkThemeEnabled)
            finish()
        })

        // Radio button listener for dark mode selection
        lightModeRadio?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> // Adjust text color based on selection
            if (isChecked) {
                lightModeRadio?.setTextColor(Color.WHITE)
                darkModeRadio?.setTextColor(Color.GRAY)
            } else {
                lightModeRadio?.setTextColor(Color.GRAY)
                darkModeRadio?.setTextColor(Color.WHITE)
            }
        })
    }

    override fun onStop() {
        super.onStop()

        // Save user preferences to SharedPreferences
        val editor = prefs!!.edit()
        val defaultEditor = defaultPrefs!!.edit()

        editor.putString(Constants.PREF_USERNAME, QuizUtils.userDisplayName)
       // editor.putString(Constants.PREF_NATIVE_LANGUAGE_DEFAULT, QuizUtils.userNativeLanguage)
        editor.putBoolean(Constants.PREF_IS_THEME_DARK_MODE, QuizUtils.isDarkThemeEnabled)

     //   defaultEditor.putString(Constants.SETTINGS_KEY_LANGUAGE_SWITCH, QuizUtils.userNativeLanguage)
        defaultEditor.putString(Constants.PREF_USERNAME, QuizUtils.userDisplayName)
        defaultEditor.putBoolean(Constants.SETTINGS_KEY_THEME_SWITCH, QuizUtils.isDarkThemeEnabled)

        editor.apply() // Apply changes
        defaultEditor.apply() // Apply changes
    }

    /**
     * Changes the theme of the application based on the selected mode.
     *
     * @param isDarkMode Boolean indicating if dark mode is enabled.
     */
    fun changeAppTheme(isDarkMode: Boolean) {
        QuizUtils.isDarkThemeEnabled = isDarkMode
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
