package com.example.labourcinnect1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class btnlanguagechange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btnlanguagechange);

        // Step 1: Check if language is already selected
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isLanguageSelected = sharedPreferences.getBoolean("LanguageSelected", false);

        if (!isLanguageSelected) {
            // Show language selection dialog
            showLanguageDialog();
        } else {
            // Apply saved language and move to Registration
            String languageCode = sharedPreferences.getString("SelectedLanguage", "en");
            setAppLocale(languageCode);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    // Step 2: Show AlertDialog for language selection
    private void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");

        String[] languages = {"English", "मराठी"};
        builder.setItems(languages, (dialog, which) -> {
            String selectedLanguage = (which == 0) ? "en" : "mr";
            saveLanguageSelection(selectedLanguage);
        });

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_dialog_bg));
        dialog.show();
    }

    // Step 3: Save selected language in SharedPreferences
    private void saveLanguageSelection(String languageCode) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedLanguage", languageCode);
        editor.putBoolean("LanguageSelected", true);
        editor.apply();

        // Apply language and restart activity
        setAppLocale(languageCode);
        Intent intent = new Intent(getApplicationContext(), labcon.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Step 4: Apply the selected language
    private void setAppLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
