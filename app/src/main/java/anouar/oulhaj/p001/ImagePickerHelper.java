package anouar.oulhaj.p001;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

public class ImagePickerHelper {

    public static final int REQUEST_IMAGE_PICKER = 101;
    private static final int REQUEST_PERMISSIONS = 102;

    private Activity activity;

    public ImagePickerHelper(Activity activity) {
        this.activity = activity;
    }

    public void pickImage() {
        if (checkPermission()) {
            openImagePicker();
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        int permissionResult = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                // Handle permission denied
            }
        }
    }

    private void openImagePicker() {
        ImagePicker.create(activity)
                .single()
                .showCamera(true)
                .start(REQUEST_IMAGE_PICKER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null) {
                String imagePath = image.getPath();
                // Do something with the imagePath, e.g., display it in an ImageView
            }
        }
    }
}
