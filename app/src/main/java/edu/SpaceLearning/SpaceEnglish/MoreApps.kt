package edu.SpaceLearning.SpaceEnglish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment for displaying more apps and linking to their respective URLs.
 */
public class MoreApps extends Fragment {

    public MoreApps() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.more_apps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find buttons in the layout
        Button btnApp2 = view.findViewById(R.id.btnMoreAppsTheSecond);
        Button btnApp3 = view.findViewById(R.id.btnMoreAppsTheThird);

        // Set click listeners for each button
        btnApp2.setOnClickListener(v -> toOurApp(getString(R.string.app1_url)));
        btnApp3.setOnClickListener(v -> toOurApp(getString(R.string.app2_url)));
    }

    /**
     * Opens the specified app URL using an Intent.
     *
     * @param urlApp The URL of the app to open.
     */
    private void toOurApp(String urlApp){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp));
        startActivity(intent);
    }

}
