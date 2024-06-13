package edu.SpaceLearning.SpaceEnglish._Navfragments;

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

public class SettingsNavFragment extends PreferenceFragmentCompat {

    private InteractionActivityFragmentsListener interactionListener;
    private SwitchPreferenceCompat switchAppTheme;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey);


        switchAppTheme = (SwitchPreferenceCompat) findPreference(Constants.KEY_SETTINGS_SWITCH_THEME);
        Preference btnPrivacy = findPreference(Constants.KEY_SETTINGS_BTN_PRIVACY);
        Preference btnContactEmail = findPreference(Constants.KEY_SETTINGS_BTN_CONTACTUS);
        ListPreference listSwitchLanguagePreference = (ListPreference) findPreference(Constants.KEY_SETTINGS_SWITCH_LANGUAGE);


        if (listSwitchLanguagePreference != null) {

            listSwitchLanguagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String txtValue = String.valueOf(newValue);
                if(txtValue.equals(Constants.LANGUAGE_NATIVE_ARABIC)) {
                    Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_ARABIC;
                } else if (txtValue.equals(Constants.LANGUAGE_NATIVE_SPANISH)) {
                    Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_SPANISH;
                }
                else Utils.nativeLanguage = Constants.LANGUAGE_NATIVE_FRENCH;
                return true;
            });
        }
        switchAppTheme.setChecked(Utils.isThemeNight);

        switchAppTheme.setOnPreferenceClickListener(preference -> {
            Utils.isThemeNight = switchAppTheme.isChecked();
            interactionListener.onChangeTheme(Utils.isThemeNight);
            return true;
        });

        btnPrivacy.setOnPreferenceClickListener(preference -> {
            toPrivacy();
            return true;
        });
        btnContactEmail.setOnPreferenceClickListener(preference -> {
            contactUsEmail();
            return true;
        });


    }

    private void contactUsEmail(){
        String ourEmail = "oulhajfuturapps@gmail.com";
        String subject = "Enter the subject";
        String msg = "Enter your question please!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+ourEmail));
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        if(intent.resolveActivity(requireActivity().getPackageManager()) != null){
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), "No email app installed", Toast.LENGTH_SHORT).show();
        }

    }
    private void toPrivacy(){
        String privacySite = "https://www.app-privacy-policy.com/live.php?token=s7KzNS1bghXl15LaXFmk5ifAT7v9ji9r";
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(privacySite));
        startActivity(intent);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InteractionActivityFragmentsListener)
            interactionListener = (InteractionActivityFragmentsListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(interactionListener != null)
        interactionListener = null;
    }
}