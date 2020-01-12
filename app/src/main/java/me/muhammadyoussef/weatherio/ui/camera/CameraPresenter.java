package me.muhammadyoussef.weatherio.ui.camera;

import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.utils.DiskUtils;

@FragmentScope
public class CameraPresenter implements CameraContract.Presenter {

    private final CameraContract.View view;
    private final DiskUtils diskUtils;
    private Uri imageUri;

    @Inject
    CameraPresenter(CameraContract.View view, DiskUtils diskUtils) {
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCameraClicked() {
        File destination = diskUtils.newImageFile(diskUtils.getAttachmentsDirectory());
        imageUri = Uri.fromFile(destination);
        view.snapPhoto(imageUri);
    }

    @Override
    public void onPhotoSnapped() {
        if (imageUri != null) {
            view.navigateToAnnotationScreen(imageUri);
        }
    }
}
