package edu.SpaceLearning.SpaceEnglish.Broadcast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.SpaceLearning.SpaceEnglish.R;

public class NoConnectionFragment extends Fragment {



    public NoConnectionFragment() {
        // Required empty public constructor
    }

    public static NoConnectionFragment newInstance(String param1, String param2) {
        NoConnectionFragment fragment = new NoConnectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_connection, container, false);
    }
}