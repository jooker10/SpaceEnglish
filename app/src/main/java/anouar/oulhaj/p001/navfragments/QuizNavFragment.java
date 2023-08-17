package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001._Main.Utils;


public class QuizNavFragment extends Fragment {

   private OnFragmentNavigationListener navigationListener;
    public QuizNavFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentNavigationListener) {
            navigationListener = (OnFragmentNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(navigationListener != null)
            navigationListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_nav_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.nameOfFragmentSearchView = "Quiz";

        // Notify the MainActivity that this fragment is selected
        if (navigationListener != null) {
            navigationListener.onFragmentSelected(this);
        }

    }




}