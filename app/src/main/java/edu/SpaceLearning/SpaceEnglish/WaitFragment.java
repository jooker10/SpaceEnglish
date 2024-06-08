package edu.SpaceLearning.SpaceEnglish;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitFragment extends Fragment {

    private static final String ARG_PERMISSION_SCORE = "permission";

    private int permissionScore;
    //private AdsManager adsManager;

    public WaitFragment() {
        // Required empty public constructor
    }


    public static WaitFragment newInstance(int permissionScore) {
        WaitFragment fragment = new WaitFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PERMISSION_SCORE, permissionScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            permissionScore = getArguments().getInt(ARG_PERMISSION_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvWaitRequiredToOpen = view.findViewById(R.id.tvScoreOpen);
        tvWaitRequiredToOpen.setText("Required "+ permissionScore + " points To Open this Table");

    }
}