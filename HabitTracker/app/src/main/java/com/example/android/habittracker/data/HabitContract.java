package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "habit";
        public static final String COLUMN_SHIFT = "shift";

        public static final int MORNING = 1;
        public static final int AFTERNOON = 2;
        public static final int NIGHT = 3;
    }
}
