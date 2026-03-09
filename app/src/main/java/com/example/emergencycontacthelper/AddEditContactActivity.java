package com.example.emergencycontacthelper;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditContactActivity extends AppCompatActivity {

    EditText etName, etPhone, etNote;
    Button btnSave;
    DatabaseHelper dbHelper;

    long userId = -1;
    long contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSave);

        userId = getIntent().getLongExtra("USER_ID", -1);
        contactId = getIntent().getLongExtra("CONTACT_ID", -1);

        // If editing, load existing contact
        if (contactId != -1) {
            loadContact();
        }

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void loadContact() {
        var cursor = dbHelper.getContactById(contactId);
        if (cursor.moveToFirst()) {
            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            etNote.setText(cursor.getString(cursor.getColumnIndexOrThrow("note")));
        }
        cursor.close();
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
            return;
        }

        long result;
        if (contactId == -1) {
            // Add new contact
            result = dbHelper.addContact(userId, name, phone, note);
        } else {
            // Update existing
            result = dbHelper.updateContact(contactId, name, phone, note);
        }

        if (result != -1) {
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
            finish(); // Go back to contact list
        } else {
            Toast.makeText(this, "Error saving contact", Toast.LENGTH_SHORT).show();
        }
    }
}