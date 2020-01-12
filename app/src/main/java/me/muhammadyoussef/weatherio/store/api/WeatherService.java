package me.muhammadyoussef.weatherio.store.api;

import io.reactivex.Single;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather?appid=84cf90dc9151a1f95fe43ece75760e75&units=metric")
    Single<WeatherApiResponse> fetchWeatherData(@Query("lat") double latitude, @Query("lon") double longitude);
}
