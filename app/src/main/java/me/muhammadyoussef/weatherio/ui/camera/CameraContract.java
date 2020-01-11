package me.muhammadyoussef.weatherio.ui.camera;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

public interface CameraContract {

    interface View {

        void snapPhoto();

        void navigateToAnnotationScreen(@NonNull Uri photoUri);
    }

    interface Presenter {

        void onCameraClicked();

        void onPhotoSnapped(@NonNull Bitmap photo);
    }
}
