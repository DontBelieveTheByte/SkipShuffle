package com.dontbelievethebyte.skipshuffle.activities.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;

public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {
        public TextView title;
    }

    private LayoutInflater layoutInflater;
    private Typeface typeface;
    private int layoutRessource;
    private int selectedItem;
    private int selectedTextBackgroundColor;
    private int uiType;

    public NavigationDrawerAdapter(Context context, int resource, String[] strings, PreferencesHelper preferencesHelper, Typeface typeface)
    {
        super(context, resource, strings);
        layoutInflater = LayoutInflater.from(context);
        this.typeface = typeface;
        layoutRessource = resource;
        this.uiType = preferencesHelper.getUIType();
        selectedTextBackgroundColor = ColorMapper.getListDividerColor(preferencesHelper.getUIType());
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
                    layoutRessource,
                    null)
            ;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title = setTitle(
                convertView,
                getItem(position),
                R.id.drawer_item_text
        );
        if (position == selectedItem) {
            convertView.setBackgroundColor(selectedTextBackgroundColor);
        }
        return convertView;
    }

    private TextView setTitle(View view, String text, int resourceId)
    {
        TextView tv = (TextView) view.findViewById(resourceId);
        if (null != typeface) {
            tv.setTypeface(typeface);
        }
        tv.setText(text);
        tv.setTextColor(ColorMapper.getSonglabelColor(uiType));
        return tv;
    }
}
