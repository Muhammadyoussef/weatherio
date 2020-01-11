package me.muhammadyoussef.weatherio.di.fragment;

import dagger.Subcomponent;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.ui.camera.CameraFragment;
import me.muhammadyoussef.weatherio.ui.history.HistoryFragment;

/**
 * This interface is used by dagger to generate the code that defines the connection between the provider of objects
 * (i.e. {@link FragmentModule}), and the object which expresses a dependency.
 */

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(CameraFragment cameraFragment);

    void inject(HistoryFragment historyFragment);
}
