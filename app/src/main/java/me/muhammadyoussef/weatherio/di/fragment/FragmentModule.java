package me.muhammadyoussef.weatherio.di.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import me.muhammadyoussef.weatherio.di.qualifier.ForFragment;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;

/**
 * This class is responsible for providing the requested objects to {@link FragmentScope} annotated classes
 */

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    Fragment provideFragment() {
        return fragment;
    }

    @FragmentScope
    @ForFragment
    @Provides
    Context provideFragmentContext() {
        return fragment.getContext();
    }

    @FragmentScope
    @Provides
    FragmentManager provideFragmentManager() {
        return fragment.getChildFragmentManager();
    }

    @FragmentScope
    @ForFragment
    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }
}
