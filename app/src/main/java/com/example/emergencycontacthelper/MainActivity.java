package com.example.emergencycontacthelper;

import android.os.Bundle;
import android.util.Log;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Temporary DB check - remove later after confirmation
        try {
            Log.d("DB_TEST", "Starting DB initialization...");

            DatabaseHelper dbHelper = new DatabaseHelper(this);

            // Force creation/open
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Log.d("DB_TEST", "Database opened OK");

            // Check tables
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{DatabaseHelper.TABLE_USERS});
            if (cursor.getCount() > 0) {
                Log.d("DB_TEST", "Users table exists");
            } else {
                Log.e("DB_TEST", "Users table missing");
            }
            cursor.close();

            cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{DatabaseHelper.TABLE_CONTACTS});
            if (cursor.getCount() > 0) {
                Log.d("DB_TEST", "EmergencyContacts table exists");
            } else {
                Log.e("DB_TEST", "EmergencyContacts table missing");
            }
            cursor.close();

            db.close();
            Log.d("DB_TEST", "DB check completed successfully");

        } catch (Exception e) {
            Log.e("DB_TEST", "DB error: " + e.getClass().getSimpleName() + " - " + e.getMessage(), e);
        }

        // Rest of your UI code here later...
    }
}