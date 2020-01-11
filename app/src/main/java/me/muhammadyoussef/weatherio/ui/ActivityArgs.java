package me.muhammadyoussef.weatherio.ui;

import android.content.Context;
import android.content.Intent;

public interface ActivityArgs {

    Intent intent(Context activity);

    default void launch(Context activity) {
        activity.startActivity(intent(activity));
    }
}
