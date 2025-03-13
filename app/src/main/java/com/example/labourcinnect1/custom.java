package com.example.labourcinnect1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class custom extends BaseAdapter {
    int[] arr;
    String[] txtarr;
    Context context;
    LayoutInflater inflater;
    public custom(Context applicationContext, String[] array, int[] imgarr) {
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
        View v=inflater.inflate(R.layout.singletextimage,null);
        ImageView iv=v.findViewById(R.id.image);
        TextView txt=v.findViewById(R.id.text);
        iv.setImageResource(arr[i]);
        txt.setText(txtarr[i]);
        return v;
    }

}
