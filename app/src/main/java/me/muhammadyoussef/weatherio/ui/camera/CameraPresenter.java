package me.muhammadyoussef.weatherio.ui.camera;

import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.utils.DiskUtils;

@FragmentScope
public class CameraPresenter implements CameraContract.Presenter {

    private final CameraContract.View view;
    private final DiskUtils diskUtils;

    @Inject
    CameraPresenter(CameraContract.View view, DiskUtils diskUtils) {
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCameraClicked() {
        File destination = diskUtils.newImageFile(diskUtils.getAttachmentsDirectory());
        view.snapPhoto(Uri.fromFile(destination));
    }

    @Override
    public void onPhotoSnapped(@NonNull Uri photo) {
        view.navigateToAnnotationScreen(photo);
    }
}
