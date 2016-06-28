package com.udacity.silver.habits.data;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

public class Habit {

    public final String name;
    public final int timesCompleted;
    public final double timesPerDay;
    public final String creationDate;

    public Habit(String name, int timesCompleted, String creationDate) {
        this.name = name;
        this.timesCompleted = timesCompleted;
        this.creationDate = creationDate;

        double tryingToGetTimesPerDay = 0;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
            java.util.Date creationTime = format.parse(creationDate);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(creationTime);

            int daysSinceCreation = Math.max(1, Days.daysBetween(new DateTime(creationTime), DateTime.now()).getDays());
            tryingToGetTimesPerDay = ((float) timesCompleted) / daysSinceCreation;

        } catch (ParseException e) {
            Timber.e(e, "WTF parse ");
        }

        timesPerDay = tryingToGetTimesPerDay;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", timesCompleted=" + timesCompleted +
                ", timesPerDay=" + timesPerDay +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
