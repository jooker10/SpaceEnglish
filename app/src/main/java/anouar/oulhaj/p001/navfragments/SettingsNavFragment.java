package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import anouar.oulhaj.p001.Constants;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;

public class SettingsNavFragment extends PreferenceFragmentCompat {

    private setOnChangeThemeListener listener;
    private OnFragmentNavigationListener navigationListener;
    private SwitchPreferenceCompat switchAppTheme;
    public static final String KEY_SETTINGS_SWITCH_THEME = "switch_theme";
    public static final String KEY_SETTINGS_SWITCH_LANGUAGE = "switch_language";
    public static final String KEY_SETTINGS_BTN_PRIVACY = "btn_privacy";
    public static final String KEY_SETTINGS_BTN_CONTACTUS = "btn_contactus";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Utils.nameOfFragmentSearchView = "Settings";


        switchAppTheme = (SwitchPreferenceCompat) findPreference(KEY_SETTINGS_SWITCH_THEME);
        Preference btnPrivacy = findPreference(KEY_SETTINGS_BTN_PRIVACY);
        Preference btnContactEmail = findPreference(KEY_SETTINGS_BTN_CONTACTUS);
        ListPreference listPreference = (ListPreference) findPreference(KEY_SETTINGS_SWITCH_LANGUAGE);

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

        if (listPreference != null) {

            listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
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

        switchAppTheme.setOnPreferenceClickListener(preference -> {
            listener.onChangeTheme(switchAppTheme.isChecked());
            return true;
        });

        btnPrivacy.setOnPreferenceClickListener(preference -> {
            Toast.makeText(requireActivity(), "Go to Privacy", Toast.LENGTH_SHORT).show();
            return true;
        });
        btnContactEmail.setOnPreferenceClickListener(preference -> {
            Toast.makeText(requireActivity(), "Send an email", Toast.LENGTH_SHORT).show();
            return true;
        });


    }


    public interface setOnChangeThemeListener {
        void onChangeTheme(boolean isDarkMode);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof setOnChangeThemeListener)
            listener = (setOnChangeThemeListener) context;
        if(context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}