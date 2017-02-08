package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    private HabitDbHelper mHabitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        mHabitDbHelper = new HabitDbHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        SQLiteDatabase db = mHabitDbHelper.getReadableDatabase();
        String[] projection = {HabitEntry._ID, HabitEntry.COLUMN_HABIT_NAME, HabitEntry.COLUMN_SHIFT};
        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null, null);

        displayView.setText(getString(R.string.habits_registered) + " " + cursor.getCount() + "\n\n");
        displayView.append(HabitEntry._ID + " - " + HabitEntry.COLUMN_HABIT_NAME + " - " + HabitEntry.COLUMN_SHIFT + "\n");
        try {
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int shiftColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_SHIFT);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentShift = cursor.getInt(shiftColumnIndex);

                displayView.append("\n" + currentId + " - " + currentName + " - " + currentShift);
            }
        } finally {
            cursor.close();
        }
    }
}
