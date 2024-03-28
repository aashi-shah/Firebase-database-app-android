package com.example.firebasedatabaseapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MybaseAdapter extends BaseAdapter {
    Context context;
    ArrayList<DriverModel> langModelArrayList;

    public MybaseAdapter(Context context, ArrayList<DriverModel> langModelArrayList) {
        this.context = context;
        this.langModelArrayList = langModelArrayList;
    }


    @Override
    public int getCount() {
        return langModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return langModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contextview = layoutInflater.inflate(R.layout.raw_custom_list,null);

        TextView name = contextview.findViewById(R.id.names);

        DriverModel langModel = langModelArrayList.get(i);
        name.setText(langModel.getFirstname()+" "+langModel.getLastname());

        contextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = langModelArrayList.get(i).getId();
                String fn = langModelArrayList.get(i).getFirstname();
                String ln = langModelArrayList.get(i).getLastname();

                Intent i = new Intent(context,UpdateActivity.class);
                i.putExtra("KEY_ID",id);
                i.putExtra("KEY_FN",fn);
                i.putExtra("KEY_LN",ln);
                context.startActivity(i);
            }
        });
        return contextview;
    }
}
