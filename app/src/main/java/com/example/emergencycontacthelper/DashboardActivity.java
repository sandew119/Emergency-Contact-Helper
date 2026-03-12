package com.example.emergencycontacthelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnSendSMS, btnLogout, btnContacts, btnQuickDial;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnQuickDial = findViewById(R.id.btnQuickDial);
        btnSendSMS = findViewById(R.id.btnSOS);
        btnLogout = findViewById(R.id.btnLogout);
        btnContacts = findViewById(R.id.btnContacts);

        userId = getIntent().getLongExtra("USER_ID", -1);

        // Quick Dial
        btnQuickDial.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuickDialActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        // Send SMS
        btnSendSMS.setOnClickListener(v -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:"));
            smsIntent.putExtra("sms_body", "Emergency Help Needed!");
            startActivity(smsIntent);
        });

        // Manage Contacts
        btnContacts.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactListActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            SessionManager session = new SessionManager(this);
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}