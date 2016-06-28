package com.udacity.silver.inventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class InventoryDbHelper extends SQLiteOpenHelper {


    static final String COLUMN_NAME = "name";
    static final String COLUMN_QUANTITY = "quantity";
    static final String COLUMN_PRICE = "price";
    static final int INDEX_NAME = 0;
    static final int INDEX_QUANTITY = 1;
    static final int INDEX_PRICE = 2;
    static final String[] INVENTORY_COLUMNS = {
            COLUMN_NAME,
            COLUMN_QUANTITY,
            COLUMN_PRICE
    };
    static final String TABLE_NAME = "inventory";
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER DEFAULT 0, " +
                COLUMN_PRICE + " INTEGER, " +
                "UNIQUE (" + COLUMN_NAME + ") ON CONFLICT REPLACE" +

                ")";

        db.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues(3);
        values.put(COLUMN_NAME, product.name);
        values.put(COLUMN_QUANTITY, product.quantity);
        values.put(COLUMN_PRICE, product.priceInCents);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        Timber.d("Adding product: %s", product);
    }

    public Product getProduct(String name) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, INVENTORY_COLUMNS, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);

        Product product = null;

        if (cursor.moveToFirst()) {
            product = new Product(cursor.getString(INDEX_NAME), cursor.getInt(INDEX_QUANTITY), cursor.getInt(INDEX_PRICE));
        }

        return product;


    }

    public void changeQuantity(String name, int delta) {

        Product product = getProduct(name);

        addProduct(new Product(product.name, product.quantity + delta, product.priceInCents));


    }


    public List<Product> getAllProducts() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, INVENTORY_COLUMNS, null, null, null, null, COLUMN_NAME);

        ArrayList<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(new Product(cursor.getString(INDEX_NAME), cursor.getInt(INDEX_QUANTITY), cursor.getInt(INDEX_PRICE)));
        }

        cursor.close();
        return products;

    }

}
