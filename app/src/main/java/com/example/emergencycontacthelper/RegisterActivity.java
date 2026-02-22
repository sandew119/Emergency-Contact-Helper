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

        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        Button btnBackToLogin = findViewById(R.id.btn_back_to_login);

        // When they click Register
        btnCreateAccount.setOnClickListener(v -> createNewAccount());

        // When they click the text to go back to Login
        btnBackToLogin.setOnClickListener(v -> {
            finish(); // This simply closes the register screen, revealing the login screen underneath
        });
    }

    private void createNewAccount() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // 1. Check if fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Try to add user to database
        if (dbHelper.addUser(username, password)) {
            Toast.makeText(this, "Registration Successful! Please login.", Toast.LENGTH_SHORT).show();
            finish(); // Close the register screen and return to login
        } else {
            Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT).show();
        }
    }
}