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
    @BindView(R.id.product_name)
    TextView name;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.quantity)
    TextView quantity;
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

        Glide.with(this).load(new File(product.imagePath)).into(image);

        dbHelper = new InventoryDbHelper(this);
    }


    private void modifyQuantity(int delta) {
        product.quantity += delta;
        quantity.setText(String.format(Locale.getDefault(), "%d", product.quantity));
        dbHelper.changeQuantity(product.name, delta);
    }

    public void minus5(View view) {
        modifyQuantity(-5);
    }

    public void minus1(View view) {
        modifyQuantity(-1);
    }

    public void plus1(View view) {
        modifyQuantity(1);
    }

    public void plus5(View view) {
        modifyQuantity(5);
    }

    public void softDelete(View view) {
        delete.setVisibility(View.VISIBLE);
    }


    public void hardDelete(View view) {
        dbHelper.deleteProduct(product.name);
        NavUtils.navigateUpFromSameTask(this);
    }
}
