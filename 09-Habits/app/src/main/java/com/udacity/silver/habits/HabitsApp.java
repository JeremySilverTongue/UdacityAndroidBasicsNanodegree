package com.udacity.silver.habits;

import android.app.Application;

import timber.log.Timber;

public class HabitsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.uprootAll();
        Timber.plant(new Timber.DebugTree());
    }
}
