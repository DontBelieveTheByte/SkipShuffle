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
    private int mSelectedItem;
    private int selectedTextBackgroundColor;
    private Typeface typeface;

    public NavigationDrawerAdapter(Context context, int resource, String[] objects, PreferencesHelper preferencesHelper, Typeface typeface) {
        super(context, resource, objects);
        layoutInflater = LayoutInflater.from(context);
        this.typeface = typeface;


        selectedTextBackgroundColor = ColorMapper.getListDividerColor(preferencesHelper.getUIType());
    }
    public int getSelectedItem() {
        return mSelectedItem;
    }
    public void setSelectedItem(int selectedItem) {
        mSelectedItem = selectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get item TextView
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    R.layout.common_playlist_item,
                    null)
            ;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title = setTitle(
                convertView,
                R.id.track_title,
                position == mSelectedItem
        );
        return convertView;
    }

    private TextView setTitle(View view, int resourceId, boolean selected)
    {
        TextView tv = (TextView) view.findViewById(resourceId);

        if (selected) {
            tv.setBackgroundColor(selectedTextBackgroundColor);
        }

        if (null != typeface) {
            tv.setTypeface(typeface);
        }

//        tv.setText(title);
        return tv;
    }
}

//Trigger from activity.
//private NavigationDrawerAdapter mAdapter;
//...
//private void selectItem(int position) {
//        mAdapter.setSelectedItem(position);
//        ...
//}