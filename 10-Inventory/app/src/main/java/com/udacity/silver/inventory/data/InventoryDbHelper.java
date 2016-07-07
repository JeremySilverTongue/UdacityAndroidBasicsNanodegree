package com.udacity.silver.inventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class InventoryDbHelper extends SQLiteOpenHelper {


    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_IMAGE_PATH = "image_path";


    private static final int INDEX_NAME = 0;
    private static final int INDEX_QUANTITY = 1;
    private static final int INDEX_PRICE = 2;
    private static final int INDEX_EMAIL = 3;
    private static final int INDEX_IMAGE_PATH = 4;


    private static final String[] INVENTORY_COLUMNS = {
            COLUMN_NAME,
            COLUMN_QUANTITY,
            COLUMN_PRICE,
            COLUMN_EMAIL,
            COLUMN_IMAGE_PATH
    };
    private static final String TABLE_NAME = "inventory";
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 3;


    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER DEFAULT 0, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_IMAGE_PATH + " TEXT, " +
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
        values.put(COLUMN_EMAIL, product.vendorEmail);
        values.put(COLUMN_IMAGE_PATH, product.imagePath);


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    private Product getProduct(String name) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, INVENTORY_COLUMNS, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);

        Product product = null;

        if (cursor.moveToFirst()) {
            product = getProductFromCursor(cursor);
        }

        cursor.close();
        db.close();

        return product;


    }

    @NonNull
    private Product getProductFromCursor(Cursor cursor) {
        return new Product(
                cursor.getString(INDEX_NAME),
                cursor.getInt(INDEX_QUANTITY),
                cursor.getInt(INDEX_PRICE),
                cursor.getString(INDEX_EMAIL),
                cursor.getString(INDEX_IMAGE_PATH)
        );
    }

    public void changeQuantity(String name, int delta) {
        Product product = getProduct(name);
        addProduct(new Product(product.name, product.quantity + delta, product.priceInCents, product.vendorEmail, product.imagePath));
    }


    public List<Product> getAllProducts() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, INVENTORY_COLUMNS, null, null, null, null, COLUMN_NAME);

        ArrayList<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(getProductFromCursor(cursor));
        }

        cursor.close();
        db.close();
        return products;
    }

    public void deleteProduct(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{name});
        db.close();
    }

}
