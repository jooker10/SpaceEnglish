package edu.SpaceLearning.SpaceEnglish.navfragments;

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

import edu.SpaceLearning.SpaceEnglish._Main.Constants;
import edu.SpaceLearning.SpaceEnglish.OnFragmentNavigationListener;
import edu.SpaceLearning.SpaceEnglish.R;
import edu.SpaceLearning.SpaceEnglish._Main.Utils;

public class SettingsNavFragment extends PreferenceFragmentCompat {

    private setOnChangeThemeListener themeListener;
    private OnFragmentNavigationListener navigationListener;
    private SwitchPreferenceCompat switchAppTheme;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_fragment_preferences, rootKey);

        Utils.fragmentNameTagSearch = "Settings";


        switchAppTheme = (SwitchPreferenceCompat) findPreference(Constants.KEY_SETTINGS_SWITCH_THEME);
        Preference btnPrivacy = findPreference(Constants.KEY_SETTINGS_BTN_PRIVACY);
        Preference btnContactEmail = findPreference(Constants.KEY_SETTINGS_BTN_CONTACTUS);
        ListPreference listSwitchLanguagePreference = (ListPreference) findPreference(Constants.KEY_SETTINGS_SWITCH_LANGUAGE);

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

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
            themeListener.onChangeTheme(Utils.isThemeNight);
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

    public interface setOnChangeThemeListener {
        void onChangeTheme(boolean isDarkMode);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof setOnChangeThemeListener)
            themeListener = (setOnChangeThemeListener) context;
        if(context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        themeListener = null;
    }
}