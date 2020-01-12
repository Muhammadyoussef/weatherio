package me.muhammadyoussef.weatherio.utils;

import android.content.Context;

import javax.inject.Inject;

import androidx.annotation.StringRes;
import me.muhammadyoussef.weatherio.di.qualifier.ForApplication;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;

@ApplicationScope
public class ResourcesUtil {

    private final Context context;

    @Inject
    public ResourcesUtil(@ForApplication Context context) {
        this.context = context;
    }

    public String getString(@StringRes int resourceId) {
        return context.getString(resourceId);
    }
}
