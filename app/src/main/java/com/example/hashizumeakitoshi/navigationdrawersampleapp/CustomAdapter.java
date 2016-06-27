package com.example.hashizumeakitoshi.navigationdrawersampleapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kokushiseiya on 16/04/18.
 */
public class CustomAdapter extends ArrayAdapter<ListContent> {

    static class ViewHolder {
        TextView text;
        ImageView image;
    }

    public CustomAdapter(Context context, int layoutId, ArrayList<ListContent> lists) {
        super(context, layoutId, lists);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }

        final ListContent listContent = getItem(position);


        viewHolder.text.setText(listContent.getText());
        viewHolder.image.setImageResource(listContent.getResId());


        return convertView;
    }
}
