package com.example.labourcinnect1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class drawer_toolbar extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton buttonDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_toolbar);
        drawerLayout=findViewById(R.id.drawerLayout);
        buttonDrawer=findViewById(R.id.btndrawer);

        buttonDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

    }
}