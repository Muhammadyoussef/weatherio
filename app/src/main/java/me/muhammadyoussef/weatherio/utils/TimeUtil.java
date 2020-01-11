package me.muhammadyoussef.weatherio.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;

@ApplicationScope
public class TimeUtil {

    private static final String FULL_DATE_FORMAT = "hh:mm aaa MMM d, YYY";

    private final DateFormat dateFormatter;

    @Inject
    public TimeUtil() {
        dateFormatter = new SimpleDateFormat(FULL_DATE_FORMAT, Locale.getDefault());
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public String formatFullDate(long timeStamp) {
        return dateFormatter.format(new Date(timeStamp));
    }
}
