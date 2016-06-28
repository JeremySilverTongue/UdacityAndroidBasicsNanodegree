package com.udacity.silver.inventory.data;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.ArrayList;
import java.util.List;


public class DatabaseTest extends AndroidTestCase {


    private InventoryDbHelper db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new InventoryDbHelper(context);
    }


    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testAddEntry() {

        Product testProduct = new Product("Derps", 42, 420);

        db.addProduct(testProduct);
        Product returnedProduct = db.getProduct("Derps");
        assertEquals(testProduct, returnedProduct);
    }

    public void testAddDuplicateEntry() {

        Product testProduct = new Product("Derps", 42, 420);

        db.addProduct(testProduct);
        db.addProduct(testProduct);
        Product returnedProduct = db.getProduct("Derps");
        assertEquals(testProduct, returnedProduct);
    }

    public void testAddMultipleEntries() {


        Product testProduct1 = new Product("Derps", 42, 420);
        Product testProduct2 = new Product("Herps", 12, 360);
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct1);
        productList.add(testProduct2);

        db.addProduct(testProduct1);
        db.addProduct(testProduct2);

        List<Product> returnedList = db.getAllProducts();

        assertEquals(productList, returnedList);


    }

}
