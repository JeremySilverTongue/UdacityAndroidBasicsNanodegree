package com.udacity.silver.habits.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    public static ArrayList<Habit> getHabits(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> habits = prefs.getStringSet(HABIT_SET_KEY, new HashSet<String>());
        ArrayList<Habit> output = new ArrayList<>();
        for (String habitName : habits) {
            int count = prefs.getInt(habitName + HABIT_COUNT_SUFFIX, 0);
            long creationTime = prefs.getLong(habitName + HABIT_TIME_SUFFIX, 0);
            double daysSinceCreation = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - creationTime);
            daysSinceCreation = Math.max(daysSinceCreation, 1);
            output.add(new Habit(habitName, count, count / daysSinceCreation));
        }
        Collections.sort(output, new Comparator<Habit>() {
            @Override
            public int compare(Habit lhs, Habit rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });
        return output;
    }


}
