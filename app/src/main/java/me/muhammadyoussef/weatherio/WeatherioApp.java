package me.muhammadyoussef.weatherio;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

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
        setStrictModeEnabledForDebug();
        setupTimberTree();
        installLeakCanary();
    }

    @Override
    public AppComponent getComponent() {
        return appComponent;
    }

    /*
     * When enabled it detects things you might be doing by accident and brings them to your attention so you can fix them.
     * Thread policy is used to catch accidental disk or network operations on the application's MAIN thread.
     * VmPolicy is used to detect when a closeable or other object with an explicit termination method is finalized without having been closed.
     */
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

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
