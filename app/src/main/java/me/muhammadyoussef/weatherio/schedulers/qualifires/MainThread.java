package me.muhammadyoussef.weatherio.schedulers.qualifires;

import javax.inject.Qualifier;

import me.muhammadyoussef.weatherio.schedulers.ThreadSchedulers;

/**
 * This qualifier is used for distinguishing between different {@link ThreadSchedulers} for dependency injection.
 */

@Qualifier
public @interface MainThread {
}