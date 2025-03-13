package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ContractorHomepage extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ImageButton btndrawer, construct, carpenter, painter, electric, load_uload, plumber;
    NavigationView navigationView;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private TextView userNameTextView, userEmailTextView, locationTextView;
    ImageSlider imageSlider;
    Intent intent;

    // Firebase database reference
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @SuppressLint({"MissingInflatedId", "WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLocale(getSavedLanguage());
        setContentView(R.layout.activity_contractor_homepage);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Contractors");
        storageReference = FirebaseStorage.getInstance().getReference("ContractorImages");



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.user_name);
        userEmailTextView = headerView.findViewById(R.id.user_email);
        btndrawer = findViewById(R.id.btndrawer1);
        toolbar = findViewById(R.id.toolbar);
        construct = findViewById(R.id.construct);
        carpenter = findViewById(R.id.carpenter);
        painter = findViewById(R.id.painter);
        electric = findViewById(R.id.electric);
        load_uload = findViewById(R.id.load_unload);
        plumber = findViewById(R.id.plumber);

        // Button click listeners
        plumber.setOnClickListener(this);
        load_uload.setOnClickListener(this);
        electric.setOnClickListener(this);
        painter.setOnClickListener(this);
        carpenter.setOnClickListener(this);
        construct.setOnClickListener(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("My Toolbar");
        toolbar.setSubtitle("Sub title");

        btndrawer.setOnClickListener(view -> drawerLayout.open());

        // Image Slider
        imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.imageslider_constr_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_painting_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_plumbing_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_carpenter_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.loadig_and_unloadig, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_electrician_contractor, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.Logout) {
                startActivity(new Intent(ContractorHomepage.this, MainActivity.class));
            } else if (id == R.id.feedback) {
                startActivity(new Intent(ContractorHomepage.this, feedback.class));
            } else if (id == R.id.profile) {
                Intent intent = new Intent(ContractorHomepage.this, profile.class);
                intent.putExtra("usertype", "Contractor");
                startActivity(intent);
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Save contractor details in Firebase
        saveContractorData("John Doe", "123 Street, NY", "New York", "NYC",
                "9876543210", "30", "Electrician", "password123", "40.7128, -74.0060");
    }

    private void saveContractorData(String name, String address, String state, String city,
                                    String mobile, String age, String typeOfWork,
                                    String password, String location) {
        String id = databaseReference.push().getKey();

        HashMap<String, Object> contractorData = new HashMap<>();
        contractorData.put("id", id);
        contractorData.put("name", name);
        contractorData.put("address", address);
        contractorData.put("state", state);
        contractorData.put("city", city);
        contractorData.put("mobile", mobile);
        contractorData.put("age", age);
        contractorData.put("typeOfWork", typeOfWork);
        contractorData.put("password", password);
        contractorData.put("location", location);

        if (id != null) {
            databaseReference.child(id).setValue(contractorData)
                    .addOnSuccessListener(unused -> Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void setAppLocale(String languageCode) {
        Log.d("LanguageChange", "Applying language: " + languageCode);

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private String getSavedLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("SelectedLanguage", "en"); // Default: English
    }



    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Selected category: " + view.getId(), Toast.LENGTH_SHORT).show();
    }
}
