package com.example.recipesbymaayan;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        EditText usernameInput = findViewById(R.id.etRegisterUsername);
        EditText passwordInput = findViewById(R.id.etRegisterPassword);
        EditText confirmPasswordInput = findViewById(R.id.etConfirmPassword);
        Button registerButton = findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            String confirmPassword = confirmPasswordInput.getText().toString();

            usernameInput.setError(null);
            passwordInput.setError(null);
            confirmPasswordInput.setError(null);

            if (username.isEmpty()) {
                usernameInput.setError("יש להזין שם משתמש");
                return;
            }

            if (password.length() < 6) {
                passwordInput.setError("הסיסמה חייבת להכיל לפחות 6 תווים");
                return;
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordInput.setError("הסיסמאות אינן זהות");
                return;
            }

            Intent intent = new Intent(
                    RegisterActivity.this,
                    LoginActivity.class
            );

            intent.putExtra("username", username);
            intent.putExtra("password", password);

            startActivity(intent);
            finish();        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}