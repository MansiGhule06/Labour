package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class labourlaws extends AppCompatActivity {
RadioGroup rg;
ImageButton back;
LabourLawDetailsFragment detailsFragment;
String info;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labourlaws);
        rg=findViewById(R.id.rg);
        back=findViewById(R.id.back);
        info=getIntent().getStringExtra("user_type");
        back.setOnClickListener(view -> {
            if(info=="labour")
            {
                Intent intent=new Intent(labourlaws.this,LabourHomepage.class);
            }
            if(info=="contractor")
            {
                Intent intent=new Intent(labourlaws.this,ContractorHomepage.class);
            }

        });
        detailsFragment = new LabourLawDetailsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, detailsFragment);
        transaction.commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String lawDetails = "";

                if (checkedId == R.id.r1) {
                    lawDetails = getString(R.string.the_factories_act_1881_enacted_during_british_rule_this_was_one_of_the_earliest_pieces_of_legislation_addressing_the_working_conditions_in_factories_it_aimed_to_regulate_industrial_workers_hours_of_work_health_and_safety);
                } else if (checkedId == R.id.r2) {
                    lawDetails = getString(R.string.the_trade_union_act_1926_this_legislation_provided_legal_recognition_to_trade_unions_allowing_workers_to_organize_and_bargain_for_better_working_conditions_collectively_it_marked_a_significant_step_towards_protecting_workers_rights);
                } else if (checkedId == R.id.r3) {
                    lawDetails = getString(R.string.this_act_ensured_the_timely_payment_of_wages_to_industrial_workers_providing_a_legal_framework_for_the_calculation_and_disbursement_of_wages_and_preventing_unfair_practices_by_employers);
                } else if (checkedId == R.id.r4) {
                    lawDetails = getString(R.string.the_minimum_wages_act_1948_enacted_to_safeguard_the_interests_of_labourers_this_law_established_the_concept_of_minimum_wages_to_ensure_that_workers_receive_remuneration_sufficient_for_a_basic_standard_of_living);
                } else if (checkedId == R.id.r5) {
                    lawDetails = getString(R.string.criteria_of_labour_laws_in_india_india) +
                            "\n" +
                            getString(R.string.minimum_wage_in_rs_rs_178_per_day) +
                            getString(R.string.standard_working_hours_12_hours_per_day) +
                            getString(R.string.overtime_limit_maximum_1_hour_per_day) +
                            getString(R.string.pay_for_overtime_in_100);
                }

                detailsFragment.updateLawDetails(lawDetails);
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
