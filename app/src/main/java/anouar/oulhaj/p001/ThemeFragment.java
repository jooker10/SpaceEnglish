package anouar.oulhaj.p001;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class ThemeFragment extends Fragment {

    private SwitchCompat switchMode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchMode = view.findViewById(R.id.settingsSwitchTheme);
        switchMode.setChecked(Utils.isDarkThemeTest);
        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Utils.isDarkThemeTest = isChecked;

            if (isChecked) {
                // Enable Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Enable Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }


        });

        return view;
    }
}
