package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.app.LocaleManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
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

public class LabourHomepage extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Intent intent;
TextView usertype;
    ImageButton buttonDrawer,construct,plumbing,electricity,carpenter,load_unload,painting;
    ImageButton back;
    NavigationView navigationView;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private TextView userNameTextView;
    private TextView userEmailTextView;
    TextView lb;
    TextView locationTextView;
    String userName;
    @SuppressLint({"MissingInflatedId", "RestrictedApi", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour_homepage);
        drawerLayout = findViewById(R.id.drawer_layout);
        buttonDrawer=findViewById(R.id.btndrawer);
        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.user_name);
        userEmailTextView = headerView.findViewById(R.id.user_email);
        construct=findViewById(R.id.construct);
        plumbing=findViewById(R.id.plumbing);
        electricity=findViewById(R.id.electrician);
        carpenter=findViewById(R.id.carpentering);
        load_unload=findViewById(R.id.load_unload);
        painting=findViewById(R.id.painting);

        lb=findViewById(R.id.lb);
        toolbar = findViewById(R.id.toolbar);

        construct.setOnClickListener(this);
        plumbing.setOnClickListener(this);
        electricity.setOnClickListener(this);
        carpenter.setOnClickListener(this);
        load_unload.setOnClickListener(this);
        painting.setOnClickListener(this);

         userName = getUserName(); // Fetch username dynamically

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle("My Toolbar");
        toolbar.setSubtitle("Sub title");


        buttonDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        ArrayList<com.denzcoskun.imageslider.models.SlideModel> imageList = new ArrayList<>();

        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_constr_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_painting_labour, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_plumbing_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.imageslider_carpenter_contractor, ScaleTypes.CENTER_CROP));
        imageList.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.loadig_and_unloadig, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.imageslider_electrician_contractor, ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = findViewById(R.id.image_slider1);
        imageSlider.setImageList(imageList);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Logout) {
                    Intent intent = new Intent(LabourHomepage.this, MainActivity.class);
                    startActivity(intent);

                }

                if (id == R.id.feedback) {
                    Intent intent = new Intent(LabourHomepage.this, feedback.class);
                    startActivity(intent);

                }
                if (id == R.id.profile) {
                    Intent intent = new Intent(LabourHomepage.this, profile.class);
                    intent.putExtra("usertype","Labour");
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
           // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LabourHomepage.this,about_us.class);
            startActivity(intent);
        } else if (itemId == R.id.marathi) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                LocaleManager localeManager = getSystemService(LocaleManager.class);
                localeManager.setApplicationLocales(LocaleList.forLanguageTags("mr")); // Change to Marathi

            }

        }
        else if(itemId==R.id.labour_laws)
        {
            Intent intent=new Intent(LabourHomepage.this,labourlaws.class);
            intent.putExtra("user_type","labour");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private String getUserName() {
        Intent intent = getIntent();
        return intent.getStringExtra("userName"); // Make sure userName is sent from the previous activity
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.construct)
        {
             intent=new Intent(this,Constructionlabour.class);
            intent.putExtra("user_type","labour");
            startActivity(intent);
        }
        else if (view.getId()==R.id.plumbing) {
             intent=new Intent(this,Plumbing.class);
            intent.putExtra("user_type","labour");

            startActivity(intent);
        }
        else if (view.getId()==R.id.electrician) {
             intent=new Intent(this,Electrician.class);
            intent.putExtra("user_type","labour");
            startActivity(intent);
        }
        else if (view.getId()==R.id.carpentering) {
             intent=new Intent(this,Carpentering.class);
            intent.putExtra("user_type","labour");

            startActivity(intent);
        }
        else if (view.getId()==R.id.load_unload) {
             intent=new Intent(this,Loading_Unloading.class);
            intent.putExtra("user_type","labour");

            startActivity(intent);
        }
        else if (view.getId()==R.id.painting) {
             intent=new Intent(this,Painter.class);
            intent.putExtra("user_type","labour");

            startActivity(intent);
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