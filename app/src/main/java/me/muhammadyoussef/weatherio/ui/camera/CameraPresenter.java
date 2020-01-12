package me.muhammadyoussef.weatherio.ui.camera;

import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.utils.DiskUtils;
import me.muhammadyoussef.weatherio.utils.PermissionUtil;

@FragmentScope
public class CameraPresenter implements CameraContract.Presenter {

    private final PermissionUtil permissionUtil;
    private final CameraContract.View view;
    private final DiskUtils diskUtils;
    private Uri imageUri;

    @Inject
    CameraPresenter(PermissionUtil permissionUtil, CameraContract.View view, DiskUtils diskUtils) {
        this.permissionUtil = permissionUtil;
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCameraClicked() {
        if (permissionUtil.hasLocationPermission()) {
            if (permissionUtil.isGPSEnabled()) {
                File destination = diskUtils.newImageFile(diskUtils.getAttachmentsDirectory());
                imageUri = Uri.fromFile(destination);
                view.snapPhoto(imageUri);
            } else {
                view.showGPSWarning();
            }
        } else {
            permissionUtil.requestLocationPermission();
        }
    }

    @Override
    public void onPhotoSnapped() {
        if (imageUri != null) {
            view.navigateToAnnotationScreen(imageUri);
        }
    }
}
