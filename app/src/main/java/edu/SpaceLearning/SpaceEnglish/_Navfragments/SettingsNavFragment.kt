/*
 * File: SettingsNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for handling application settings in the SpaceEnglish app.
 *          This fragment includes preferences for app theme, language, privacy policy,
 *          and contacting support via email.
 */
package edu.SpaceLearning.SpaceEnglish._Navfragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener
import edu.SpaceLearning.SpaceEnglish.R
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils
import androidx.core.net.toUri

// Android imports

/**
 * Fragment for managing application settings.
 * Provides options for theme switching, language selection,
 * privacy policy viewing, and contacting support via email.
 */
class SettingsNavFragment : PreferenceFragmentCompat() {
    // Listener for interaction events with the parent activity
    private var interactionListener: InteractionActivityFragmentsListener? = null

    // Switch preference for toggling app theme
    private var switchAppTheme: SwitchPreferenceCompat? = null

    /**
     * Called during the fragment's creation to initialize preferences from XML.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @param rootKey If non-null, this is the key of the preference fragment that is being instantiated.
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey)

        // Initialize UI components from preferences
        switchAppTheme = findPreference(Constants.KEY_SETTINGS_SWITCH_THEME)
        val btnPrivacy = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_PRIVACY)
        val btnContactEmail = findPreference<Preference>(Constants.KEY_SETTINGS_BTN_CONTACTUS)
        val listSwitchLanguagePreference =
            findPreference<ListPreference>(Constants.KEY_SETTINGS_SWITCH_LANGUAGE)

        // Set language change listener
        if (listSwitchLanguagePreference != null) {
            listSwitchLanguagePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference: Preference?, newValue: Any ->
                    val txtValue = newValue.toString()
                    if (txtValue == Constants.LANGUAGE_NATIVE_ARABIC) {
                        Utils.nativeLanguage =
                            Constants.LANGUAGE_NATIVE_ARABIC
                    } else if (txtValue == Constants.LANGUAGE_NATIVE_SPANISH) {
                        Utils.nativeLanguage =
                            Constants.LANGUAGE_NATIVE_SPANISH
                    } else {
                        Utils.nativeLanguage =
                            Constants.LANGUAGE_NATIVE_FRENCH
                    }
                    true
                }
        }

        // Set initial state for app theme switch
        switchAppTheme?.let {
            it.isChecked = Utils.isThemeNight
            it.onPreferenceClickListener =
                Preference.OnPreferenceClickListener { preference: Preference? ->
                    Utils.isThemeNight =
                        it.isChecked
                    // interactionListener!!.onChangeTheme(Utils.isThemeNight)
                    true
                }
        }
       /* switchAppTheme?.isChecked = Utils.isThemeNight

        // Set click listener for app theme switch
        switchAppTheme?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { preference: Preference? ->
                Utils.isThemeNight =
                    switchAppTheme!!.isChecked
               // interactionListener!!.onChangeTheme(Utils.isThemeNight)
                true
            }*/

        // Set click listener for privacy policy button
        btnPrivacy!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { preference: Preference? ->
                toPrivacy()
                true
            }

        // Set click listener for contact email button
        btnContactEmail!!.onPreferenceClickListener =
            Preference.OnPreferenceClickListener { preference: Preference? ->
                contactUsEmail()
                true
            }
    }

    /**
     * Opens the default email client to contact support via email.
     * Prompts user if no email client is available.
     */
    private fun contactUsEmail() {
        val ourEmail = "oulhajfuturapps@gmail.com"
        val subject = "Enter the subject"
        val msg = "Enter your question please!"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData("mailto:$ourEmail".toUri())
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireActivity(), "No email app installed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Opens the privacy policy webpage in the default browser.
     */
    private fun toPrivacy() {
        val privacySite =
            "https://www.app-privacy-policy.com/live.php?token=s7KzNS1bghXl15LaXFmk5ifAT7v9ji9r"
        val intent = Intent(Intent.ACTION_VIEW, privacySite.toUri())
        startActivity(intent)
    }

    /**
     * Called when the fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context is InteractionActivityFragmentsListener) {
            interactionListener = context
        }
    }

    /**
     * Called when the fragment is no longer attached to its context.
     */
    override fun onDetach() {
        super.onDetach()
        // Release the interaction listener to avoid memory leaks
        interactionListener = null
    }
}
