package com.udacity.silver.inventory.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.udacity.silver.inventory.R;
import com.udacity.silver.inventory.data.InventoryDbHelper;
import com.udacity.silver.inventory.data.Product;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddProductDialog extends DialogFragment {

    @BindView(R.id.product_name)
    EditText nameEditText;

    @BindView(R.id.price)
    EditText priceEditText;

    @BindView(R.id.quantity)
    EditText quantityEditText;

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

        // TODO: Add some verification that the item is legit

        Product product = new Product(name, quantity, price);
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

}
