package com.udacity.silver.layoutxml;

import android.app.Application;

import timber.log.Timber;

public class XmlLayoutApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
