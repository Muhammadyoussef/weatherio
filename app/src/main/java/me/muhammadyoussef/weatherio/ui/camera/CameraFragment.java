package me.muhammadyoussef.weatherio.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.ComponentProvider;
import me.muhammadyoussef.weatherio.di.activity.ActivityComponent;
import me.muhammadyoussef.weatherio.di.fragment.FragmentModule;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment implements CameraContract.View {

    private static final int SNAP_PHOTO_REQUEST_CODE = 1001;

    @Inject
    CameraPresenter presenter;

    public static Fragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComponentProvider) {
            ComponentProvider provider = ((ComponentProvider) context);
            if (provider.getComponent() instanceof ActivityComponent) {
                ((ActivityComponent) provider.getComponent())
                        .plus(new FragmentModule(this))
                        .inject(this);
            } else {
                throw new IllegalStateException("Component must be " + ActivityComponent.class.getName());
            }
        } else {
            throw new IllegalStateException("host context must implement " + ComponentProvider.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SNAP_PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Object result = data.getExtras().get(MediaStore.EXTRA_OUTPUT);
            if (result instanceof Uri) {
                presenter.onPhotoSnapped((Uri) result);
            }
        }
    }

    @Override
    public void snapPhoto(Uri destination) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destination);
        startActivityForResult(intent, SNAP_PHOTO_REQUEST_CODE);
    }

    @Override
    public void navigateToAnnotationScreen(@NonNull Uri photoUri) {
        //TODO navigate to annotation screen
    }

    @OnClick(R.id.btn_camera)
    void onCameraClicked() {
        presenter.onCameraClicked();
    }
}
