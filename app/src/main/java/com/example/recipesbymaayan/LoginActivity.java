package com.example.recipesbymaayan;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        String registeredUsername = getIntent().getStringExtra("username");
        String registeredPassword = getIntent().getStringExtra("password");
        EditText usernameInput = findViewById(R.id.etUsername);
        EditText passwordInput = findViewById(R.id.etPassword);
        Button loginButton = findViewById(R.id.btnLogin);
        TextView registerLink = findViewById(R.id.tvRegister);
        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
            finish();
        });
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "יש למלא שם משתמש וסיסמה",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }
            if (registeredUsername != null
                    && registeredPassword != null
                    && username.equals(registeredUsername)
                    && password.equals(registeredPassword)) {

                Toast.makeText(
                        LoginActivity.this,
                        "הכניסה הצליחה",
                        Toast.LENGTH_SHORT
                ).show();

            } else {
                Toast.makeText(
                        LoginActivity.this,
                        "שם המשתמש או הסיסמה שגויים",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}