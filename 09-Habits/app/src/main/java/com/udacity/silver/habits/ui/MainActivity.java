package com.udacity.silver.habits.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.udacity.silver.habits.R;

public class MainActivity extends AppCompatActivity implements AddHabitDialog.HabitAdder {

    private static final String HABIT_DIALOG_TAG = "Habit Dialog Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addHabit(View view) {
        new AddHabitDialog().show(getFragmentManager(), HABIT_DIALOG_TAG);
    }

    @Override
    public void addHabit(String habitName) {

    }
}
