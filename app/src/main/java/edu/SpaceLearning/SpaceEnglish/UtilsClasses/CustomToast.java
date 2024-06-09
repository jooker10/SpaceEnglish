package edu.SpaceLearning.SpaceEnglish.UtilsClasses;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.SpaceLearning.SpaceEnglish.R;

public class CustomToast {

    public static void showToast(Context context, String message) {
        // Create a LayoutInflater to inflate your custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View customToastView = inflater.inflate(R.layout.custom_toast, null);

        // Customize the Toast view
        TextView toastMessage = customToastView.findViewById(R.id.toast_message);
        toastMessage.setText(message);

        // Create and show the Toast
        Toast customToast = new Toast(context);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setGravity(Gravity.BOTTOM, 0, 200);
        customToast.setView(customToastView);
        customToast.show();
    }
}
