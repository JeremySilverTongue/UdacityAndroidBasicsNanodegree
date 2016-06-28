package com.udacity.silver.habits.data;

import android.provider.BaseColumns;


class HabitContract {


    static final class HabitTable implements BaseColumns {

        static final String COLUMN_NAME = "name";
        static final String COLUMN_COUNT = "count";
        static final String COLUMN_CREATION_DATE = "absolute_change";
        static final String[] HABIT_COLUMNS = {
                _ID,
                COLUMN_NAME,
                COLUMN_COUNT,
                COLUMN_CREATION_DATE
        };
        static final String TABLE_NAME = "habits";

    }
}
