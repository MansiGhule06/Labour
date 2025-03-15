package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Carpentering extends AppCompatActivity {
    ListView listview;
    ImageButton back;
    Button btn;
    String[] carray={"John","Elsa","quran","Prapti"};
    String[] larray={"John","Elsa","quran","Prapti"};
    custom adapter;
    String info;
    Intent intent1,intent;
    String[] cdetails = {
            "Tanaya is a software engineer with expertise in Android development.",
            "Disha is a data scientist specializing in AI and machine learning.",
            "Mansi is a UI/UX designer with a passion for creative designs.",
            "Prapti is a backend developer focused on Java and databases."
    };
    int[] cimgarr={R.drawable.about_us,R.drawable.admin_logo,R.drawable.carpenter,R.drawable.contractor};
    String[] ldetails = {
            "Tanaya is a software engineer with expertise in Android development.",
            "Disha is a data scientist specializing in AI and machine learning.",
            "Mansi is a UI/UX designer with a passion for creative designs.",
            "Prapti is a backend developer focused on Java and databases."
    };
    int[] limgarr={R.drawable.about_us,R.drawable.admin_logo,R.drawable.carpenter,R.drawable.contractor};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpentering);
        listview=findViewById(R.id.list);
        info=getIntent().getStringExtra("user_type");

        if (info.equals("contractor")) {
            adapter=new custom(this,carray,cimgarr, btn);
        }
        else if(info.equals("labour"))
        {
            adapter=new custom(this,larray,limgarr, btn);
        }
        listview.setAdapter(adapter);

        back=findViewById(R.id.ib);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                intent = new Intent(Carpentering.this, MemberDetailsActivity.class);
                //intent.putExtra("name", array[i]);     // Send Name
                if(info.equals("labour")) {
                    intent.putExtra("image", limgarr[i]);   // Send Image
                    intent.putExtra("details", ldetails[i]); // Send Details
                }
                if (info.equals("contractor")) {
                    intent.putExtra("image", cimgarr[i]);   // Send Image
                    intent.putExtra("details", cdetails[i]); // Send Details
                }
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.equals("contractor")) {
                    intent1=new Intent(Carpentering.this,ContractorHomepage.class);
                }
                else if(info.equals("labour"))
                {
                    intent1=new Intent(Carpentering.this,LabourHomepage.class);
                }
                startActivity(intent1);
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
