package com.udacity.silver.inventory;

import android.app.Application;

import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;


public class InventoryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        InventoryDbHelper db = new InventoryDbHelper(this);

        db.addProduct(new Product("Test Product 1", 2, 1234, "vendor@example.com", ""));
        db.addProduct(new Product("Test Product 2", 5, 2345, "vendor@example.com", ""));
    }

}
