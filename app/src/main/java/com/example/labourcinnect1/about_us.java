package com.example.labourcinnect1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class about_us extends AppCompatActivity implements View.OnClickListener{

    ImageButton msg,whatsapp,email,phone;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        msg=findViewById(R.id.msg);
        phone=findViewById(R.id.phone);
        whatsapp=findViewById(R.id.whatsapp);
        email=findViewById(R.id.email);

        msg.setOnClickListener(this);
        phone.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        email.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent=null;
        if (view.getId()==R.id.email)
        {
             intent=new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:labourConnect6781@gmail.com"));
            startActivity(intent);
        } else if (view.getId()==R.id.whatsapp) {
             intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/+918788811656"));
            //startActivity(intent);
        }
        else if (view.getId()==R.id.phone) {
             intent=new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+917038951250"));
            //startActivity(intent);
        }
        else if (view.getId()==R.id.msg) {
             intent=new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sms:+918483852446"));
            intent.putExtra("sms_body","Your message here");
           // startActivity(intent);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
