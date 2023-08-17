package anouar.oulhaj.p001.QuizFrags;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import anouar.oulhaj.p001.R;
import anouar.oulhaj.p001.onVideoBuyClickListener;


public class SheetBuyingFragment extends Fragment {

    private onVideoBuyClickListener videoBuyClickListener;
    public SheetBuyingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onVideoBuyClickListener)
            videoBuyClickListener = (onVideoBuyClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(videoBuyClickListener != null)
            videoBuyClickListener = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sheet_buying, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBuyAll = view.findViewById(R.id.btnBuyAll);
        btnBuyAll.setOnClickListener(view1 -> {
            videoBuyClickListener.onBuyWithPaypal();
        });
    }
}