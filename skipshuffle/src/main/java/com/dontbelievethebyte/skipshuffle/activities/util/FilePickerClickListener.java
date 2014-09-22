package com.dontbelievethebyte.skipshuffle.activities.util;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

import java.io.File;

public class FilePickerClickListener implements ListView.OnItemClickListener {

    private FilePickerActivity filePickerActivity;
    private DrawerLayout drawer;

    public FilePickerClickListener(FilePickerActivity filePickerActivity, DrawerLayout drawer)
    {
        this.filePickerActivity = filePickerActivity;
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id)
    {
        File newDirectory = (File) adapterView.getItemAtPosition(position);

        if (null == newDirectory) {
            Toast.makeText(
                    filePickerActivity,
                    filePickerActivity.getString(R.string.no_access),
                    Toast.LENGTH_SHORT
            ).show();
        } else if (newDirectory.isDirectory()) {
            filePickerActivity.setCurrentDirectory(newDirectory);
            filePickerActivity.refreshFilesList();
        } else {
            Toast.makeText(
                    filePickerActivity,
                    filePickerActivity.getString(
                            R.string.not_a_directory,
                            filePickerActivity.getCurrentDirectory().getName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
