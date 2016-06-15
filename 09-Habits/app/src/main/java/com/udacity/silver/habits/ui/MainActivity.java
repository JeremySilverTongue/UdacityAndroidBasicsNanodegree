package com.udacity.silver.habits.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.silver.habits.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private static final String HABIT_DIALOG_TAG = "Habit Dialog Fragment";
    @BindView(R.id.habits_list)
    RecyclerView habitsList;
    private HabitAdapter habitAdapter;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        habitAdapter = new HabitAdapter(this);


        habitsList.setAdapter(habitAdapter);

        habitsList.setLayoutManager(new LinearLayoutManager(this));

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                habitAdapter.reloadHabits();
            }


        };

        PreferenceManager.getDefaultSharedPreferences(this).

                registerOnSharedPreferenceChangeListener(listener);

    }

    public void addHabit(View view) {
        new AddHabitDialog().show(getFragmentManager(), HABIT_DIALOG_TAG);

    }


}
