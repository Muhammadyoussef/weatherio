package me.muhammadyoussef.weatherio.ui.annotation;

import android.graphics.Bitmap;
import android.util.Pair;

import com.google.android.gms.location.LocationRequest;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.patloew.rxlocation.RxLocation;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import me.muhammadyoussef.weatherio.di.scope.ActivityScope;
import me.muhammadyoussef.weatherio.schedulers.ThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.qualifires.IOThread;
import me.muhammadyoussef.weatherio.store.OpenWeatherStore;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherConditionItem;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherItemType;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherMapper;
import me.muhammadyoussef.weatherio.utils.DiskUtils;
import me.muhammadyoussef.weatherio.utils.PermissionUtil;
import timber.log.Timber;

@ActivityScope
public class AnnotationPresenter implements AnnotationContract.Presenter, WeatherDataOwner {

    private final BehaviorRelay<List<WeatherConditionItem>> weatherRelay;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposables;
    private final PermissionUtil permissionUtil;
    private final OpenWeatherStore weatherStore;
    private final AnnotationContract.View view;
    private final WeatherMapper weatherMapper;
    private final RxLocation rxLocation;
    private final DiskUtils diskUtils;
    private AnnotationActivityArgs activityArgs;

    @Inject
    AnnotationPresenter(@IOThread ThreadSchedulers threadSchedulers,
                        PermissionUtil permissionUtil, AnnotationContract.View view,
                        OpenWeatherStore weatherStore,
                        WeatherMapper weatherMapper,
                        RxLocation rxLocation,
                        DiskUtils diskUtils) {
        this.permissionUtil = permissionUtil;
        this.weatherRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.disposables = new CompositeDisposable();
        this.threadSchedulers = threadSchedulers;
        this.weatherMapper = weatherMapper;
        this.weatherStore = weatherStore;
        this.rxLocation = rxLocation;
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCreate(AnnotationActivityArgs activityArgs) {
        this.activityArgs = activityArgs;
        view.setupViews();
        view.loadPhoto(activityArgs.getPhotoUri());
        fetchChecked();
    }

    @Override
    public void onSaveClicked(Bitmap captureBitmap) {
        disposables.add(diskUtils.save(captureBitmap, getPhotoFile())
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(view::navigateToDetails, Timber::e));
    }

    @Override
    public void onReloadClicked() {
        fetchChecked();
    }

    @Override
    public int getItemCount() {
        return weatherRelay.getValue().size();
    }

    @Override
    public WeatherConditionItem getItem(int position) {
        return weatherRelay.getValue().get(position);
    }

    @Override
    public void onItemClicked(WeatherConditionItem item, boolean activated) {
        if (activated) {
            showWeatherItem(item);
        } else {
            hideWeatherItem(item);
        }
    }

    private void fetchChecked() {
        if (permissionUtil.hasLocationPermission()) {
            if (permissionUtil.isGPSEnabled()) {
                fetch();
            } else {
                view.showGPSWarning();
                view.showNetworkFailureHint();
            }
        } else {
            permissionUtil.requestLocationPermission();
            view.showNetworkFailureHint();
        }
    }

    private void fetch() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1);
        disposables.add(rxLocation.location().updates(locationRequest)
                .map(location -> new Pair<>(location.getLatitude(), location.getLongitude()))
                .flatMapSingle(latLongPair -> weatherStore.fetchWeatherData(latLongPair.first, latLongPair.second))
                .map(weatherMapper::toViewModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.showLoadingHint())
                .subscribe(weatherConditionItems -> {
                    weatherRelay.accept(weatherConditionItems);
                    view.notifyDataSetChanged();
                    view.showEditingHint();
                }, throwable -> {
                    view.showNetworkFailureHint();
                    Timber.e(throwable);
                }));
    }

    private void showWeatherItem(WeatherConditionItem item) {
        switch (item.getType()) {
            case WeatherItemType.CONDITION:
                view.showCondition(item.getValue());
                break;
            case WeatherItemType.HUMIDITY:
                view.showHumidity(item.getValue());
                break;
            case WeatherItemType.LOCATION:
                view.showLocation(item.getValue());
                break;
            case WeatherItemType.TEMPERATURE:
                view.showTemperature(item.getValue());
                break;
            default:
                //do nothing
        }
    }

    private void hideWeatherItem(WeatherConditionItem item) {
        switch (item.getType()) {
            case WeatherItemType.CONDITION:
                view.hideCondition();
                break;
            case WeatherItemType.HUMIDITY:
                view.hideHumidity();
                break;
            case WeatherItemType.LOCATION:
                view.hideLocation();
                break;
            case WeatherItemType.TEMPERATURE:
                view.hideTemperature();
                break;
            default:
                // do nothing
        }
    }

    private File getPhotoFile() {
        if (activityArgs == null || activityArgs.getPhotoUri() == null || activityArgs.getPhotoUri().getPath() == null) {
            return diskUtils.newImageFile(diskUtils.getAttachmentsDirectory());
        } else {
            return new File(activityArgs.getPhotoUri().getPath());
        }
    }
}
