package com.example.hashizumeakitoshi.navigationdrawersampleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hnk_1031 on 16/06/25.
 */
public class ListAdapter extends ArrayAdapter<CustomContent>{

    static class ViewHolder{
        TextView title;
        TextView birthday;
        TextView age;

    }

    public ListAdapter(Context context, int layoutId, ArrayList<CustomContent> lists) {
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
            viewHolder.title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.birthday=(TextView)convertView.findViewById(R.id.Birthday1);
            viewHolder.age=(TextView)convertView.findViewById(R.id.Age1);
            convertView.setTag(viewHolder);

        }

        final CustomContent customContent = getItem(position);


        viewHolder.title.setText(customContent.getTitle());
        viewHolder.birthday.setText(customContent.getBirthday());
        viewHolder.age.setText(customContent.getAge());



        return convertView;
    }

}
