/*
 * File: SettingsNavFragment.kt
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
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils

/**
 * Fragment for managing application settings.
 * Provides options for theme switching, language selection,
 * privacy policy viewing, and contacting support via email.
 */
class SettingsNavFragment : PreferenceFragmentCompat() {

    private var interactionListener: InteractionActivityFragmentsListener? = null
    private lateinit var switchAppTheme: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey)

        // Initialize preferences
        switchAppTheme = findPreference(Constants.KEY_SETTINGS_SWITCH_THEME) ?: return
        val btnPrivacy = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_PRIVACY)
        val btnContactEmail = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_CONTACTUS)
        val languagePreference = findPreference<ListPreference>(Constants.KEY_SETTINGS_SWITCH_LANGUAGE)

        // Language preference change listener
        languagePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val newLang = newValue.toString()
                Utils.nativeLanguage = when (newLang) {
                    Constants.LANGUAGE_NATIVE_ARABIC -> Constants.LANGUAGE_NATIVE_ARABIC
                    Constants.LANGUAGE_NATIVE_SPANISH -> Constants.LANGUAGE_NATIVE_SPANISH
                    else -> Constants.LANGUAGE_NATIVE_FRENCH
                }
                // Optional: refresh the UI
                requireActivity().recreate()
                true
            }

        // Theme switch state and click listener
        switchAppTheme.isChecked = Utils.isThemeNight
        switchAppTheme.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Utils.isThemeNight = switchAppTheme.isChecked
                interactionListener?.onChangeTheme(Utils.isThemeNight)
                true
            }

        // Privacy button listener
        btnPrivacy?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openPrivacyPolicy()
                true
            }

        // Contact email listener
        btnContactEmail?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                sendSupportEmail()
                true
            }
    }

    /**
     * Launches an email intent to contact support.
     */
    private fun sendSupportEmail() {
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
    private fun openPrivacyPolicy() {
        val privacyUrl = "https://www.app-privacy-policy.com/live.php?token=s7KzNS1bghXl15LaXFmk5ifAT7v9ji9r"
        val intent = Intent(Intent.ACTION_VIEW, privacyUrl.toUri())
        startActivity(intent)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        interactionListener = null
    }
}
