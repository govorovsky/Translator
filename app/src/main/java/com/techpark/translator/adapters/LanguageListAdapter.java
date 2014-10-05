package com.techpark.translator.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techpark.translator.R;
import com.techpark.translator.entities.LanguageList;

import java.util.List;

/**
 * Created by andrew on 05.10.14.
 */
public class LanguageListAdapter extends ArrayAdapter<LanguageList.LanguageListEntry> {
    LayoutInflater layoutInflater;
    int resId;

    public LanguageListAdapter(Activity context, int resId, List<LanguageList.LanguageListEntry> objects) {
        super(context, resId, objects);
        this.resId = resId;
        layoutInflater = context.getLayoutInflater();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       return  getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.language_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(getItem(position).getName());
        return convertView;
    }

    private class ViewHolder {

        public TextView text;
    }
}

