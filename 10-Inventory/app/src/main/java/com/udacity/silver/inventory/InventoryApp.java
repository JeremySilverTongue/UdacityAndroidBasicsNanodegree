package com.udacity.silver.inventory;

import android.app.Application;

import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import timber.log.Timber;

public class InventoryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }

        InventoryDbHelper db = new InventoryDbHelper(this);

        db.addProduct(new Product("Herp", 2, 1234, "vendor@example.com", ""));
        db.addProduct(new Product("Derp", 5, 2345, "vendor@example.com", ""));
    }

}