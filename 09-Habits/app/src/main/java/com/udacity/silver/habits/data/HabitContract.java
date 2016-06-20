package com.udacity.silver.habits.data;

import android.content.Context;
import android.provider.BaseColumns;

/**
 * Created by silver on 6/20/16.
 */

public class HabitContract {



    static final class HabitTable implements BaseColumns {

        static final String TABLE_NAME = "habits";

        static final String COLUMN_NAME = "name";
        static final String COLUMN_COUNT = "count";
        static final String COLUMN_CREATION_DATE = "absolute_change";

        public static final String[] HABIT_COLUMNS = {
                _ID,
                COLUMN_NAME,
                COLUMN_COUNT,
                COLUMN_CREATION_DATE
        };

    }
}
