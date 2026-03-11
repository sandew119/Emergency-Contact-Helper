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

public class ContactAdapter extends CursorAdapter {

    public ContactAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
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

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));

        tvName.setText(name);
        tvPhone.setText(phone);
        tvNote.setText(note);

        // Call icon click
        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            context.startActivity(intent);
        });
    }
}