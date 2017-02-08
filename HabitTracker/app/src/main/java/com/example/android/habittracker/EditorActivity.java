package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mHabitNameEditText;
    private Spinner mShiftSpinner;
    private HabitDbHelper mHabitDbHelper;
    private int mShift = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mHabitNameEditText = (EditText) findViewById(R.id.edit_habit);
        mShiftSpinner = (Spinner) findViewById(R.id.spinner_shift);

        mHabitDbHelper = new HabitDbHelper(this);
        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter shiftSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_shift_options, android.R.layout.simple_spinner_item);
        shiftSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mShiftSpinner.setAdapter(shiftSpinnerAdapter);

        mShiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.morning))) {
                        mShift = HabitEntry.MORNING;
                    } else if (selection.equals(getString(R.string.afternoon))) {
                        mShift = HabitEntry.AFTERNOON;
                    } else {
                        mShift = HabitEntry.NIGHT;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mShift = 0;
            }
        });
    }

    private void insertPet() {
        String habitNameString = mHabitNameEditText.getText().toString().trim();
        int shift = mShift;

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitNameString);
        values.put(HabitEntry.COLUMN_SHIFT, shift);

        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.habit_number) + " " + newRowId + " " + getString(R.string.saved), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                insertPet();
                finish();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}