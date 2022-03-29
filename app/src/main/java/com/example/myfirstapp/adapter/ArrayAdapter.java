package com.example.myfirstapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.database.entity.VisitEntity;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapter {

    private Context context;
    private int layoutId;
    private String[] items;

    public ArrayAdapter(Context context, int layoutId, String[] items) {

        this.context = context;
        this.layoutId = layoutId;
        this.items = items;
    }

    public android.widget.ArrayAdapter<String> getAdapter() {
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<String>(context, layoutId, items){

            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    // Create the Layout
                    view = inflater.inflate(R.layout.listview_item_noedit, parent, false);
                } else {
                    view = convertView;
                }
                //Add Text to the layout
                TextView text = view.findViewById(R.id.tv_no_edit);
                text.setText(items[position]);
                return view;
            }
        };

        return adapter;
    };


}