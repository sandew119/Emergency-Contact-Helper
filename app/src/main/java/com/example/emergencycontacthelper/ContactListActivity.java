package com.example.emergencycontacthelper;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ContactListActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listView;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_contacts);

        userId = getIntent().getLongExtra("USER_ID",-1);

        loadContacts();
    }

    private void loadContacts(){

        Cursor cursor = dbHelper.getUserContacts(userId);

        String[] from = {"name","phone","note"};

        int[] to = {
                android.R.id.text1,
                android.R.id.text2,
                android.R.id.text2
        };

        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(
                        this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        from,
                        to,
                        0
                );

        listView.setAdapter(adapter);
    }
}