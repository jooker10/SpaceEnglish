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
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import edu.SpaceLearning.SpaceEnglish.Adapters.RecyclerViewAdapter
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess
import edu.SpaceLearning.SpaceEnglish.DataBaseFiles.DbAccess.Companion.getInstance
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment
import edu.SpaceLearning.SpaceEnglish.DialogQuizFragment.Companion.newInstance
import edu.SpaceLearning.SpaceEnglish.Listeners.AdsClickListener
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.MyBottomSheet
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.AdsManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.CustomToast.showToast
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.RatingManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SharedPrefsManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.SoundManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.TextToSpeechManager
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils.FillHashMapTableName
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils.FillItemRecyclerQuizNavList
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils.FillListCategoriesNames
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils.FillListHeaderTablePdf
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils.FillListsCorrectIncorrectAnswerResponses
import edu.SpaceLearning.SpaceEnglish._Navfragments.HomeNavFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.QuizNavFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.SettingsNavFragment
import edu.SpaceLearning.SpaceEnglish._Navfragments.TableNavFragment
import edu.SpaceLearning.SpaceEnglish.databinding.ActivityMainBinding
import java.io.File
import java.util.Objects

class MainActivity : AppCompatActivity(), InteractionActivityFragmentsListener, AdsClickListener {
    // Views and UI related variables
    private lateinit var binding: ActivityMainBinding

    // Data management
    private lateinit var adsManager: AdsManager
    private lateinit var soundManager: SoundManager
    private lateinit var ratingManager: RatingManager
    private lateinit var dbAccess: DbAccess
    private lateinit var sharedPrefsManager: SharedPrefsManager


    // utilities
    private lateinit var tableRecyclerAdapter: RecyclerViewAdapter
    private lateinit var myBottomSheet: MyBottomSheet
    private lateinit var sharedPreferences: SharedPreferences


    // Flags and state variables
    private var showRatingSheet = true
    private var isTableFragmentActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.customToolbar) // Set custom ToolBar

        // Initialize utility methods and managers
        initUtils()
        initDatabaseAccess()
        initSharedPreferences()
        initTextToSpeech()
        initSoundManager()
        initRatingMyAppManager()
        initAdmobManager()

        binding.mainNavFab.setOnClickListener { v: View? -> ShowMainBottomSheet() } // Main Fab Listener

        setBottomMainNavWithMenu() // Set up bottom navigation and initial fragment
    }

    private fun initUtils() {
        // Initialize static utility methods in Utils class
        FillItemRecyclerQuizNavList()
        FillListHeaderTablePdf()
        FillHashMapTableName()
        FillListCategoriesNames()
        FillListsCorrectIncorrectAnswerResponses()
    }

    private fun initDatabaseAccess() {
        // Initialize DbAccess on a separate thread
        val dataBaseThread = Thread {
            dbAccess = getInstance(baseContext)
            // dbAccess.open_to_read();
            dbAccess.dBListCategorySize
        }
        dataBaseThread.start()
        try {
            dataBaseThread.join()
        } catch (e: InterruptedException) {
            Log.e("MainActivity", dataBaseThread.name + " : Failed to join thread")
        }
    }

    private fun initSharedPreferences() {
        // Initialize SharedPreferences and SharedPrefsManager
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS_FILE_NAME, MODE_PRIVATE)
        sharedPrefsManager = SharedPrefsManager(sharedPreferences)
        sharedPrefsManager.sharedPreferencesData
    }

    private fun initTextToSpeech() {
        // Initialize TextToSpeechManager
        textToSpeechManager = TextToSpeechManager(
            this
        ) { status: Int -> }
    }

    private fun initSoundManager() {
        // Initialize SoundManager
        soundManager = SoundManager()
    }

    private fun initRatingMyAppManager() {
        // Initialize RatingManager
        ratingManager = RatingManager(this)
        ratingManager.requestReviewInfo()
    }

    private fun initAdmobManager() {
        // Initialize AdsManager
        adsManager = AdsManager(this)
    }

    private fun setBottomMainNavWithMenu() {
        // Set icons and initial fragment for bottom navigation
        binding.bottomNav.menu.findItem(R.id.item_nav_home)
            .setIcon(R.drawable.selector_home_nav_change_icon)
        binding.bottomNav.menu.findItem(R.id.item_nav_tables)
            .setIcon(R.drawable.selector_table_nav_change_icon)
        binding.bottomNav.menu.findItem(R.id.item_nav_quiz)
            .setIcon(R.drawable.selector_quiz_nav_change_icon)
        binding.bottomNav.menu.findItem(R.id.item_nav_settings)
            .setIcon(R.drawable.selector_settings_nav_change_icon)

        // Set initial fragment
        setNavFragment(HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT)

        // Handle bottom navigation item selection
        binding.bottomNav.setOnItemSelectedListener { item: MenuItem ->
            Objects.requireNonNull(
                supportActionBar
            )?.setDisplayHomeAsUpEnabled(false)
            var selectedFragment: Fragment? = null
            var fragmentTAG: String? = null

            when (item.itemId) {
                R.id.item_nav_home -> {
                    fragmentTAG =
                        Constants.TAG_HOME_NAV_FRAGMENT
                    selectedFragment = HomeNavFragment()
                }

                R.id.item_nav_tables -> {
                    fragmentTAG =
                        Constants.TAG_TABLES_NAV_FRAGMENT
                    selectedFragment = TableNavFragment()
                }

                R.id.item_nav_quiz -> {
                    fragmentTAG =
                        Constants.TAG_QUIZ_NAV_FRAGMENT
                    selectedFragment = QuizNavFragment()
                }

                R.id.item_nav_settings -> {
                    fragmentTAG =
                        Constants.TAG_SETTINGS_NAV_FRAGMENT
                    selectedFragment = SettingsNavFragment()
                }
            }

            if (selectedFragment != null) {
                setNavFragment(selectedFragment, fragmentTAG!!)
            }
            true
        }

        // Handle bottom navigation item reselection
        binding.bottomNav.setOnItemReselectedListener { item: MenuItem? ->
            showToast(
                this,
                "Already selected"
            )
        }
    }

    private fun ShowMainBottomSheet() {
        // Show bottom sheet dialog
        if (!myBottomSheet.isVisible) {
            myBottomSheet = MyBottomSheet()
            myBottomSheet.show(supportFragmentManager, MyBottomSheet.SHEET_TAG)
        }
    }

    private fun setNavFragment(fragment: Fragment, fragmentTAG: String) {
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
    override fun onHomeGetStarted(index: Int) {
        // Handle navigation to TableNavFragment or QuizNavFragment from HomeNavFragment
        if (index == Constants.TABLE_NAV_INDEX) {
            setNavFragment(TableNavFragment(), Constants.TAG_TABLES_NAV_FRAGMENT)
            binding.bottomNav.menu.getItem(Constants.TABLE_NAV_INDEX).setChecked(true)
        } else {
            setNavFragment(QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT)
            binding.bottomNav.menu.getItem(Constants.QUIZ_NAV_INDEX).setChecked(true)
        }
    }

    /*override fun onPickImageProfile() {
        TODO("Not yet implemented")
    }*/

    /*override fun onPickImageProfile() {
        // Handle picking image for profile
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                ImagePicker.REQUEST_CODE
            )
        } else {
            // Permission granted, start image picking
            startImagePicker()
        }
    }*/

   /* private fun startImagePicker() {
        // Start image picker library
        ImagePicker.with(this)
            .galleryOnly()
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(ImagePicker.REQUEST_CODE)
    }
*/
    override fun onShowInterstitialAd() {
        // Handle showing interstitial ad
        adsManager.showInterstitialAd()
        adsManager.reLoadInterstitialAd()
    }

    override fun onShowRewardedAd() {
        // Handle showing rewarded ad
        adsManager.showRewardedAd()
        adsManager.reloadRewardedAd()
    }

    override fun onSendScoresToDialog(
        categoryType: String,
        pointsAdded: Int,
        elementsAdded: Int,
        userRightAnswerScore: Int,
        msg: String
    ) {
        // Handle sending scores to DialogQuizFragment
        val dialog5 =
            newInstance(categoryType, pointsAdded, elementsAdded, userRightAnswerScore, msg)
        dialog5.show(supportFragmentManager, DialogQuizFragment.TAG)
    }

   /* override fun onChangeTheme(isDarkMode: Boolean) {
        // Handle changing app theme
        Utils.isThemeNight = isDarkMode
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        binding.bottomNav.menu[Constants.HOME_NAV_INDEX].setChecked(true)
    }*/

    override fun onDialogSendHomeClick(categoryType: String) {
        // Handle sending home from dialog
        setNavFragment(HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT)
        binding.bottomNav.menu.getItem(Constants.HOME_NAV_INDEX).setChecked(true)
    }

    override fun onDialogNewQuiz() {
        // Handle starting new quiz from dialog
        setNavFragment(QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT)
        binding.bottomNav.menu.getItem(Constants.QUIZ_NAV_INDEX).setChecked(true)
    }

    /*override fun onSetRequiredCategoryFragmentQuiz(fragment: Fragment?) {
        TODO("Not yet implemented")
    }*/

    /*override fun openPdfWithIntent(context: Context, pdfFile: File?) {
        TODO("Not yet implemented")
    }
*/
  /*  override fun onSetRequiredCategoryFragmentQuiz(fragment: Fragment) {
        // Handle setting required category fragment for quiz
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)
        setNavFragment(fragment, Constants.TAG_QUIZ_CATEGORY_FRAGMENT)
    }*/

     fun openPdfWithIntent(context: Context?, pdfFile: File) {
        // Handle opening PDF file with Intent
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission granted, proceed to open PDF
            openPdfFile(pdfFile)
        }
    }

   /* override fun onFilterTableRecycler(tableAdapter: RecyclerViewAdapter) {
        // Handle filtering table RecyclerView
        if (tableAdapter != null) {
            this.tableRecyclerAdapter = tableAdapter
        }
    }
*/
    override fun onShowVideoAds(categoryType: String) {
        // Handle showing video ads and navigating to TableNavFragment
        dbAccess.dBListCategorySize
        setNavFragment(TableNavFragment(), Constants.TAG_TABLES_NAV_FRAGMENT)
        binding.bottomNav.menu.getItem(Constants.TABLE_NAV_INDEX).setChecked(true)
    }

    // Menu and Permissions Handling
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // Get the SearchView from the menu item
        val searchItem = menu.findItem(R.id.mainmenu_search)

        // Show or hide SearchView based on fragment visibility
        if (isTableFragmentActive) {
            searchItem.setVisible(true)
        } else {
            searchItem.setVisible(false)
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
                tableRecyclerAdapter.filter.filter(newText)
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
            contactUsEmail()
        } else if (id == R.id.menu_main_rating_app) {
            // Handle rating app action
            ratingManager.showReviewFlow()
        } else if (id == android.R.id.home) {
            // Handle Up button action
            setNavFragment(QuizNavFragment(), Constants.TAG_QUIZ_NAV_FRAGMENT)
            Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun contactUsEmail() {
        // Handle sending email for contact us
        val ourEmail = "oulhajfuturapps@gmail.com"
        val subject = "Enter the subject"
        val msg = "Enter your question please!"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:$ourEmail"))
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            showToast(this, "No email app installed")
        }
    }

    override fun onStop() {
        // Save preferences and cleanup
        super.onStop()
        val editor = sharedPreferences.edit()
        sharedPrefsManager.putSharedPreferencesScores(editor)
        sharedPrefsManager.putSharedPrefTheme(editor)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // Handle permission request results
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        /*if (requestCode == ImagePicker.REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImagePicker()
            }
        }*/
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open PDF
                Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Handle activity result, particularly for image picker
        super.onActivityResult(requestCode, resultCode, data)
       /* if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Utils.uriProfile = data.data
            setNavFragment(HomeNavFragment(), Constants.TAG_HOME_NAV_FRAGMENT)
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup on activity destroy
        if (soundManager != null) {
            soundManager.release()
        }
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
            ratingManager.showReviewFlow()
            showRatingSheet = false
        }
    }

    private fun openPdfFile(pdfFile: File) {
        // Open PDF file with Intent
        if (pdfFile.exists()) {
            val uri = FileProvider.getUriForFile(
                this,
                "$packageName.provider", pdfFile
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val pm = packageManager
            if (intent.resolveActivity(pm) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Downloaded, please check the following path: " + pdfFile.path,
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            // Handle file not found
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200

        var textToSpeechManager: TextToSpeechManager? = null
    }
}
