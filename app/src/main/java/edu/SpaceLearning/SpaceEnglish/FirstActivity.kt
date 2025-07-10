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
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity

/**
 * FirstActivity is the initial activity where the user sets up preferences like language, username, and theme.
 */
class FirstActivity : AppCompatActivity() {
    private var btnSkip: Button? = null
    var btnSubmit: Button? = null
    var autoTvLanguage: AutoCompleteTextView? = null
    var tvUserName: TextInputEditText? = null
    var radioDarkMode: RadioButton? = null
    var radioLightMode: RadioButton? = null
    var sharedPreferences: SharedPreferences? = null
    var defaultSharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE)
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            this
        )

        // Initialize views
        btnSkip = findViewById(R.id.firstBtnSkip)
        btnSubmit = findViewById(R.id.firstBtnSubmit)
        autoTvLanguage = findViewById(R.id.autoTxtLanguage)
        tvUserName = findViewById(R.id.txtInputUserName)
        radioDarkMode = findViewById(R.id.firstRadioDarkMode)
        radioLightMode = findViewById(R.id.firstRadioLightMode)

        // Set up autocomplete for language selection
        val nativeLanguages = resources.getStringArray(R.array.settings_options_languages)
        val autoAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nativeLanguages)
        autoTvLanguage?.setAdapter(autoAdapter)

        // Skip button click listener
        btnSkip?.setOnClickListener{
            // Navigate to MainActivity on skip
            val intent : Intent = Intent(this@FirstActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish current activity
        }

        // Submit button click listener
        btnSubmit?.setOnClickListener(View.OnClickListener { // Retrieve user input
            val strLang = autoTvLanguage?.getText().toString()
            val strUserName = tvUserName?.getText().toString().trim { it <= ' ' }
            val isDarkMode = radioDarkMode?.isChecked()

            // Store user preferences in Utils class
            Utils.nativeLanguage = strLang
            Utils.userName = strUserName
           //--- Utils.isThemeNight = isDarkMode

            // Navigate to MainActivity
            val intent = Intent(this@FirstActivity, MainActivity::class.java)
            startActivity(intent)

            // Apply theme change and finish current activity
            ChangeTheme(Utils.isThemeNight)
            finish()
        })

        // Radio button listener for dark mode selection
        radioLightMode?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> // Adjust text color based on selection
            if (isChecked) {
                radioLightMode?.setTextColor(Color.WHITE)
                radioDarkMode?.setTextColor(Color.GRAY)
            } else {
                radioLightMode?.setTextColor(Color.GRAY)
                radioDarkMode?.setTextColor(Color.WHITE)
            }
        })
    }

    override fun onStop() {
        super.onStop()

        // Save user preferences to SharedPreferences
        val editor = sharedPreferences!!.edit()
        val defaultEditor = defaultSharedPreferences!!.edit()

        editor.putString(Constants.KEY_PREF_USER_NAME, Utils.userName)
        editor.putString(Constants.TAG_PREF_NATIVE_LANGUAGE, Utils.nativeLanguage)
        editor.putBoolean(Constants.TAG_PREF_IS_THEME_DARK_MODE, Utils.isThemeNight)

        defaultEditor.putString(Constants.KEY_SETTINGS_SWITCH_LANGUAGE, Utils.nativeLanguage)
        defaultEditor.putString(Constants.KEY_PREF_USER_NAME, Utils.userName)
        defaultEditor.putBoolean(Constants.KEY_SETTINGS_SWITCH_THEME, Utils.isThemeNight)

        editor.apply() // Apply changes
        defaultEditor.apply() // Apply changes
    }

    /**
     * Changes the theme of the application based on the selected mode.
     *
     * @param isDarkMode Boolean indicating if dark mode is enabled.
     */
    fun ChangeTheme(isDarkMode: Boolean) {
        Utils.isThemeNight = isDarkMode
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
