package com.udacity.silver.inventory.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.bumptech.glide.Glide;
import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;


public class AddProductDialog extends DialogFragment {

    static final int GET_IMAGE_TAG = 1;

    ImageView preview;
    EditText nameEditText;
    CurrencyEditText priceEditText;
    EditText quantityEditText;
    EditText emailEditText;

    private String photoPath;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_add_product, null);

        builder.setView(view);
        builder.setMessage("Add Product");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addProduct();
            }
        });

        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        addProduct();

                    }
                });
            }
        });

        preview = (ImageView) view.findViewById(R.id.preview);
        nameEditText = (EditText) view.findViewById(R.id.product_name);
        priceEditText = (CurrencyEditText) view.findViewById(R.id.price);
        quantityEditText = (EditText) view.findViewById(R.id.quantity);
        emailEditText = (EditText) view.findViewById(R.id.email);


        return dialog;


    }


    private void addProduct() {
        InventoryDbHelper db = new InventoryDbHelper(getActivity());

        String name = nameEditText.getText().toString();
        String price = Long.toString(priceEditText.getRawValue());
        String quantity = quantityEditText.getText().toString();
        String email = quantityEditText.getText().toString();


        if (name.isEmpty()) {
            showError(getString(R.string.error_name));
        } else if (price.isEmpty()) {
            showError(getString(R.string.error_price));
        } else if (quantity.isEmpty()) {
            showError(getString(R.string.error_quantity));
        } else if (email.isEmpty()) {
            showError(getString(R.string.error_email));
        } else {
            Product product = new Product(name, Integer.parseInt(quantity), Integer.parseInt(price), email, photoPath);
            db.addProduct(product);
            dismissAllowingStateLoss();
        }

    }

    private void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    void receivePhoto(String photoPath) {
        this.photoPath = photoPath;
        Glide.with(getActivity()).load(photoPath).into(preview);
    }


}
