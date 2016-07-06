package com.udacity.silver.inventory.ui;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    static final String PRODUCT_KEY = "product";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.product_name)
    TextView name;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.image)
    ImageView image;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.price)
    TextView price;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.quantity)
    TextView quantity;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.hard_delete)
    Button hardDelete;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.delete)
    Button delete;

    private Product product;
    private InventoryDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        product = getIntent().getParcelableExtra(PRODUCT_KEY);

        name.setText(product.name);
        price.setText(product.getFormattedPrice());
        quantity.setText(String.format(Locale.getDefault(), "%d", product.quantity));

        if (!product.imagePath.isEmpty()) {
            Glide.with(this).load(new File(product.imagePath)).into(image);
        }

        dbHelper = new InventoryDbHelper(this);
    }


    private void modifyQuantity(int delta) {
        product.quantity += delta;
        quantity.setText(String.format(Locale.getDefault(), "%d", product.quantity));
        dbHelper.changeQuantity(product.name, delta);
    }

    public void minus5(@SuppressWarnings("UnusedParameters") View view) {
        modifyQuantity(-5);
    }

    public void minus1(@SuppressWarnings("UnusedParameters") View view) {
        modifyQuantity(-1);
    }

    public void plus1(@SuppressWarnings("UnusedParameters") View view) {
        modifyQuantity(1);
    }

    public void plus5(@SuppressWarnings("UnusedParameters") View view) {
        modifyQuantity(5);
    }

    public void softDelete(@SuppressWarnings("UnusedParameters") View view) {
        delete.setVisibility(View.GONE);
        hardDelete.setVisibility(View.VISIBLE);
    }


    public void hardDelete(@SuppressWarnings("UnusedParameters") View view) {
        //noinspection ResultOfMethodCallIgnored
        new File(product.imagePath).delete();
        dbHelper.deleteProduct(product.name);
        NavUtils.navigateUpFromSameTask(this);
    }


    public void order(@SuppressWarnings("UnusedParameters") View view) {
        product.sendEmailToSupplier(this);
    }
}
