/*
 * MainActivity.java
 * Space English Learning App
 *
 * Main activity of the Space English Learning App, responsible for managing navigation,
 * user interactions, and displaying various fragments based on user actions.
 * Includes functionalities like bottom navigation, fragment transactions, menu handling,
 * permissions management, and interaction with databases and utilities.
 *
 * This activity initializes necessary components such as database access, shared preferences,
 * ads management, sound management, and text-to-speech services. It also handles user
 * interactions through implemented interfaces for fragment communications and click events.
 *
 * The app supports features like image picking, displaying PDF files, showing ads, and managing
 * app themes dynamically based on user preferences.
 *
 * Authors:
 * - [Your Name]
 * - [Co-author's Name] (if applicable)
 *
 * Date: [Date of creation or last modification]
 * Version: [App version number]
 */
package edu.SpaceLearning.SpaceEnglish._Main

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerTableAdapter
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DatabaseManager
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DatabaseManager.Companion.getInstance
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment.Companion.newInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.ActivityFragmentInteractionListener
import edu.SpaceLearning.SpaceEnglish.LoginActivity
import edu.SpaceLearning.SpaceEnglish.MyBottomSheet
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.AdsManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.CustomToast.showToast
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.RatingManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import edu.SpaceLearning.SpaceEnglish._Navfragments.HomeNavFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.QuizNavigationFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.AppSettingsFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.TableNavigationFragment
import edu.SpaceLearning.SpaceEnglish.databinding.ActivityMainBinding
import java.io.File
import java.util.Objects

class MainActivity : AppCompatActivity(), ActivityFragmentInteractionListener, AdsClickListener {
    // Views and UI related variables
    private lateinit var binding: ActivityMainBinding
    // Data management
    private var adsManager: AdsManager? = null
      var soundManager: SoundManager? = null
    private  var ratingManager: RatingManager? = null
    private lateinit var databaseManager: DatabaseManager
    private lateinit var sharedPrefsManager: SharedPrefsManager

    // utilities
    private var tableRecyclerAdapter: RecyclerTableAdapter? = null
    private var myBottomSheet: MyBottomSheet? = null
    private lateinit var sharedPreferences: SharedPreferences


    // Flags and state variables
    private var showRatingSheet = true
    private var isTableFragmentActive = false

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200

        var textToSpeechManager: TextToSpeechManager? = null
        //var soundManager : SoundManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setSupportActionBar(binding.customToolbar) // Set custom ToolBar



        // Initialize utility methods and managers
        initializeUtilityData()
        initializeDatabaseManager()
        initializeSharedPreferences()
        initializeTextToSpeechManager()
        initializeSoundSystem()
        initializeRatingManager()
        initializeAdManager()

        binding.mainNavFab.setOnClickListener { v: View? -> displayMainBottomSheet() } // Main Fab TimerListener
        binding.btnSignOutGoogle.setOnClickListener { performGoogleSignOut() } // FireBase auth google sign-out

        setupBottomNavigation() // Set up bottom navigation and initial fragment
    }

    private fun initializeUtilityData() {
        // Initialize static utility methods in Utils class

        Utils.fillListOfCategoriesNames()
        Utils.headerTablePdfNames()
        Utils.fillQuizListsCorrectIncorrectAnswerResponses()
        Utils.fillItemRecyclerQuizNavList()
        Utils.fillHashMapDbTableName()
    }



    private fun initializeDatabaseManager() {
        // Initialize DatabaseManager on a separate thread
        val dataBaseThread = Thread {
            databaseManager = getInstance(baseContext)
                // databaseManager.open_to_read();
            databaseManager.updateCategorySizes

        }
        dataBaseThread.start()
        try {
            dataBaseThread.join()
        } catch (e: InterruptedException) {
            Log.e("MainActivity", dataBaseThread.name + " : Failed to join thread")
        }
    }

    private fun initializeSharedPreferences() {
        // Initialize SharedPreferences and SharedPrefsManager
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE)
        sharedPrefsManager = SharedPrefsManager(sharedPreferences).also {
           it.sharedPreferencesData
        }
    }

    private fun initializeTextToSpeechManager() {
        // Initialize TextToSpeechManager
        textToSpeechManager = TextToSpeechManager(
            this
        ) { status: Int -> }
    }

    private fun initializeSoundSystem() {
        // Initialize SoundManager
        soundManager = SoundManager()
    }

    private fun initializeRatingManager() {
        // Initialize RatingManager
        ratingManager = RatingManager(this).also {
            it.requestReviewInfo()
        }
    }

    private fun initializeAdManager() {
        // Initialize AdsManager
        adsManager = AdsManager(this)
    }

    private fun performGoogleSignOut() {
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity :: class.java)
        startActivity(intent)
        finish()
    }


   private fun setupBottomNavigation() {
       // Set icons for bottom navigation items
       val iconMap = mapOf(
           R.id.item_nav_home to R.drawable.selector_home_nav_change_icon,
           R.id.item_nav_tables to R.drawable.selector_table_nav_change_icon,
           R.id.item_nav_quiz to R.drawable.selector_quiz_nav_change_icon,
           R.id.item_nav_settings to R.drawable.selector_settings_nav_change_icon
       )

       iconMap.forEach { (menuId, iconRes) ->
           binding.bottomNav.menu.findItem(menuId).setIcon(iconRes)
       }

       // Set initial fragment
       navigateToFragment(HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT)

       // Handle bottom navigation item selection
       binding.bottomNav.setOnItemSelectedListener { item ->
           supportActionBar?.setDisplayHomeAsUpEnabled(false)

           val (fragment, tag) = when (item.itemId) {
               R.id.item_nav_home -> HomeNavFragment() to Constants.TAG_HOME_NAV_FRAGMENT
               R.id.item_nav_tables -> TableNavigationFragment() to Constants.TAG_TABLES_NAV_FRAGMENT
               R.id.item_nav_quiz -> QuizNavigationFragment() to Constants.TAG_QUIZ_NAV_FRAGMENT
               R.id.item_nav_settings -> AppSettingsFragment() to Constants.TAG_SETTINGS_NAV_FRAGMENT
               else -> null to null
           }

           fragment?.let {
               navigateToFragment(it, tag!!)
           }

           true
       }

       // Handle reselection
       binding.bottomNav.setOnItemReselectedListener {
           showToast(this, "Already selected")
       }
   }


    private fun displayMainBottomSheet() {
        if (myBottomSheet == null || myBottomSheet?.isVisible == false) {
            myBottomSheet = MyBottomSheet()
            myBottomSheet?.show(supportFragmentManager, MyBottomSheet.SHEET_TAG)
        }
    }

    private fun navigateToFragment(fragment: Fragment, fragmentTAG: String) {
        // Set fragment in fragment container with custom animations
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right,
            R.anim.fragment_enter_to_right, R.anim.fragment_exit_to_right
        )
        ft.replace(R.id.main_frag_container, fragment, fragmentTAG)
        ft.commit()

        // Update isTableFragmentActive based on the fragment TAG
        isTableFragmentActive = fragmentTAG == Constants.TAG_TABLES_NAV_FRAGMENT
    }

    // Interface methods implementations
    override fun onHomeStartClicked(index: Int) {
        val (fragment, tag) = when (index) {
            Constants.TABLE_NAV_INDEX -> TableNavigationFragment() to Constants.TAG_TABLES_NAV_FRAGMENT
            else -> QuizNavigationFragment() to Constants.TAG_QUIZ_NAV_FRAGMENT
        }

        navigateToFragment(fragment, tag)
        binding.bottomNav.menu[index].isChecked = true
    }

    override fun onShowInterstitialAd() {
        // Handle showing interstitial ad
        adsManager?.showInterstitialAd()
        adsManager?.reLoadInterstitialAd()
    }

    override fun onShowRewardedAd() {
        // Handle showing rewarded ad
        adsManager?.showRewardedAd()
        adsManager?.reloadRewardedAd()
    }

    override fun onScoresSubmittedToDialog(categoryType: String, pointsAdded: Int, elementsAdded: Int, userRightAnswerScore: Int, msg: String) {
        // Handle sending scores to DialogQuizFragment
        newInstance(categoryType, pointsAdded, elementsAdded, userRightAnswerScore, msg)
            .show(supportFragmentManager, DialogQuizFragment.TAG)
    }

    override fun onThemeToggled(isDarkMode: Boolean) {
        // Handle changing app theme
        Utils.isThemeNight = isDarkMode
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        binding.bottomNav.menu[Constants.HOME_NAV_INDEX].isChecked = true
    }

    override fun onDialogReturnHomeClicked(categoryType: String) {
        // Handle sending home from dialog
        navigateToFragment(HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT)
        binding.bottomNav.menu[Constants.HOME_NAV_INDEX].isChecked = true
    }

    override fun onStartNewQuizClicked() {
        // Handle starting new quiz from dialog
        navigateToFragment(QuizNavigationFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT)
        binding.bottomNav.menu[Constants.QUIZ_NAV_INDEX].isChecked = true
    }

    override fun onSetQuizCategoryFragment(fragment: Fragment) {
        // Handle setting required category fragment for quiz
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        navigateToFragment(fragment, Constants.TAG_QUIZ_CATEGORY_FRAGMENT)
    }

     override fun onPdfOpenRequested(context: Context, pdfFile: File?) {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
             != PackageManager.PERMISSION_GRANTED
         ) {
             ActivityCompat.requestPermissions(
                 this,
                 arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                 PERMISSION_REQUEST_CODE
             )
         } else {
             openPdfFile(pdfFile)
         }
     }

     override fun onTableFilterRequested(tableRecyclerAdapter: RecyclerTableAdapter) {
        // Handle filtering table RecyclerView
         this@MainActivity.tableRecyclerAdapter = tableRecyclerAdapter
    }

     override fun onShowVideoAds(categoryType: String) {
        // Handle showing video ads and navigating to TableNavigationFragment
        databaseManager.updateCategorySizes
        navigateToFragment(TableNavigationFragment(), Constants.TAG_TABLES_NAV_FRAGMENT)
       binding.bottomNav.menu[Constants.TABLE_NAV_INDEX].isChecked = true
    }

    // Menu and Permissions Handling
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // Get the SearchView from the menu item
        val searchItem = menu.findItem(R.id.mainmenu_search)

        // Show or hide SearchView based on fragment visibility
        if (isTableFragmentActive) {
            searchItem.isVisible = true
        } else {
            searchItem.isVisible = false
        }

        // Initialize SearchView
        val searchView = checkNotNull(searchItem.actionView as SearchView?)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission if needed
                return false // Return true to consume the event
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle search query text changes
                tableRecyclerAdapter?.filter?.filter(newText)
                return true // Return true to consume the event
            }
        })

        return true // Return true to indicate menu creation is handled
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here
        val id = item.itemId
        if (id == R.id.menu_main_contact_us) {
            // Handle contact us action
            sendContactEmail()
        } else if (id == R.id.menu_main_rating_app) {
            // Handle rating app action
            ratingManager?.showReviewFlow()
        } else if (id == android.R.id.home) {
            // Handle Up button action
            navigateToFragment(QuizNavigationFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT)
            Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendContactEmail() {
        // Handle sending email for contact us
        val ourEmail = "oulhajfuturapps@gmail.com"
        val subject = "Enter the subject"
        val msg = "Enter your question please!"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData("mailto:$ourEmail".toUri())
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showToast(this, "No email app installed")
        }
    }

    private fun openPdfFile(pdfFile: File?) {
        if (pdfFile == null || !pdfFile.exists()) {
            runOnUiThread {
                Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show()
            }
            return
        }

        runOnUiThread {
            Toast.makeText(
                this,
                "PDF saved. Check file at:\n${pdfFile.path}",
                Toast.LENGTH_LONG
            ).show()
        }

        try {
            val uri = FileProvider.getUriForFile(
                this,
                "$packageName.provider", pdfFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "No app found to open PDF.\nYou can open it manually from:\n${pdfFile.path}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        } catch (e: Exception) {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Error opening PDF. File saved at:\n${pdfFile.path}",
                    Toast.LENGTH_LONG
                ).show()
            }
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // Handle permission request results
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open PDF
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    override fun onStop() {
        // Save preferences and cleanup
        super.onStop()
        val editor = sharedPreferences.edit()
        sharedPrefsManager.putSharedPreferencesScores(editor)
        sharedPrefsManager.putSharedPrefTheme(editor)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup on activity destroy
        soundManager?.release()
    }

    override fun onBackPressed() {
        // Handle back button press
        if (!showRatingSheet) {
            // Show confirmation dialog to exit app
            val alertDialog = AlertDialog.Builder(this@MainActivity)
            alertDialog.setTitle("Exit App")
            alertDialog.setMessage("Do you want to exit the app?")
            alertDialog.setPositiveButton(
                "Yes"
            ) { dialog: DialogInterface?, which: Int ->
                super.onBackPressed()
            }
            alertDialog.setNegativeButton(
                "No"
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }
            alertDialog.show()
        } else {
            // Show rating flow
            ratingManager?.showReviewFlow()
            showRatingSheet = false
        }
    }

}
