package anouar.oulhaj.p001.navfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import anouar.oulhaj.p001.InfoQuizFragment;
import anouar.oulhaj.p001.OnFragmentNavigationListener;
import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.Utils;


public class QuizNavFragmentReplace extends Fragment {

   private OnFragmentNavigationListener navigationListener;
    public QuizNavFragmentReplace() {
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
        return inflater.inflate(R.layout.quiz_nav_fragment_replace, container, false);
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