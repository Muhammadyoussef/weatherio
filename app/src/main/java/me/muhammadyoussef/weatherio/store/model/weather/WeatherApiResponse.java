package me.muhammadyoussef.weatherio.store.model.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherApiResponse {

    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("main")
    private Main main;
    @SerializedName("name")
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Main {

        @SerializedName("temp")
        private double temp;
        @SerializedName("humidity")
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

    }

    public static class Weather {

        @SerializedName("description")
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
