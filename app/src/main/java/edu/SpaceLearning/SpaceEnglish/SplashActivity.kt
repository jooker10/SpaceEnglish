package edu.SpaceLearning.SpaceEnglish

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var defaultSharedPrefs: SharedPreferences
    private lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize Mobile Ads SDK in a background thread
        Thread {
            MobileAds.initialize(
                this
            ) { initializationStatus: InitializationStatus? -> }
        }.start()

        // Initialize SharedPreferences and SharedPrefsManager
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE)
        defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPrefsManager = SharedPrefsManager(sharedPreferences)

        // Retrieve and apply the saved theme
       //____ sharedPrefsManager!!.getSharedPrefTheme()
        onChangeTheme(Utils.isThemeNight)

        // Start animation for splash icon
        val iconSplash = findViewById<ImageView>(R.id.img_splash_icon)
        iconSplash.animation =
            AnimationUtils.loadAnimation(
                this,
                R.anim.splash_icon_anim
            )

        // Check if initial configuration is completed
        val isInitialConfigurationCompleted =
            defaultSharedPrefs.getBoolean(Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY, false)

        // Delay for 2 seconds before proceeding to next activity
        Handler().postDelayed({
            if (isInitialConfigurationCompleted) {
                // If initial configuration is completed, load MainActivity
                loadMainActivity()
            } else {
                // If initial configuration is not completed, load FirstActivity
                val editor = defaultSharedPrefs.edit()
                editor.putBoolean(
                    Constants.TAG_PREF_IS_FIRST_TIME_ACTIVITY,
                    true
                )
                editor.apply()
                loadFirstActivity()
            }
        }, 2000)
    }

    private fun loadFirstActivity() {
        val intent = Intent(this@SplashActivity, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onChangeTheme(isDarkMode: Boolean) {
        Utils.isThemeNight = isDarkMode
        if (isDarkMode) {
            // Apply night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Apply day mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
