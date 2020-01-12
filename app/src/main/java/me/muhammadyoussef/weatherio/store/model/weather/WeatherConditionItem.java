package me.muhammadyoussef.weatherio.store.model.weather;

public class WeatherConditionItem {

    @WeatherItemType
    private final int type;
    private final String name;
    private final String value;

    public WeatherConditionItem(@WeatherItemType int type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @WeatherItemType
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
