package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;

public class ContractorHomepage extends AppCompatActivity implements View.OnClickListener{

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ImageButton btndrawer,construct,carpenter,painter,electric,load_uload,plumber;

    NavigationView navigationView;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private TextView userNameTextView;
    private TextView userEmailTextView;
    TextView locationTextView;
    ImageSlider imageSlider;
    Intent intent;
    @SuppressLint({"MissingInflatedId", "WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLocale();

        setContentView(R.layout.activity_contractor_homepage);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.user_name);
        userEmailTextView = headerView.findViewById(R.id.user_email);
        btndrawer=findViewById(R.id.btndrawer);
        toolbar = findViewById(R.id.toolbar);
        construct=findViewById(R.id.construct1);
        carpenter=findViewById(R.id.carpentering);
        painter=findViewById(R.id.painting);
        electric=findViewById(R.id.electric);
        load_uload=findViewById(R.id.load_unload);
        plumber=findViewById(R.id.plumber);

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



        btndrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        imageSlider = findViewById(R.id.image_slider);

        ArrayList<com.denzcoskun.imageslider.models.SlideModel> imageList = new ArrayList<>();

        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_constr_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_painting_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_plumbing_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_carpenter_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.loadig_and_unloadig, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_electrician_contractor, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Logout) {
                    Intent intent = new Intent(ContractorHomepage.this, ContractorHomepage.class);
                    startActivity(intent);
                }

                if (id == R.id.feedback) {
                    Intent intent = new Intent(ContractorHomepage.this, feedback.class);
                    startActivity(intent);

                }
                if (id == R.id.profile) {
                    Intent intent = new Intent(ContractorHomepage.this, profile.class);
                    intent.putExtra("usertype","Contractor");
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START); // Close drawer after selection
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.navigation_drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.aboutus) {
            Intent intent=new Intent(ContractorHomepage.this,about_us.class);
            startActivity(intent);
        } else if (itemId == R.id.marathi) {
            saveLanguageSelection("mr");
        } else if (itemId == R.id.english) {
            saveLanguageSelection("en");
        }

        else if(itemId==R.id.labour_laws)
        {
            Intent intent=new Intent(ContractorHomepage.this,labourlaws.class);
            intent.putExtra("user_type","contractor");

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.construct1)
        {
            intent=new Intent(this,Constructionlabour.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
        else if (view.getId()==R.id.carpentering) {
            intent=new Intent(this,Carpentering.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
        else if (view.getId()==R.id.painting) {
            intent=new Intent(this,Painter.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
        else if (view.getId()==R.id.electric) {
            intent=new Intent(this,Electrician.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
        else if (view.getId()==R.id.load_unload) {
            intent=new Intent(this,Loading_Unloading.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
        else if (view.getId()==R.id.plumber) {
            intent=new Intent(this,Plumbing.class);
            intent.putExtra("user_type","contractor");
            startActivity(intent);
        }
    }
    private void saveLanguageSelection(String lc) {
        Log.d("LanguageChange", "Saving language: " + lc); // Add this

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedLanguage", lc);
        editor.apply();

        setAppLocale(lc);

        Log.d("LanguageChange", "Restarting Activity"); // Add this

        Intent intent = new Intent(getApplicationContext(), ContractorHomepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void setAppLocale(String languageCode) {
        Log.d("LanguageChange", "Applying language: " + languageCode); // Add this

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    private void setAppLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en");

        Log.d("LanguageChange", "Applying language: " + languageCode);

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Only recreate if already running (to avoid infinite loops)
        if (!getClass().getSimpleName().equals("ContractorHomepage")) {
            recreate();
        }
    }



}