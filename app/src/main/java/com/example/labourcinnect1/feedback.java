package com.example.labourcinnect1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class feedback extends AppCompatActivity {

    private TextInputEditText userNameInput, feedbackInput;
    private RadioGroup recommendationGroup;
    private MaterialCheckBox checkBoxQuality, checkBoxResponse, checkBoxInterface, checkBoxCommunication, checkBoxPricing;
    private Button submitButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        // Initialize UI components
        userNameInput = findViewById(R.id.user_name_input);
        feedbackInput = findViewById(R.id.feedback_input);
        recommendationGroup = findViewById(R.id.recommendation_group);
        checkBoxQuality = findViewById(R.id.checkbox_quality);
        checkBoxResponse = findViewById(R.id.checkbox_response);
        checkBoxInterface = findViewById(R.id.checkbox_interface);
        checkBoxCommunication = findViewById(R.id.checkbox_communication);
        checkBoxPricing = findViewById(R.id.checkbox_pricing);
        submitButton = findViewById(R.id.button_submit);
        progressBar = findViewById(R.id.progress_bar);

        // Set click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        String userName = userNameInput.getText().toString().trim();
        String feedback = feedbackInput.getText().toString().trim();

        // Get selected recommendation option
        int selectedId = recommendationGroup.getCheckedRadioButtonId();
        String recommendation = "";
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            recommendation = selectedRadioButton.getText().toString();
        }

        // Get selected improvement checkboxes
        StringBuilder improvements = new StringBuilder();
        if (checkBoxQuality.isChecked()) improvements.append("Service quality, ");
        if (checkBoxResponse.isChecked()) improvements.append("Response time, ");
        if (checkBoxInterface.isChecked()) improvements.append("User interface, ");
        if (checkBoxCommunication.isChecked()) improvements.append("Communication, ");
        if (checkBoxPricing.isChecked()) improvements.append("Pricing");

        // Simulate feedback submission
        progressBar.setVisibility(View.VISIBLE);
        submitButton.setEnabled(false);

        // Simulated delay (replace with actual API call)
        submitButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                submitButton.setEnabled(true);
                Toast.makeText(feedback.this, ""+R.string.feedback_submitted_successfully, Toast.LENGTH_SHORT).show();
            }
        }, 2000);
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