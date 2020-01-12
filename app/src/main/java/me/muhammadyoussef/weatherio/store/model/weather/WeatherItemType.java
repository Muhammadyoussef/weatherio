package me.muhammadyoussef.weatherio.store.model.weather;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static me.muhammadyoussef.weatherio.store.model.weather.WeatherItemType.CONDITION;
import static me.muhammadyoussef.weatherio.store.model.weather.WeatherItemType.HUMIDITY;
import static me.muhammadyoussef.weatherio.store.model.weather.WeatherItemType.LOCATION;
import static me.muhammadyoussef.weatherio.store.model.weather.WeatherItemType.TEMPERATURE;

@Retention(RetentionPolicy.RUNTIME)
@IntDef({CONDITION, TEMPERATURE, HUMIDITY, LOCATION})
public @interface WeatherItemType {
    int CONDITION = 0;
    int TEMPERATURE = 1;
    int HUMIDITY = 2;
    int LOCATION = 3;
}