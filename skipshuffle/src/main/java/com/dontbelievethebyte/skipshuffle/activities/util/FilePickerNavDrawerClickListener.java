package com.dontbelievethebyte.skipshuffle.activities.util;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerListAdapter;

import java.io.File;

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
        FilePickerListAdapter filePickerListAdapter = (FilePickerListAdapter) parent.getAdapter();
        drawer.closeDrawer(Gravity.START);
        File item = filePickerListAdapter.getItem(position);
        Toast.makeText(
                context,
                R.string.media_scan_sel_target_directories,
                Toast.LENGTH_LONG
        ).show();
        Log.d("POSITION", " : " + position);
    }
}
