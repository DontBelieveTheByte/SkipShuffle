/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;

public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {
        public TextView title;
    }

    private LayoutInflater layoutInflater;
    private Typeface typeface;
    private int layoutResource;
    private int selectedItem;
    private int selectedTextBackgroundColor;
    private int textColor;

    //@TODO colorize using visitor instead of constructor.
    public NavigationDrawerAdapter(Context context, int resource, String[] strings, PreferencesHelper preferencesHelper, Typeface typeface)
    {
        super(context, resource, strings);
        layoutInflater = LayoutInflater.from(context);
        this.typeface = typeface;
        layoutResource = resource;
        selectedTextBackgroundColor = ColorMapper.getListDivider(preferencesHelper.getUIType());
        textColor = context.getResources().getColor(
                ColorMapper.getNavDrawerText(preferencesHelper.getUIType())
        );
    }

    public int getSelectedItem()
    {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem)
    {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
        //Trigger from activity.
        //private NavigationDrawerAdapter mAdapter;
        //...
        //private void selectItem(int position) {
        //        mAdapter.setSelectedItem(position);
        //        ...
        //}
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Get item TextView
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    layoutResource,
                    null)
            ;
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.title = setTitle(
                convertView,
                getItem(position),
                R.id.drawer_item_text
        );

        if (position == selectedItem)
            convertView.setBackgroundColor(selectedTextBackgroundColor);

        return convertView;
    }

    private TextView setTitle(View view, String text, int resourceId)
    {
        TextView tv = (TextView) view.findViewById(resourceId);
        if (null != typeface) {
            tv.setTypeface(typeface);
        }
        tv.setText(text);
        tv.setTextColor(textColor);
        return tv;
    }
}
