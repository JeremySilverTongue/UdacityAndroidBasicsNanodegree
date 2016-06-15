package com.udacity.silver.habits.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public final class Storage {

    private static final String HABIT_SET_KEY = "habits";
    private static final String HABIT_TIME_SUFFIX = "-time";
    private static final String HABIT_COUNT_SUFFIX = "-count";


    private Storage() {
    }


    public static void addHabit(Context context, String habitName) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Set<String> habits = prefs.getStringSet(HABIT_SET_KEY, new HashSet<String>());

        habits.add(habitName);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(HABIT_SET_KEY, habits);
        editor.putInt(habitName + HABIT_COUNT_SUFFIX, 0);
        editor.putLong(habitName + HABIT_TIME_SUFFIX, System.currentTimeMillis());
        editor.apply();

        Timber.d("%s", habits.toString());
    }


    public static ArrayList<Habit> getHabits(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Set<String> habits = prefs.getStringSet(HABIT_SET_KEY, new HashSet<String>());
        ArrayList<Habit> output = new ArrayList<>();

        for (String habitName : habits) {

            int count = prefs.getInt(habitName + HABIT_COUNT_SUFFIX, 0);
            long creationTime = prefs.getLong(habitName + HABIT_TIME_SUFFIX, 0);

            double daysSinceCreation = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - creationTime);

            output.add(new Habit(habitName, count, 0));


        }

        return output;

    }


}
