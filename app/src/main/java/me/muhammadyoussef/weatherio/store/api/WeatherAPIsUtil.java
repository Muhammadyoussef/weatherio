package me.muhammadyoussef.weatherio.store.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScope
public class WeatherAPIsUtil {

    private static final String BASE_URL = "https://api.openweathermap.org";
    private static Retrofit retrofit;

    @Inject
    WeatherAPIsUtil() {
    }

    public WeatherService getAPIService() {
        return getClient().create(WeatherService.class);
    }

    private Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
