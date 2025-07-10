/*
 * File: SettingsNavFragment.java
 * Author: [Your Name]
 * Date: [Date]
 * Purpose: Fragment for handling application settings in the SpaceEnglish app.
 *          This fragment includes preferences for app theme, language, privacy policy,
 *          and contacting support via email.
 */

package edu.SpaceLearning.SpaceEnglish._Navfragments;

// Android imports
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import edu.SpaceLearning.SpaceEnglish.Listeners.InteractionActivityFragmentsListener;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Constants;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish.UtilsClasses.Utils;

/**
 * Fragment for managing application settings.
 * Provides options for theme switching, language selection,
 * privacy policy viewing, and contacting support via email.
 */
public class SettingsNavFragment extends PreferenceFragmentCompat {

    // Listener for interaction events with the parent activity
    private InteractionActivityFragmentsListener interactionListener;

    // Switch preference for toggling app theme
    private SwitchPreferenceCompat switchAppTheme;

    /**
     * Called during the fragment's creation to initialize preferences from XML.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @param rootKey If non-null, this is the key of the preference fragment that is being instantiated.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey);

        // Initialize UI components from preferences
        switchAppTheme = findPreference(Constants.KEY_SETTINGS_SWITCH_THEME);
        Preference btnPrivacy = findPreference(Constants.KEY_SETTINGS_BTN_PRIVACY);
        Preference btnContactEmail = findPreference(Constants.KEY_SETTINGS_BTN_CONTACTUS);
        ListPreference listSwitchLanguagePreference = findPreference(Constants.KEY_SETTINGS_SWITCH_LANGUAGE);

        // Set language change listener
        if (listSwitchLanguagePreference != null) {
            listSwitchLanguagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String txtValue = String.valueOf(newValue);
                if (txtValue.equals(Constants.LANGUAGE_NATIVE_ARABIC)) {
                    Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_ARABIC;
                } else if (txtValue.equals(Constants.LANGUAGE_NATIVE_SPANISH)) {
                    Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_SPANISH;
                } else {
                    Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_FRENCH;
                }
                return true;
            });
        }

        // Set initial state for app theme switch
        switchAppTheme.setChecked(Utils.isThemeNight);

        // Set click listener for app theme switch
        switchAppTheme.setOnPreferenceClickListener(preference -> {
            Utils.isThemeNight = switchAppTheme.isChecked();
            interactionListener.onChangeTheme(Utils.isThemeNight);
            return true;
        });

        // Set click listener for privacy policy button
        btnPrivacy.setOnPreferenceClickListener(preference -> {
            toPrivacy();
            return true;
        });

        // Set click listener for contact email button
        btnContactEmail.setOnPreferenceClickListener(preference -> {
            contactUsEmail();
            return true;
        });
    }

    /**
     * Opens the default email client to contact support via email.
     * Prompts user if no email client is available.
     */
    private void contactUsEmail() {
        String ourEmail = "oulhajfuturapps@gmail.com";
        String subject = "Enter the subject";
        String msg = "Enter your question please!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + ourEmail));
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), "No email app installed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens the privacy policy webpage in the default browser.
     */
    private void toPrivacy() {
        String privacySite = "https://www.app-privacy-policy.com/live.php?token=s7KzNS1bghXl15LaXFmk5ifAT7v9ji9r";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacySite));
        startActivity(intent);
    }

    /**
     * Called when the fragment is first attached to its context.
     * @param context The context to which the fragment is attached.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure the context implements InteractionActivityFragmentsListener
        if (context instanceof InteractionActivityFragmentsListener) {
            interactionListener = (InteractionActivityFragmentsListener) context;
        }
    }

    /**
     * Called when the fragment is no longer attached to its context.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        // Release the interaction listener to avoid memory leaks
        interactionListener = null;
    }
}
