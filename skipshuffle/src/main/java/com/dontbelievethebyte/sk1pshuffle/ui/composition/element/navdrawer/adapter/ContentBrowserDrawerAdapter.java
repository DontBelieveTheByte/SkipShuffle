/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;

public class ContentBrowserDrawerAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {
        public TextView title;
    }

    private LayoutInflater layoutInflater;
    private Typeface typeface;
    private int layoutResource;
    private int selectedItem;
    private int selectedTextBackgroundColor;
    private int textColor;

    public ContentBrowserDrawerAdapter(Context context, int resource, String[] strings, Theme theme)
    {
        super(context, resource, strings);
        layoutInflater = LayoutInflater.from(context);
        this.typeface = theme.getCustomTypeface().getTypeFace();
        layoutResource = resource;
        selectedTextBackgroundColor = theme.getColors().background;
        textColor = context.getResources().getColor(theme.getColors().navDrawerText);
    }

    public int getSelectedItem()
    {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem)
    {
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
        //Trigger from baseActivity.
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
