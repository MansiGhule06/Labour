package com.example.labourcinnect1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Ensure your XML layout is named activity_main.xml

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        CheckBox showPasswordCheckBox = findViewById(R.id.cbShowPassword);
        Button loginButton = findViewById(R.id.loginButton);
        userType=getIntent().getStringExtra("user_type");
        // Set a listener for the login button
        loginButton.setOnClickListener(v -> validateLogin());
// Set a listener for the show password checkbox
        showPasswordCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            } else {
                passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

            // Move the cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }

    private void validateLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,""+ R.string.please_enter_both_username_and_password, Toast.LENGTH_SHORT).show();
        } else {
            // Here you can add further validation (e.g., checking against a database)
            Toast.makeText(this,""+ R.string.login_successful, Toast.LENGTH_SHORT).show();
            // Proceed with your login logic
        }
        if("labour".equals(userType))
        {
            Intent in=new Intent(LoginActivity.this, LabourHomepage.class);
            in.putExtra("userName", username);
            startActivity(in);
        }
        if("contractor".equals(userType))
        {
            Intent in1=new Intent(LoginActivity.this,ContractorHomepage.class);
            startActivity(in1);
        }

    }
    private void setAppLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en");
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}