package com.udacity.silver.habits.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.udacity.silver.habits.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AddHabitDialog extends DialogFragment {


    @BindView(R.id.habit_edit_text)
    EditText habit;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View custom = inflater.inflate(R.layout.dialog_add_habit, null);
        builder.setView(custom);
        builder.setMessage(getString(R.string.dialog_title));
        builder.setPositiveButton(getString(R.string.dialog_add),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addHabit();
                    }
                });
        builder.setNegativeButton(getString(R.string.dialog_cancel), null);

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        ButterKnife.bind(this, custom);

        habit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                addHabit();
                return true;
            }
        });

        return dialog;
    }

    private void addHabit() {
        Timber.d("I am adding a habit: %s", habit.getText().toString());
        dismissAllowingStateLoss();
    }


    interface HabitAdder {

        void addHabit(String habitName);

    }

}
