package me.muhammadyoussef.weatherio.ui.host;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.WeatherioApp;
import me.muhammadyoussef.weatherio.di.ComponentProvider;
import me.muhammadyoussef.weatherio.di.activity.ActivityComponent;
import me.muhammadyoussef.weatherio.di.activity.ActivityModule;
import me.muhammadyoussef.weatherio.di.application.AppComponent;
import me.muhammadyoussef.weatherio.ui.camera.CameraFragment;

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
    public void setMainContent() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CameraFragment.newInstance())
                .commit();
    }
}
