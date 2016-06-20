package com.udacity.silver.habits.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.udacity.silver.habits.data.HabitContract.HabitTable;


import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class HabitDbHelper extends SQLiteOpenHelper {


    private static final String NAME = "Habits.db";
    private static final int VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + HabitTable.TABLE_NAME + " (" +
                HabitTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HabitTable.COLUMN_NAME + " TEXT NOT NULL, " +
                HabitTable.COLUMN_COUNT + " INTEGER DEFAULT 0, " +
                HabitTable.COLUMN_CREATION_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "UNIQUE (" + HabitTable.COLUMN_NAME + ") ON CONFLICT REPLACE);";
        //        builder.append(Quote.COLUMN_PERCENTAGE_CHANGE).append(" REAL NOT NULL, ");
//        builder.append(Quote.COLUMN_HISTORY).append(" TEXT NOT NULL, ");

        db.execSQL(builder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + HabitTable.TABLE_NAME);

        onCreate(db);
    }

    public void addHabit(String name) {
        Timber.d("Inserting: %s", name);
        ContentValues values = new ContentValues();
        values.put(HabitTable.COLUMN_NAME, name);
        SQLiteDatabase database = getWritableDatabase();
        database.insert(HabitTable.TABLE_NAME, null, values);
    }

    public void printQuery() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(HabitTable.TABLE_NAME, HabitTable.HABIT_COLUMNS, null, null, null, null, null);



                ArrayList<Habit> output = new ArrayList<>();
        for (String habitName : habits) {

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

        cursor.moveToFirst();
        do {

            String habitName = cursor.getString(1);
            int count = cursor.getInt(2);

            DateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.US);
            try {
                java.util.Date creationTime = format.parse(cursor.getString(3));

                Calendar calendar = Calendar.getInstance();

                calendar.setTime(creationTime);

                Days.daysBetween(new DateTime(creationTime), DateTime.now());

            } catch (ParseException e){
                Timber.e(e, "WTF parse ");
            }


            Timber.d("Habit %s Count %d Creation: %s ", , , cursor.getString(3));





        }
        while (cursor.moveToNext());




        cursor.close();

    }

//    public void incrementHabit(String name) {
//        SQLiteDatabase database = getWritableDatabase();
//
//
//        database.update(HabitTable.TABLE_NAME, null, values);
//    }


}
