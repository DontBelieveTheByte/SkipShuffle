package com.dontbelievethebyte.skipshuffle.activities.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerListAdapter;

import java.io.File;

public class FilePickerClickListener implements ListView.OnItemClickListener {

    private FilePickerActivity filePickerActivity;

    public FilePickerClickListener(FilePickerActivity filePickerActivity)
    {
        this.filePickerActivity = filePickerActivity;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id)
    {
        File newDirectory = (File) adapterView.getItemAtPosition(position);
        FilePickerListAdapter filePickerListAdapter = (FilePickerListAdapter) adapterView.getAdapter();
        if (null == newDirectory) {
            Toast.makeText(
                    filePickerActivity,
                    filePickerActivity.getString(R.string.no_access),
                    Toast.LENGTH_SHORT
            ).show();
        } else if (newDirectory.isDirectory()) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.file_picker_checkbox);
            if (checkBox.isChecked()) {
                Toast.makeText(
                        filePickerActivity,
                        R.string.subdirectory_included,
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                filePickerListAdapter.setCurrentDirectory(newDirectory);
                filePickerListAdapter.refreshFilesList();
            }
        } else {
            Toast.makeText(
                    filePickerActivity,
                    filePickerActivity.getString(
                            R.string.not_a_directory,
                            newDirectory.getName()
                    ),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
