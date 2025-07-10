package edu.SpaceLearning.SpaceEnglish.UtilsClasses

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import edu.SpaceLearning.SpaceEnglish.R

/**
 * CustomToast class for displaying custom Toast messages.
 */
object CustomToast {
    /**
     * Show a custom Toast message with the provided text.
     *
     * @param context The context from which the Toast is shown.
     * @param message The message text to be displayed in the Toast.
     */
    @JvmStatic
    fun showToast(context: Context?, message: String?) {
        // Create a LayoutInflater to inflate your custom layout
        val inflater = LayoutInflater.from(context)
        val customToastView = inflater.inflate(R.layout.custom_toast, null)

        // Customize the Toast view
        val toastMessage = customToastView.findViewById<TextView>(R.id.toast_message)
        toastMessage.text = message

        // Create and show the Toast
        val customToast = Toast(context)
        customToast.duration = Toast.LENGTH_SHORT
        customToast.setGravity(Gravity.BOTTOM, 0, 200) // Set the position of the Toast
        customToast.view = customToastView // Set the custom view to the Toast
        customToast.show() // Show the Toast
    }
}
