package com.udacity.silver.habits.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.silver.habits.data.HabitContract.HabitTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public List<Habit> getHabitsList() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(HabitTable.TABLE_NAME, HabitTable.HABIT_COLUMNS, null, null, null, null, null);

        ArrayList<Habit> output = new ArrayList<>();

        while (cursor.moveToNext()) {
            output.add(extractHabit(cursor));
        }

        Collections.sort(output, new Comparator<Habit>() {
            @Override
            public int compare(Habit lhs, Habit rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });

        cursor.close();

        return output;

    }

    public void incrementHabit(String name) {
        Habit habit = getHabit(name);
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = packageHabit(new Habit(name, habit.timesCompleted + 1, habit.creationDate));
        database.insert(HabitTable.TABLE_NAME, null, values);
    }

    private Habit getHabit(String name) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(HabitTable.TABLE_NAME, HabitTable.HABIT_COLUMNS, HabitTable.COLUMN_NAME + " = ?", new String[]{name}, null, null, null);
        if (cursor.moveToFirst()) {
            return extractHabit(cursor);
        } else {
            return new Habit(name, 0, "");
        }
    }

    private ContentValues packageHabit(Habit habit) {
        ContentValues values = new ContentValues(3);
        values.put(HabitTable.COLUMN_NAME, habit.name);
        values.put(HabitTable.COLUMN_COUNT, habit.timesCompleted);
        values.put(HabitTable.COLUMN_CREATION_DATE, habit.creationDate);
        return values;
    }

    private Habit extractHabit(Cursor cursor) {
        String habitName = cursor.getString(1);
        int count = cursor.getInt(2);
        String creationDate = cursor.getString(3);
        return new Habit(habitName, count, creationDate);
    }
}
