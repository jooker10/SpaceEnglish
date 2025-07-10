package edu.SpaceLearning.SpaceEnglish

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.FrameLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BottomSheetDialogFragment to display a bottom sheet with options to switch between fragments.
 */
class MyBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sheet_adding_elements, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a BottomSheetDialog and customize its behavior
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                // Set the peek height and initial state of the bottom sheet
                val behavior: BottomSheetBehavior<*> =
                    BottomSheetBehavior.from(bottomSheet)
                behavior.peekHeight = resources.displayMetrics.heightPixels
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        return dialog
    }

    override fun onViewCreated(view_sheet: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view_sheet, savedInstanceState)

        // Initialize views and set initial fragment
        val radioVideo = view_sheet.findViewById<RadioButton>(R.id.radioVideo)
        val radioMoreApps = view_sheet.findViewById<RadioButton>(R.id.radioMoreApps)
        setVideoBuyFragment(SheetVideoFragment()) // Set initial fragment

        // Handle radio button clicks to switch fragments
        radioVideo.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
            if (b) {
                setVideoBuyFragment(SheetVideoFragment()) // Switch to SheetVideoFragment
                radioVideo.setTextColor(Color.WHITE) // Change text color when selected
            } else {
                radioVideo.setTextColor(resources.getColor(R.color.gray_600)) // Reset text color
            }
        }

        radioMoreApps.setOnCheckedChangeListener { compoundButton: CompoundButton?, b: Boolean ->
            if (b) {
                setVideoBuyFragment(MoreApps()) // Switch to MoreApps fragment
                radioMoreApps.setTextColor(Color.WHITE) // Change text color when selected
            } else {
                radioMoreApps.setTextColor(resources.getColor(R.color.gray_600)) // Reset text color
            }
        }
    }

    // Method to replace the fragment in the container with animation
    private fun setVideoBuyFragment(fragment: Fragment) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.fragment_enter_to_right,
            R.anim.fragment_exit_to_right,
            R.anim.fragment_enter_to_right,
            R.anim.fragment_exit_to_right
        )
        ft.replace(R.id.fragContainerVideoBuy, fragment)
        ft.commit()
    }

    companion object {
        const val SHEET_TAG: String = "sheet"

        // Static factory method to create a new instance of MyBottomSheet
        fun newInstance(): MyBottomSheet {
            return MyBottomSheet()
        }
    }
}
