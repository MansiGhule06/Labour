package com.example.labourcinnect1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView ivMainLogo;
    Animation fadeInAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean("FirstLaunch", true);

        ivMainLogo = findViewById(R.id.ivMainLogo);
        fadeInAnim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
        ivMainLogo.setAnimation(fadeInAnim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isFirstLaunch) {
                    // First time: Show language selection
                    Intent intent=new Intent(SplashActivity.this,btnlanguagechange.class);
                    startActivity(intent);

                    // Update preference so it doesn't open again
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("FirstLaunch", false);
                    editor.apply();
                } else {
                    // Next time: Directly go to labcon
                    startActivity(new Intent(SplashActivity.this, labcon.class));
                }

                finish();
            }
        }, 3000);
    }
}