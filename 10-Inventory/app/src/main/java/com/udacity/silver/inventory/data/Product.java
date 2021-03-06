package com.udacity.silver.inventory.data;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.udacity.silver.inventory.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Product implements Parcelable {

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public final String name;
    public final int priceInCents;
    public final String vendorEmail;
    public final String imagePath;
    private final DecimalFormat dollarFormat;
    public int quantity;

    public Product(String name, int quantity, int priceInCents, String vendorEmail, String imagePath) {
        this.name = name;
        this.quantity = quantity;
        this.priceInCents = priceInCents;
        this.vendorEmail = vendorEmail;
        this.imagePath = imagePath;

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }

    private Product(Parcel in) {
        this.name = in.readString();
        this.quantity = in.readInt();
        this.priceInCents = in.readInt();
        this.vendorEmail = in.readString();
        this.imagePath = in.readString();

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeInt(priceInCents);
        dest.writeString(vendorEmail);
        dest.writeString(imagePath);
    }

    public String getFormattedPrice() {
        return dollarFormat.format((float) priceInCents / 100);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", priceInCents=" + priceInCents +
                ", quantity=" + quantity +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public void sendEmailToSupplier(Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + vendorEmail)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body, name, dollarFormat.format((float) priceInCents / 200)));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "Send Email"));
        } else {
            Toast.makeText(context, "Order more!", Toast.LENGTH_LONG).show();
        }
    }
}
