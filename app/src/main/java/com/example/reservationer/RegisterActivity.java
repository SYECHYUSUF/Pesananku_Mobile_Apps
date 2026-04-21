package com.example.reservationer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etName = findViewById(R.id.et_register_name);
        EditText etEmail = findViewById(R.id.et_register_email);
        EditText etPassword = findViewById(R.id.et_register_password);
        Button btnRegister = findViewById(R.id.btn_register);
        TextView tvLoginBack = findViewById(R.id.tv_login_back);

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        tvLoginBack.setOnClickListener(v -> finish());
    }
}