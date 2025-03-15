package com.example.labourcinnect1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class custom extends BaseAdapter {
    int[] arr;
    String[] txtarr;
    Context context;
    LayoutInflater inflater;
    public custom(Context applicationContext, String[] array, int[] imgarr, Button btn) {
        this.context=applicationContext;
        this.arr=imgarr;
        this.txtarr=array;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return txtarr.length;
    }

    @Override
    public Object getItem(int i) {
        return txtarr[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=inflater.inflate(R.layout.user_item_show,null);
        ImageView iv=v.findViewById(R.id.userImage);
        TextView txt=v.findViewById(R.id.tvUsername);
        TextView txt1=v.findViewById(R.id.tvMobile);
        TextView txt2=v.findViewById(R.id.tvAddress);
        TextView txt3=v.findViewById(R.id.tvAge);
        Button btnCall=v.findViewById(R.id.btnCall);


        iv.setImageResource(arr[i]);
        txt.setText(txtarr[i]);
        txt1.setText(txtarr[i]);
        txt2.setText(txtarr[i]);
        txt3.setText(txtarr[i]);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = txt1.getText().toString();

                // Check if CALL_PHONE permission is granted
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Directly call the number
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));
                    context.startActivity(callIntent);
                } else {
                    // Request permission if not granted
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                }

            }
        });

        return v;
    }

}
