package com.example.emergencycontacthelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister;

    DatabaseHelper dbHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tvRegister);

        // Go to Register Page
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash password before checking
        String hashedPassword = PasswordUtils.hashPassword(password);

        long userId = dbHelper.loginUser(username, hashedPassword);

        if (userId > -1) {

            // Save session
            sessionManager.createLoginSession(userId);

            // Navigate to Dashboard and pass USER_ID
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);

            finish();

        } else {
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}