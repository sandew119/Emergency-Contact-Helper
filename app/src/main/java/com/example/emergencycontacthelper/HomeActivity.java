package com.example.emergencycontacthelper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    private String emergencyNumber = "tel:119";
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the User ID passed from MainActivity
        currentUserId = getIntent().getIntExtra("USER_ID", -1);

        Button btnEmergencyCall = findViewById(R.id.btn_emergency_call);
        Button btnLogout = findViewById(R.id.btn_logout);
        Button btnAddContact = findViewById(R.id.btn_add_contact);

        // 1. EMERGENCY CALL BUTTON
        btnEmergencyCall.setOnClickListener(v -> makePhoneCall());

        // 2. LOGOUT BUTTON
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // 3. ADD CONTACT BUTTON (Placeholder for now)
        btnAddContact.setOnClickListener(v -> {
            Toast.makeText(this, "Add Contact Screen coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    // SAFELY MAKE THE PHONE CALL
    private void makePhoneCall() {
        // Check if permission is NOT granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            // Permission already granted, make the call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(emergencyNumber));
            startActivity(callIntent);
        }
    }

    // HANDLE THE USER'S ANSWER TO THE PERMISSION POP-UP
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall(); // They clicked Yes, make the call!
            } else {
                Toast.makeText(this, "Permission DENIED to make calls", Toast.LENGTH_SHORT).show();
            }
        }
    }
}