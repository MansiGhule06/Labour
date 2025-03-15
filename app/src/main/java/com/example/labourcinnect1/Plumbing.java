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

public class Plumbing extends AppCompatActivity {

    ListView listview;
    Button btn;
    custom adapter;
    ImageButton back;

    String[] larray={"plumb","qwer","cdvv","Prasdapti"};
    String[] ldetails;
    int[] limgarr={R.drawable.about_us,R.drawable.admin_logo,R.drawable.carpenter,R.drawable.contractor};
    String[] carray={"plumb","qwer","cdvv","Prasdapti"};
    String[] cdetails;
    int[] cimgarr={R.drawable.about_us,R.drawable.admin_logo,R.drawable.carpenter,R.drawable.contractor};
    String info;
    Intent intent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumbing);
        listview=findViewById(R.id.list);
        info=getIntent().getStringExtra("user_type");
        ldetails = new String[]{
                getString(R.string.contractor_is_a_software_engineer_with_expertise_in_android_development),
                getString(R.string.disha_is_a_data_scientist_specializing_in_ai_and_machine_learning),
                getString(R.string.mansi_is_a_ui_ux_designer_with_a_passion_for_creative_designs),
                getString(R.string.prapti_is_a_backend_developer_focused_on_java_and_databases)
        };
        cdetails = new String[]{
                getString(R.string.contractor_is_a_software_engineer_with_expertise_in_android_development),
                getString(R.string.disha_is_a_data_scientist_specializing_in_ai_and_machine_learning),
                getString(R.string.mansi_is_a_ui_ux_designer_with_a_passion_for_creative_designs),
                getString(R.string.prapti_is_a_backend_developer_focused_on_java_and_databases)
        };
        if (info.equals("contractor")) {
            adapter=new custom(this,carray,cimgarr, btn);
        }
        else if(info.equals("labour"))
        {
            adapter=new custom(this,larray,limgarr, btn);
        }
        listview.setAdapter(adapter);
        back=findViewById(R.id.ib);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.equals("contractor")) {
                    intent=new Intent(Plumbing.this,ContractorHomepage.class);
                }
                else if(info.equals("labour"))
                {
                    intent=new Intent(Plumbing.this,LabourHomepage.class);
                };
                startActivity(intent);
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(Plumbing.this, MemberDetailsActivity.class);
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