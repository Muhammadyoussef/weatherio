package me.muhammadyoussef.weatherio.di.application;

import dagger.Component;
import me.muhammadyoussef.weatherio.di.activity.ActivityComponent;
import me.muhammadyoussef.weatherio.di.activity.ActivityModule;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;

/**
 * This interface is used by dagger to generate the code that defines the connection between the provider of objects
 * (i.e. {@link AppModule}), and the object which expresses a dependency.
 */

@ApplicationScope
@Component(modules = {AppModule.class, SchedulersModule.class})
public interface AppComponent {

    ActivityComponent plus(ActivityModule activityModule);
}
