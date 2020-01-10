package me.muhammadyoussef.weatherio.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.muhammadyoussef.weatherio.di.qualifier.ForApplication;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;

/**
 * This class is responsible for providing the requested objects to {@link ApplicationScope} annotated classes
 */

@Module(includes = {SchedulersModule.class})
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @ApplicationScope
    @Provides
    Application providesApplication() {
        return application;
    }

    @ApplicationScope
    @Provides
    @ForApplication
    Context providesApplicationContext() {
        return application;
    }
}
