package com.example.emergencycontacthelper;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class QuickDialActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private Button btnBack;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_dial);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_quick_dial);
        btnBack = findViewById(R.id.btnBack);
        userId = getIntent().getLongExtra("USER_ID", -1);

        btnBack.setOnClickListener(v -> finish());

        loadQuickDialContacts();
    }

    private void loadQuickDialContacts() {
        Cursor cursor = dbHelper.getQuickDialContacts(userId);
        ContactAdapter adapter = new ContactAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadQuickDialContacts();
    }
}