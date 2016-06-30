package com.udacity.silver.inventory.data;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Product implements Parcelable {

    public final String name;
    public final int priceInCents;
    public int quantity;


    private DecimalFormat dollarFormat;



//    public final String vendorEmail;
//    public final String imagePath;


    public Product(String name, int quantity, int priceInCents) {
        this.name = name;
        this.quantity = quantity;
        this.priceInCents = priceInCents;

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }

    private Product(Parcel in){
        this.name = in.readString();
        this.quantity = in.readInt();
        this.priceInCents = in.readInt();

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

    }



    public String getFormattedPrice(){
        return dollarFormat.format((float)priceInCents/100);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", priceInCents=" + priceInCents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return quantity == product.quantity && priceInCents == product.priceInCents && name.equals(product.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + priceInCents;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeInt(priceInCents);

    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>(){
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };





}
