package me.muhammadyoussef.weatherio.di.application;

import dagger.Module;
import dagger.Provides;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;
import me.muhammadyoussef.weatherio.schedulers.ComputationalThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.IOThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.MainThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.NetworkThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.ThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.qualifires.ComputationalThread;
import me.muhammadyoussef.weatherio.schedulers.qualifires.IOThread;
import me.muhammadyoussef.weatherio.schedulers.qualifires.MainThread;
import me.muhammadyoussef.weatherio.schedulers.qualifires.NetworkThread;

/**
 * This class is responsible for providing the requested objects for {@link ThreadSchedulers} objects
 */

@Module
public class SchedulersModule {

    @ApplicationScope
    @Provides
    @MainThread
    ThreadSchedulers providesMainThreadSchedulers() {
        return new MainThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @IOThread
    ThreadSchedulers providesIOThreadSchedulers() {
        return new IOThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @ComputationalThread
    ThreadSchedulers providesComputationalThreadSchedulers() {
        return new ComputationalThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @NetworkThread
    ThreadSchedulers providesUnitTestingThreadSchedulers() {
        return new NetworkThreadSchedulers();
    }
}
