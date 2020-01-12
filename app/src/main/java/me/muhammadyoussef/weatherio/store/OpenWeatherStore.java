package me.muhammadyoussef.weatherio.store;

import javax.inject.Inject;

import io.reactivex.Single;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;
import me.muhammadyoussef.weatherio.store.api.WeatherAPIsUtil;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherApiResponse;

@ApplicationScope
public class OpenWeatherStore {

    private final WeatherAPIsUtil apIsUtil;

    @Inject
    OpenWeatherStore(WeatherAPIsUtil apIsUtil) {
        this.apIsUtil = apIsUtil;
    }

    public Single<WeatherApiResponse> fetchWeatherData(String latitude, String longitude) {
        return apIsUtil.getAPIService()
                .fetchWeatherData(latitude, longitude);
    }
}
