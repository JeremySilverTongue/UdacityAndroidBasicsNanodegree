package com.udacity.silver.udacitytour;

import android.app.Application;

import timber.log.Timber;


public class TourApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
