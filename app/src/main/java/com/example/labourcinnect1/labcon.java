package com.example.labourcinnect1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class labcon extends AppCompatActivity {

    ImageButton leftButton, rightButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labcon);

        // Get the ImageButtons by their IDs
        leftButton = findViewById(R.id.leftButton);  // For Contractor Registration
        rightButton = findViewById(R.id.rightButton);  // For Labour Registration


        // Set click listener for the left button (Labour Registration)
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open MainActivity for Labour Registration

                Toast.makeText(labcon.this,"For Labour Registration",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(labcon.this, RegistrationActivity.class); // Open RegistrationActivity
                startActivity(intent1);
            }
        });


        // Set click listener for the right button (Contractor Registration)
       leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(labcon.this,"For Contractor Registration",Toast.LENGTH_SHORT).show();
                // Open MainActivity for Contractor Registration
               Intent intent2 = new Intent(labcon.this, MainActivity.class); // Open MainActivity
                 startActivity(intent2);
            }
        });




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
