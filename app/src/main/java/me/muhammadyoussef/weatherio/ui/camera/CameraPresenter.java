package me.muhammadyoussef.weatherio.ui.camera;

import android.graphics.Bitmap;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.schedulers.ThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.qualifires.IOThread;
import me.muhammadyoussef.weatherio.utils.DiskUtils;
import timber.log.Timber;

@FragmentScope
public class CameraPresenter implements CameraContract.Presenter {

    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposables;
    private final CameraContract.View view;
    private final DiskUtils diskUtils;

    @Inject
    CameraPresenter(@IOThread ThreadSchedulers threadSchedulers,
                    CameraContract.View view,
                    DiskUtils diskUtils) {
        this.disposables = new CompositeDisposable();
        this.threadSchedulers = threadSchedulers;
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCameraClicked() {
        view.snapPhoto();
    }

    @Override
    public void onPhotoSnapped(@NonNull Bitmap photo) {
        disposables.add(diskUtils.save(photo, diskUtils.getAttachmentsDirectory())
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(view::navigateToAnnotationScreen, Timber::e));
    }
}
