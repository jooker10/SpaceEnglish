package anouar.oulhaj.p001.navfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import anouar.oulhaj.p001.R;


public class HomeNavFragment extends Fragment {

    private static final String SCORE = "score";
    private int score;
    private static int hightScore;
    private TextView tv_hightScore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            score = bundle.getInt(SCORE);
        }
    }

    public static HomeNavFragment newInstance(int score){
        HomeNavFragment homeNavFragment = new HomeNavFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SCORE,score);

        homeNavFragment.setArguments(bundle);
        return homeNavFragment;

    }

    public HomeNavFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_hightScore = view.findViewById(R.id.hightScore);

        if(score>= hightScore) {hightScore=score;}
        tv_hightScore.setText("Hight Score : "+hightScore);

    }
}