package com.example.visitapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import com.example.visitapp.R;

public class SpinnerAdapter<T> extends ArrayAdapter<T> {

    private int mResource;
    private List<T> mData;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> data) {
        super(context, resource, data);
        mResource = resource;
        mData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getCustomView(position, convertView, parent);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        SpinnerAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(mResource, parent, false);

            viewHolder = new SpinnerAdapter.ViewHolder();
            viewHolder.itemView = convertView.findViewById(R.id.tvPersonView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SpinnerAdapter.ViewHolder) convertView.getTag();
        }
        T item = getItem(position);
        if (item != null) {
            viewHolder.itemView.setText(item.toString());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView itemView;
    }

    public void updateData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
