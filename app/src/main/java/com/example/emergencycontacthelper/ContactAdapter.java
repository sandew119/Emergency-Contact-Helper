package com.example.emergencycontacthelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ContactAdapter extends CursorAdapter {

    private DatabaseHelper dbHelper;

    public ContactAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvNote = view.findViewById(R.id.tvNote);
        ImageButton btnCall = view.findViewById(R.id.btnCall);
        ImageButton btnEdit = view.findViewById(R.id.btnEdit);
        ImageButton btnDelete = view.findViewById(R.id.btnDelete);

        final long contactId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
        final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        final String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        final String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
        final long userId = cursor.getLong(cursor.getColumnIndexOrThrow("user_id"));

        tvName.setText(name);
        tvPhone.setText(phone);
        tvNote.setText(note);

        // Call icon click
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            context.startActivity(intent);
        });

        // Edit icon click
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditContactActivity.class);
            intent.putExtra("CONTACT_ID", contactId);
            intent.putExtra("USER_ID", userId);
            context.startActivity(intent);
        });

        // Delete icon click
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete " + name + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteContact(contactId);
                        // Refresh the list
                        if (context instanceof ContactListActivity) {
                            ((ContactListActivity) context).onResume();
                        }
                        Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}