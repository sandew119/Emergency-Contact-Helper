package com.example.emergencycontacthelper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContactAdapter extends CursorAdapter {

    public ContactAdapter(Context context, Cursor c) {
        super(context, c, 0);
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

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));

        tvName.setText(name);
        tvPhone.setText(phone);
        tvNote.setText(note);
    }
}