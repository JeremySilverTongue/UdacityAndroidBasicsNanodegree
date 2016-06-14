package com.udacity.silver.habits.data;

public class Habit {

    public final String name;
    public final int timesCompleted;
    public final double timesPerDay;

    public Habit(String name, int timesCompleted, double timesPerDay) {
        this.name = name;
        this.timesCompleted = timesCompleted;
        this.timesPerDay = timesPerDay;
    }
}
