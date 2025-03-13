package com.example.labourcinnect1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MemberDetailsActivity extends AppCompatActivity {
    ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        // Get Views
        ImageView memberImageView = findViewById(R.id.ivView);
        TextView detailsTextView = findViewById(R.id.tvView);
        img=findViewById(R.id.ib);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MemberDetailsActivity.this,Constructionlabour.class);
                startActivity(intent);
            }
        });
        // Receive Data from Intent
        int imageResId = getIntent().getIntExtra("image", R.drawable.ic_launcher_foreground);
        String details = getIntent().getStringExtra("details");

        // Set Data to Views
        memberImageView.setImageResource(imageResId);
        detailsTextView.setText(details);
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
