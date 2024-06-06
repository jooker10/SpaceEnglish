package edu.SpaceLearning.SpaceEnglish;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.SpaceLearning.SpaceEnglish.QuizFrags.SheetVideoFragment;

public class MyBottomSheet extends BottomSheetDialogFragment {

    public static final String SHEET_TAG = "sheet";
    //----------- Instance-------------
    public static MyBottomSheet newInstance() {
        return new MyBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sheet_adding_elements,container,false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if(bottomSheet != null) {
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setPeekHeight(getResources().getDisplayMetrics().heightPixels);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        return dialog;

    }

    @Override
    public void onViewCreated(@NonNull View view_sheet, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view_sheet, savedInstanceState);

        //----------setFragmentsToContainer------------------------
        RadioButton radioVideo = view_sheet.findViewById(R.id.radioVideo);
        RadioButton radioMoreApps= view_sheet.findViewById(R.id.radioMoreApps);

        setVideoBuyFragment(new SheetVideoFragment());

        radioVideo.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) {
                setVideoBuyFragment(new SheetVideoFragment());
               radioVideo.setTextColor(Color.WHITE);
            }
            else { radioVideo.setTextColor(getResources().getColor(R.color.gray_600));}
        });
        radioMoreApps.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) {
                setVideoBuyFragment(new MoreApps());
                radioMoreApps.setTextColor(Color.WHITE);
            }
            else {radioMoreApps.setTextColor(getResources().getColor(R.color.gray_600));}
        });

    }


    private void setVideoBuyFragment(Fragment fragment){

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter_to_right,R.anim.fragment_exit_to_right,R.anim.fragment_enter_to_right,R.anim.fragment_exit_to_right);
        ft.replace(R.id.fragContainerVideoBuy,fragment);
        ft.commit();
    }
}
