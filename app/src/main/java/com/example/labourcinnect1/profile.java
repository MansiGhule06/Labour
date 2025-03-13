package com.example.labourcinnect1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class profile extends AppCompatActivity {
    ImageView profileImage;
    TextView fullName, phoneNumber, userType, address;
    Button editProfile, changePassword, languagePreference, logoutButton;
    String receivedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI components
        profileImage = findViewById(R.id.profileImage);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        userType = findViewById(R.id.userType);
        address = findViewById(R.id.address);

        editProfile = findViewById(R.id.editProfile);
        changePassword = findViewById(R.id.changePassword);
        receivedText = getIntent().getStringExtra("usertype");
        if (receivedText != null) {
            userType.setText(receivedText);
        }

        // Load user data (this would usually come from SharedPreferences or a database)
        loadUserData();

        // Edit Profile Click
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Intent intent = new Intent(Profile.this, EditProfileActivity.class);
                //  startActivity(intent);
            }
        });

        // Change Password Click
        changePassword.setOnClickListener(v -> {
            ChangePassword changePasswordFragment = new ChangePassword();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, changePasswordFragment)
                    .addToBackStack(null)
                    .commit();
        });




    }

    private void loadUserData() {
        // Simulated user data (Replace with actual data from database or SharedPreferences)
        fullName.setText("John Doe");

        phoneNumber.setText("+91 9876543210");
        userType.setText("Contractor");
        address.setText("Mumbai, Maharashtra");
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
