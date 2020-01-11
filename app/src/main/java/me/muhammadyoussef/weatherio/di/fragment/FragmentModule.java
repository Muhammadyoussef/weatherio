package me.muhammadyoussef.weatherio.di.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import me.muhammadyoussef.weatherio.di.qualifier.ForFragment;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.ui.camera.CameraContract;
import me.muhammadyoussef.weatherio.ui.history.HistoryContract;
import me.muhammadyoussef.weatherio.ui.history.HistoryDataOwner;

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

    @FragmentScope
    @Provides
    CameraContract.View provideCameraView() {
        return (CameraContract.View) fragment;
    }

    @FragmentScope
    @Provides
    HistoryContract.View provideHistoryView() {
        return (HistoryContract.View) fragment;
    }

    @FragmentScope
    @Provides
    HistoryDataOwner provideHistoryDataProvider() {
        return (HistoryDataOwner) fragment;
    }
}
