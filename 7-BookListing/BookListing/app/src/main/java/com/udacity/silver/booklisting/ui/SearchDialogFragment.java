package com.udacity.silver.booklisting.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.udacity.silver.booklisting.R;


public class SearchDialogFragment extends DialogFragment {


    public static final String TAG = SearchDialogFragment.class.getSimpleName();

    EditText searchField;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View custom = inflater.inflate(R.layout.fragment_search_dialog, null);

//        stock = (EditText) custom.findViewById(R.id.dialog_stock);

//        stock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                addStock();
//                return true;
//            }
//        });
        builder.setView(custom);

        builder.setMessage(getString(R.string.dialog_title));
//        builder.setPositiveButton(getString(R.string.dialog_add),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        addStock();
//                    }
//                });
        builder.setNegativeButton(getString(R.string.dialog_cancel), null);

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }


}
