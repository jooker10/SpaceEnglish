package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import anouar.oulhaj.p001.R;

public class SettingsFragment extends PreferenceFragmentCompat {

  private setOnChangeThemeListener listener;
  private SwitchPreferenceCompat switch_darkMode;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        switch_darkMode = (SwitchPreferenceCompat) findPreference("dark_theme");

        switch_darkMode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
               listener.changeTheme(switch_darkMode.isChecked());
                return true;
            }
        });

    }


    public interface setOnChangeThemeListener{
        void changeTheme(boolean isDarkMode);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof setOnChangeThemeListener) listener = (setOnChangeThemeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}