package com.example.emergencycontacthelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername,etEmail,etPassword,etConfirm;
    Button btnRegister;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=new DatabaseHelper(this);

        etUsername=findViewById(R.id.etUsername);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etConfirm=findViewById(R.id.etConfirmPassword);

        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> register());
    }

    private void register(){

        String username=etUsername.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String pass=etPassword.getText().toString();
        String confirm=etConfirm.getText().toString();

        if(username.isEmpty()||email.isEmpty()||
                pass.isEmpty()||confirm.isEmpty()){
            Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Invalid email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(pass.length()<6){
            Toast.makeText(this,"Password 6+ chars",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pass.equals(confirm)){
            Toast.makeText(this,"Passwords not match",Toast.LENGTH_SHORT).show();
            return;
        }

        String hash=PasswordUtils.hashPassword(pass);

        if(db.registerUser(username,email,hash)){
            Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }
    }
}