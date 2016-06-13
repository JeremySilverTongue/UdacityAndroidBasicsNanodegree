package com.udacity.silver.booklisting;

import android.app.Application;

import timber.log.Timber;

public class BookListingApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}

