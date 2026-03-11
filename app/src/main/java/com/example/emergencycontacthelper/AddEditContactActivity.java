package com.example.emergencycontacthelper;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditContactActivity extends AppCompatActivity {

    private EditText etName, etPhone, etNote;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    private long userId = -1;
    private long contactId = -1;

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

        if (contactId != -1) {
            loadContact();
        }

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void loadContact() {
        Cursor cursor = dbHelper.getContactById(contactId);
        if (cursor != null && cursor.moveToFirst()) {
            etName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
            etPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)));
            etNote.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTES)));
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success;
        if (contactId == -1) {
            long result = dbHelper.addContact(userId, name, phone, note);
            success = result != -1;
        } else {
            int result = dbHelper.updateContact(contactId, name, phone, note);
            success = result > 0;
        }

        if (success) {
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving contact", Toast.LENGTH_SHORT).show();
        }
    }
}