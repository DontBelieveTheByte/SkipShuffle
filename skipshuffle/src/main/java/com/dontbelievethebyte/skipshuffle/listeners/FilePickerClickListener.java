package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.adapters.FilePickerListAdapter;

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

        if (null == newDirectory)
            filePickerActivity.getToastHelper().showLongToast(
                    filePickerActivity.getString(R.string.no_access)
            );
        else if (newDirectory.isDirectory())
            filePickerActivity.setCurrentListedDirectory(newDirectory);
        else
            filePickerActivity.getToastHelper().showShortToast(
                    filePickerActivity.getString(
                            R.string.not_a_directory,
                            newDirectory.getName()
                    )
            );
    }
}
