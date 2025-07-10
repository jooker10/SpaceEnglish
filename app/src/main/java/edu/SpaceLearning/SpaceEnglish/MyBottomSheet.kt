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

/**
 * BottomSheetDialogFragment to display a bottom sheet with options to switch between fragments.
 */
public class MyBottomSheet extends BottomSheetDialogFragment {

    public static final String SHEET_TAG = "sheet";

    // Static factory method to create a new instance of MyBottomSheet
    public static MyBottomSheet newInstance() {
        return new MyBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_adding_elements, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create a BottomSheetDialog and customize its behavior
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                // Set the peek height and initial state of the bottom sheet
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

        // Initialize views and set initial fragment
        RadioButton radioVideo = view_sheet.findViewById(R.id.radioVideo);
        RadioButton radioMoreApps = view_sheet.findViewById(R.id.radioMoreApps);
        setVideoBuyFragment(new SheetVideoFragment()); // Set initial fragment

        // Handle radio button clicks to switch fragments
        radioVideo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                setVideoBuyFragment(new SheetVideoFragment()); // Switch to SheetVideoFragment
                radioVideo.setTextColor(Color.WHITE); // Change text color when selected
            } else {
                radioVideo.setTextColor(getResources().getColor(R.color.gray_600)); // Reset text color
            }
        });

        radioMoreApps.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                setVideoBuyFragment(new MoreApps()); // Switch to MoreApps fragment
                radioMoreApps.setTextColor(Color.WHITE); // Change text color when selected
            } else {
                radioMoreApps.setTextColor(getResources().getColor(R.color.gray_600)); // Reset text color
            }
        });
    }

    // Method to replace the fragment in the container with animation
    private void setVideoBuyFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.fragment_enter_to_right,
                R.anim.fragment_exit_to_right,
                R.anim.fragment_enter_to_right,
                R.anim.fragment_exit_to_right
        );
        ft.replace(R.id.fragContainerVideoBuy, fragment);
        ft.commit();
    }
}
