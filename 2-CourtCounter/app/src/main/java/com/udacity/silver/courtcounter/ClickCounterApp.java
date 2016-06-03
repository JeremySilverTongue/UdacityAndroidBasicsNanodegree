package com.udacity.silver.courtcounter;

import android.app.Application;

import timber.log.Timber;


public class ClickCounterApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
