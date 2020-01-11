package me.muhammadyoussef.weatherio.ui.host;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.di.scope.ActivityScope;

@ActivityScope
public class HostPresenter implements HostContract.Presenter {

    private final HostContract.View view;

    @Inject
    public HostPresenter(HostContract.View view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.setupClickListeners();
        view.displayCameraScreen();
    }

    @Override
    public void onCameraClicked() {
        view.displayCameraScreen();
    }

    @Override
    public void onHistoryClicked() {
        view.displayHistoryScreen();
    }
}
