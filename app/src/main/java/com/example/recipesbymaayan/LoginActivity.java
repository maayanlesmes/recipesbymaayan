package com.example.recipesbymaayan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember_me";

    private SharedPreferences prefs;
    private EditText usernameInput;
    private EditText passwordInput;
    private CheckBox cbRememberMe;

    private String registeredUsername;
    private String registeredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        Button loginButton = findViewById(R.id.btnLogin);
        TextView registerLink = findViewById(R.id.tvRegister);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        registeredUsername = getIntent().getStringExtra("username");
        registeredPassword = getIntent().getStringExtra("password");

        // ממלאים את השדות לפני שמחברים מאזין לתיבה.
        loadCredentials();

        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "יש למלא שם משתמש וסיסמה",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (registeredUsername != null
                    && registeredPassword != null
                    && username.equals(registeredUsername)
                    && password.equals(registeredPassword)) {

                if (cbRememberMe.isChecked()) {
                    saveCredentials(username, password);
                }

                Toast.makeText(this, "הכניסה הצליחה",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "שם המשתמש או הסיסמה שגויים",
                        Toast.LENGTH_SHORT).show();
            }
        });

        cbRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked && prefs.getBoolean(KEY_REMEMBER, false)) {
                showForgetSnackbar();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });
    }

    // שמירת הפרטים כשהכניסה הצליחה והתיבה מסומנת.
    private void saveCredentials(String username, String password) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER, true);
        editor.apply();
    }

    // קריאת הפרטים השמורים כשהמסך נפתח.
    private void loadCredentials() {
        if (prefs.getBoolean(KEY_REMEMBER, false)) {
            String savedUsername = prefs.getString(KEY_USERNAME, "");
            String savedPassword = prefs.getString(KEY_PASSWORD, "");

            usernameInput.setText(savedUsername);
            passwordInput.setText(savedPassword);
            cbRememberMe.setChecked(true);

            // אם לא הגיעו פרטים מהרשמה, משווים לפרטים השמורים.
            if (registeredUsername == null || registeredPassword == null) {
                registeredUsername = savedUsername;
                registeredPassword = savedPassword;
            }
        }
    }

    // בקשת אישור לפני מחיקת הפרטים השמורים.
    private void showForgetSnackbar() {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.main),
                "לשכוח את פרטי ההתחברות השמורים?",
                Snackbar.LENGTH_LONG);

        snackbar.setAction("כן, לשכוח", v -> clearCredentials());

        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar sb, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION
                        && prefs.getBoolean(KEY_REMEMBER, false)) {
                    cbRememberMe.setChecked(true);
                }
            }
        });

        snackbar.show();
    }

    // מחיקת הפרטים מקובץ ההעדפות.
    private void clearCredentials() {
        prefs.edit().clear().apply();
    }
}