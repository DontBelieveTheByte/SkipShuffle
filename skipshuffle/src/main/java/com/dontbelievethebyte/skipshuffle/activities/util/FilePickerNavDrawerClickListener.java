package com.dontbelievethebyte.skipshuffle.activities.util;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class FilePickerNavDrawerClickListener implements ListView.OnItemClickListener {

    public FilePickerNavDrawerClickListener()
    {
        Log.d(BaseActivity.TAG, "Constructed click listener.");
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
//        TextView mediaDirectoryText = (TextView) view.findViewById(R.id.drawer_item_text);
//        mediaDirectoryText.setSelected(true);
        Log.d("POSITION", " : " + position);
    }
}
