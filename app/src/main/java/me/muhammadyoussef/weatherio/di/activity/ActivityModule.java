package me.muhammadyoussef.weatherio.di.activity;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.muhammadyoussef.weatherio.di.qualifier.ForActivity;
import me.muhammadyoussef.weatherio.di.scope.ActivityScope;

/**
 * This class is responsible for providing the requested objects to {@link ActivityScope} annotated classes
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    Activity provideActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }
}