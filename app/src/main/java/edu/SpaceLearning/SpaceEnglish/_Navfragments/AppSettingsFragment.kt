/*
 * File: AppSettingsFragment.kt
 * Author: Anouar Oulhaj
 * Date: 2025-07-13
 * Purpose: Fragment for handling application settings in the SpaceEnglish app.
 *          This fragment includes preferences for app theme, language, privacy policy,
 *          and contacting support via email.
 */

package edu.SpaceLearning.SpaceEnglish._Navfragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import edu.SpaceLearning.SpaceEnglish.Listeners.ActivityFragmentInteractionListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils

/**
 * Fragment for managing application settings.
 * Provides options for theme switching, language selection,
 * privacy policy viewing, and contacting support via email.
 */
class AppSettingsFragment : PreferenceFragmentCompat() {

    private var settingsActionListener: ActivityFragmentInteractionListener? = null
    private lateinit var themeSwitchPref: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey)

        // Initialize preferences
        themeSwitchPref = findPreference(Constants.KEY_SETTINGS_SWITCH_THEME) ?: return
        val privacyPolicyPref = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_PRIVACY)
        val contactSupportPref = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_CONTACTUS)
        val languagePref = findPreference<ListPreference>(Constants.KEY_SETTINGS_SWITCH_LANGUAGE)

        // Language preference change listener
        languagePref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { x, newValue ->
               // val newLang = newValue.toString()
               /* Utils.nativeLanguage = when (newLang) {
                    Constants.LANGUAGE_NATIVE_ARABIC -> Constants.LANGUAGE_NATIVE_ARABIC
                    Constants.LANGUAGE_NATIVE_SPANISH -> Constants.LANGUAGE_NATIVE_SPANISH
                    else -> Constants.LANGUAGE_NATIVE_FRENCH
                }*/
                Utils.nativeLanguage = newValue.toString()
                // Optional: refresh the UI
                requireActivity().recreate()
                true
            }

        // Theme switch state and click listener
        themeSwitchPref.isChecked = Utils.isThemeNight
        themeSwitchPref.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Utils.isThemeNight = themeSwitchPref.isChecked
                settingsActionListener?.onThemeToggled(Utils.isThemeNight)
                true
            }

        // Privacy button listener
        privacyPolicyPref?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openPrivacyPolicyPage()
                true
            }

        // Contact email listener
        contactSupportPref?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                launchSupportEmailIntent()
                true
            }
    }

    /**
     * Launches an email intent to contact support.
     */
    private fun launchSupportEmailIntent() {
        val ourEmail = "oulhajfuturapps@gmail.com"
        val subject = "Enter the subject"
        val msg = "Enter your question please!"

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:$ourEmail".toUri()
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, msg)
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Choose Email App"))
        } else {
            Toast.makeText(requireActivity(), "No email app installed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Opens the privacy policy in the browser.
     */
    private fun openPrivacyPolicyPage() {
        val privacyUrl = "https://www.app-privacy-policy.com/live.php?token=s7KzNS1bghXl15LaXFmk5ifAT7v9ji9r"
        val intent = Intent(Intent.ACTION_VIEW, privacyUrl.toUri())
        startActivity(intent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityFragmentInteractionListener) {
            settingsActionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        settingsActionListener = null
    }
}
