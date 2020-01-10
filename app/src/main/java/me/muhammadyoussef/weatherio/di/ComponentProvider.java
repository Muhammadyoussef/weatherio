package me.muhammadyoussef.weatherio.di;

@FunctionalInterface
public interface ComponentProvider<T> {

    T getComponent();
}
