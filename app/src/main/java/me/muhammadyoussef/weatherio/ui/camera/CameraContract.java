package me.muhammadyoussef.weatherio.ui.camera;

import android.net.Uri;

import androidx.annotation.NonNull;

public interface CameraContract {

    interface View {

        void snapPhoto(Uri destination);

        void navigateToAnnotationScreen(@NonNull Uri photoUri);

        void showGPSWarning();
    }

    interface Presenter {

        void onCameraClicked();

        void onPhotoSnapped();
    }
}
