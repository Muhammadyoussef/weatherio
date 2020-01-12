package me.muhammadyoussef.weatherio.ui.annotation;

import android.graphics.Bitmap;

import com.jakewharton.rxrelay2.BehaviorRelay;

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
import timber.log.Timber;

@ActivityScope
public class AnnotationPresenter implements AnnotationContract.Presenter, WeatherDataOwner {

    private final BehaviorRelay<List<WeatherConditionItem>> weatherRelay;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposables;
    private final OpenWeatherStore weatherStore;
    private final AnnotationContract.View view;
    private final WeatherMapper weatherMapper;
    private final DiskUtils diskUtils;
    private AnnotationActivityArgs activityArgs;

    @Inject
    AnnotationPresenter(@IOThread ThreadSchedulers threadSchedulers,
                        AnnotationContract.View view,
                        OpenWeatherStore weatherStore,
                        WeatherMapper weatherMapper,
                        DiskUtils diskUtils) {
        this.weatherRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.disposables = new CompositeDisposable();
        this.threadSchedulers = threadSchedulers;
        this.weatherMapper = weatherMapper;
        this.weatherStore = weatherStore;
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onCreate(AnnotationActivityArgs activityArgs) {
        this.activityArgs = activityArgs;
        view.setupViews();
        view.loadPhoto(activityArgs.getPhotoUri());
        fetch();
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
        fetch();
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

    private void fetch() {
        // TODO get current user's location
        disposables.add(weatherStore.fetchWeatherData("29.847720", "31.337962")
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
