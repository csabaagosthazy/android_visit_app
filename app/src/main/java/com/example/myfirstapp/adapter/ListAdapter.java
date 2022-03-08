package com.example.myfirstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private int digit[] = new int[] {};

    public ListAdapter (Context context, int digit[]) {

        this.context = context;
        this.digit = digit;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return digit.length;
    }

    @Override
    public Object getItem(int position) {
        return digit[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null, true);

            holder.visit = (TextView) convertView.findViewById(R.id.tv);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.visit.setText("Visit "+ digit[position]);

        return convertView;
    }

    private class ViewHolder {

        protected TextView visit;

    }

}