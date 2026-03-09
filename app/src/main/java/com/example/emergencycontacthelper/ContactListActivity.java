package com.example.emergencycontacthelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ContactListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private Button btnAdd;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_contacts);
        btnAdd = findViewById(R.id.btnAdd);
        userId = getIntent().getLongExtra("USER_ID", -1);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ContactListActivity.this, AddEditContactActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        loadContacts();
    }

    private void loadContacts() {
        Cursor cursor = dbHelper.getUserContacts(userId);
        ContactAdapter adapter = new ContactAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ContactListActivity.this, AddEditContactActivity.class);
            intent.putExtra("CONTACT_ID", id);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(ContactListActivity.this)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteContact(id);
                        loadContacts();
                        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }
}