package me.muhammadyoussef.weatherio.di.activity;

import dagger.Subcomponent;
import me.muhammadyoussef.weatherio.di.fragment.FragmentComponent;
import me.muhammadyoussef.weatherio.di.fragment.FragmentModule;
import me.muhammadyoussef.weatherio.di.scope.ActivityScope;
import me.muhammadyoussef.weatherio.ui.annotation.AnnotationActivity;
import me.muhammadyoussef.weatherio.ui.host.HostActivity;

/**
 * This interface is used by dagger to generate the code that defines the connection between the provider of objects
 * (i.e. {@link ActivityModule}), and the object which expresses a dependency.
 */

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    FragmentComponent plus(FragmentModule fragmentModule);

    void inject(HostActivity hostActivity);

    void inject(AnnotationActivity annotationActivity);
}