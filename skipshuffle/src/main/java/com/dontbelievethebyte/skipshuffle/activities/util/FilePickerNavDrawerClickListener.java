package com.dontbelievethebyte.skipshuffle.activities.util;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerDrawerAdapter;

public class FilePickerNavDrawerClickListener implements ListView.OnItemClickListener {

    private Context context;
    private DrawerLayout drawer;

    public FilePickerNavDrawerClickListener(Context context, DrawerLayout drawer)
    {
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
        drawer.closeDrawer(Gravity.START);
        FilePickerDrawerAdapter filePickerDrawerAdapter1 = (FilePickerDrawerAdapter) parent.getAdapter();
        filePickerDrawerAdapter1.setSelectedItem(position);
    }
}
