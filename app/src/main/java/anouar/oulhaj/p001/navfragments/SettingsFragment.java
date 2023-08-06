package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import anouar.oulhaj.p001.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private setOnChangeThemeListener listener;
    private SwitchPreferenceCompat switch_darkMode;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        switch_darkMode = (SwitchPreferenceCompat) findPreference("dark_theme");
        Preference btnPrivacy = findPreference("prefSettingsBtnPrivacy");
        Preference btnContactEmail = findPreference("prefContactUs");

        switch_darkMode.setOnPreferenceClickListener(preference -> {
            listener.changeTheme(switch_darkMode.isChecked());
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
        void changeTheme(boolean isDarkMode);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof setOnChangeThemeListener)
            listener = (setOnChangeThemeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}