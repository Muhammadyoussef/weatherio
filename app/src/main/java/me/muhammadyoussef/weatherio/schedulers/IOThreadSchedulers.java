package me.muhammadyoussef.weatherio.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Input/Output-specific Schedulers
 */

public class IOThreadSchedulers implements ThreadSchedulers {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler workerThread() {
        return Schedulers.io();
    }
}