package com.udacity.silver.inventory.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class AddProductDialog extends DialogFragment {

    public static int GET_IMAGE_TAG = 1;

    @BindView(R.id.preview)
    ImageView preview;

    @BindView(R.id.product_name)
    EditText nameEditText;

    @BindView(R.id.price)
    EditText priceEditText;

    @BindView(R.id.quantity)
    EditText quantityEditText;

    @BindView(R.id.email)
    EditText emailEditText;

    private String photoPath;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_add_product, null);

        builder.setView(view);
        builder.setMessage("Add Product");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addProduct();
            }
        });

        builder.setNegativeButton("Cancel", null);

        Dialog dialog = builder.create();

        ButterKnife.bind(this, view);


        return dialog;


    }


    private void addProduct() {
        InventoryDbHelper db = new InventoryDbHelper(getActivity());
        String name = nameEditText.getText().toString();
        int price = Integer.parseInt(priceEditText.getText().toString());
        int quantity = Integer.parseInt(quantityEditText.getText().toString());
        String email = emailEditText.getText().toString();

        // TODO: Add some verification that the item is legit

        Product product = new Product(name, quantity, price, email, photoPath);
        db.addProduct(product);
        dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    public void receivePhoto(Intent data, String photoPath) {
        this.photoPath = photoPath;
        Timber.d("Got a photopath: %s", photoPath);
        Glide.with(getActivity()).load(photoPath).into(preview);
        Timber.d("Totally got a photo, yo");
    }


}
