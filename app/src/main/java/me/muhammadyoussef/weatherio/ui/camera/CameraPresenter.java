package me.muhammadyoussef.weatherio.ui.camera;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
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
        disposables.add(Single.fromCallable(diskUtils::getAttachmentsDirectory)
                .flatMap((Function<File, SingleSource<Uri>>) file -> diskUtils.save(photo, file))
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(view::navigateToAnnotationScreen, Timber::e));
    }
}
