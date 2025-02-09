package me.muhammadyoussef.weatherio.di.qualifier;

import javax.inject.Qualifier;

/**
 * This qualifier is used for distinguishing between similar objects during a dependency injection.
 * (i.e. Activity context and App context), acts like {@link javax.inject.Named}
 */

@Qualifier
public @interface ForApplication {
}