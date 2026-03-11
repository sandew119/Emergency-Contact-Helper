package com.example.emergencycontacthelper;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        etConfirmPassword = findViewById(R.id.et_reg_confirm_password);
        Button btnRegister = findViewById(R.id.btn_create_account);
        Button btnBackToLogin = findViewById(R.id.btn_back_to_login);

        btnRegister.setOnClickListener(v -> registerUser());
        btnBackToLogin.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError("Username required");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password required");
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        if (dbHelper.addUser(username, password)) {
            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to login screen
        } else {
            Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT).show();
        }
    }
}