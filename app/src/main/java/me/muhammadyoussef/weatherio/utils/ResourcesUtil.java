package me.muhammadyoussef.weatherio.utils;

import android.content.Context;
import android.view.LayoutInflater;

import javax.inject.Inject;

import androidx.annotation.StringRes;
import me.muhammadyoussef.weatherio.di.qualifier.ForActivity;
import me.muhammadyoussef.weatherio.di.scope.ActivityScope;

@ActivityScope
public class ResourcesUtil {

    private final Context context;

    @Inject
    public ResourcesUtil(@ForActivity Context context) {
        this.context = context;
    }

    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    public String getString(@StringRes int resourceId) {
        return context.getString(resourceId);
    }
}
