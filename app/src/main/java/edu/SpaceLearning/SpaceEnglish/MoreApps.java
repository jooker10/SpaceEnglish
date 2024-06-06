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

        Button btnApp2 = view.findViewById(R.id.btnMoreAppsTheSecond);
        Button btnApp3 = view.findViewById(R.id.btnMoreAppsTheThird);

        btnApp2.setOnClickListener(v -> toOurApp(getString(R.string.url_app2)));
        btnApp3.setOnClickListener(v -> toOurApp(getString(R.string.url_app3)));
    }

    private void toOurApp(String urlApp){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp));
        startActivity(intent);
    }

}