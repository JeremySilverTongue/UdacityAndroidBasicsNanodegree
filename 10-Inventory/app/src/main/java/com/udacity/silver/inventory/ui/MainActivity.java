package com.udacity.silver.inventory.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener  {


    private static final String DIALOG_TAG = "Product dialog";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        InventoryDbHelper db = new InventoryDbHelper(this);

        db.addProduct(new Product("Herp", 2, 1234, "vendor@example.com",""));
        db.addProduct(new Product("Derp", 5, 2345, "vendor@example.com",""));

        productAdapter = new ProductAdapter(this, null);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        productAdapter.refresh();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        productAdapter.refresh();
    }

    public void addProduct(View view) {
        new AddProductDialog().show(getFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Timber.d("Fragment dismissed");
        productAdapter.refresh();
    }
}
