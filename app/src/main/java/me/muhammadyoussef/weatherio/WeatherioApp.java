package me.muhammadyoussef.weatherio;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import me.muhammadyoussef.weatherio.di.ComponentProvider;
import me.muhammadyoussef.weatherio.di.application.AppComponent;
import me.muhammadyoussef.weatherio.di.application.AppModule;
import me.muhammadyoussef.weatherio.di.application.DaggerAppComponent;
import timber.log.Timber;

public class WeatherioApp extends Application implements ComponentProvider<AppComponent> {

    private final AppComponent appComponent = createAppComponent();

    public static AppComponent getComponent(Context context) {
        return getApp(context).getComponent();
    }

    private static WeatherioApp getApp(Context context) {
        return (WeatherioApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent.inject(this);
        setStrictModeEnabledForDebug();
        setupTimberTree();
    }

    @Override
    public AppComponent getComponent() {
        return appComponent;
    }

    private void setStrictModeEnabledForDebug() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void setupTimberTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        //TODO setup different tree for release (if needed)
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
