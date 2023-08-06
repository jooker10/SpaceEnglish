package anouar.oulhaj.p001;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsMAnuelFragment extends Fragment {

        private onSettingsListener listener_settings;
        private static final String ARG_DARKMODE = "dark mode";
        private boolean isDarkMode;
        private SwitchCompat switch_theme;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            isDarkMode = bundle.getBoolean(ARG_DARKMODE);
        }
    }

    public static SettingsMAnuelFragment newInstance(boolean isDarkMode){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_DARKMODE,isDarkMode);
        SettingsMAnuelFragment fragment = new SettingsMAnuelFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch_theme = view.findViewById(R.id.settingsSwitchTheme);
        switch_theme.setChecked(isDarkMode);
        Toast.makeText(getActivity(), "" +isDarkMode, Toast.LENGTH_SHORT).show();
        switch_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.isDarkMode = switch_theme.isChecked();
                 listener_settings.onDarkSettingsReturn(Utils.isDarkMode);
            }
        });
    }


    //--------------Listener---------------------
    public interface onSettingsListener{
        void onDarkSettingsReturn(boolean isDarkMode);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onSettingsListener){
            listener_settings = (onSettingsListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener_settings = null;
    }
}