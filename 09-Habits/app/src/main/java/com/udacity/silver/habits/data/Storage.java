package com.udacity.silver.habits.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

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
    }

    public static void incrementHabit(Context context, String habitName) {
        String countKey = habitName + HABIT_COUNT_SUFFIX;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int count = prefs.getInt(countKey, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(countKey, count + 1);
        editor.apply();
    }
}
