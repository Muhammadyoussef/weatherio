package me.muhammadyoussef.weatherio.store.model.weather;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;
import me.muhammadyoussef.weatherio.utils.ResourcesUtil;

@ApplicationScope
public class WeatherMapper {

    private final ResourcesUtil resourcesUtil;

    @Inject
    WeatherMapper(ResourcesUtil resourcesUtil) {
        this.resourcesUtil = resourcesUtil;
    }

    public List<WeatherConditionItem> toViewModels(WeatherApiResponse apiResponse) {
        List<WeatherConditionItem> conditionItems = new ArrayList<>();
        String condition = "N/A";
        String location = apiResponse.getName();
        int humidity = apiResponse.getMain().getHumidity();
        double temp = apiResponse.getMain().getTemp();
        if (apiResponse.getWeather().size() > 0) {
            condition = apiResponse.getWeather().get(0).getDescription();
        }

        conditionItems.add(new WeatherConditionItem(
                WeatherItemType.TEMPERATURE,
                resourcesUtil.getString(R.string.temp),
                temp + " °C"));
        conditionItems.add(new WeatherConditionItem(
                WeatherItemType.HUMIDITY,
                resourcesUtil.getString(R.string.humidity),
                humidity + " %"));
        conditionItems.add(new WeatherConditionItem(
                WeatherItemType.LOCATION,
                resourcesUtil.getString(R.string.location),
                location));
        conditionItems.add(new WeatherConditionItem(
                WeatherItemType.CONDITION,
                resourcesUtil.getString(R.string.condition),
                condition));
        return conditionItems;
    }
}
