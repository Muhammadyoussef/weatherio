package me.muhammadyoussef.weatherio.ui.annotation;

import me.muhammadyoussef.weatherio.store.model.weather.WeatherConditionItem;

public interface WeatherDataOwner {

    int getItemCount();

    WeatherConditionItem getItem(int position);

    void onItemClicked(WeatherConditionItem item, boolean activated);
}
