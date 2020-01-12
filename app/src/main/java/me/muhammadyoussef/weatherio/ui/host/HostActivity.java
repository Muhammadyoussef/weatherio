package me.muhammadyoussef.weatherio.ui.host;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.WeatherioApp;
import me.muhammadyoussef.weatherio.di.ComponentProvider;
import me.muhammadyoussef.weatherio.di.activity.ActivityComponent;
import me.muhammadyoussef.weatherio.di.activity.ActivityModule;
import me.muhammadyoussef.weatherio.di.application.AppComponent;
import me.muhammadyoussef.weatherio.ui.camera.CameraFragment;
import me.muhammadyoussef.weatherio.ui.history.HistoryFragment;

public class HostActivity extends AppCompatActivity implements HostContract.View,
        ComponentProvider<ActivityComponent> {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView navigationView;

    @Inject
    HostPresenter presenter;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        AppComponent appComponent = WeatherioApp.getComponent(getApplicationContext());
        activityComponent = appComponent.plus(new ActivityModule(this));
        activityComponent.inject(this);
        ButterKnife.bind(this);
        presenter.onCreate();
    }

    @Override
    public ActivityComponent getComponent() {
        if (activityComponent == null) {
            AppComponent appComponent = WeatherioApp.getComponent(getApplicationContext());
            activityComponent = appComponent.plus(new ActivityModule(this));
            activityComponent.inject(this);
        }
        return activityComponent;
    }

    @Override
    public void setupClickListeners() {
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_camera:
                    presenter.onCameraClicked();
                    return true;
                case R.id.navigation_history:
                    presenter.onHistoryClicked();
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public void displayCameraScreen() {
        String tag = CameraFragment.class.getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = CameraFragment.newInstance();
        }
        replaceFragment(fragment, tag);
    }

    @Override
    public void displayHistoryScreen() {
        String tag = HistoryFragment.class.getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = HistoryFragment.newInstance();
        }
        replaceFragment(fragment, tag);
    }

    private void replaceFragment(@NonNull Fragment fragment, @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }
}
