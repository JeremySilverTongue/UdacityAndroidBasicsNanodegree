package com.udacity.silver.habits.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.silver.habits.R;
import com.udacity.silver.habits.data.HabitDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private static final String HABIT_DIALOG_TAG = "Habit Dialog Fragment";
    @BindView(R.id.habits_list)
    RecyclerView habitsList;

    @BindView(R.id.empty_view)
    TextView emptyView;

    private HabitDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        HabitAdapter habitAdapter = new HabitAdapter(this, emptyView);
        habitsList.setAdapter(habitAdapter);
        habitsList.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new HabitDbHelper(this);

        dbHelper.addHabit("Test Habit");
        dbHelper.addHabit("Test Habit2");
        dbHelper.addHabit("b");
        dbHelper.addHabit("a");
        dbHelper.printQuery();

        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(habitAdapter);

    }

    public void addHabit(View view) {
        new AddHabitDialog().show(getFragmentManager(), HABIT_DIALOG_TAG);
    }
}
