package com.dontbelievethebyte.skipshuffle.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePickerListAdapter extends ArrayAdapter<File>
{
    private List<File> mObjects;
    private ArrayList<File> checkedObjects = new ArrayList<File>();
    private PreferencesHelper preferencesHelper;

    public FilePickerListAdapter(Context context, List<File> objects, PreferencesHelper preferencesHelper) {
        super(
                context,
                R.layout.common_file_picker_list_item,
                android.R.id.text1, objects);
        mObjects = objects;
        this.preferencesHelper = preferencesHelper;
    }

    public void clearBoxes()
    {
        checkedObjects = new ArrayList<File>();
    }

    public String[] getFiles()
    {
        String[] mediaDirectoriesToScan = new String[checkedObjects.size()];
        int i = 0;
        //Save to a class instance array in case the activity needs to restart.
        for (File directory : checkedObjects){
            mediaDirectoriesToScan[i] = directory.getAbsolutePath();
            i++;
        }
        return mediaDirectoriesToScan;
    }

    public void toggleCheckBox(File file)
    {
        if (checkedObjects.contains(file)) {
            checkedObjects.remove(file);
        } else {
            checkedObjects.add(file);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row;
        CheckBox checkBox;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(
                    R.layout.common_file_picker_list_item,
                    parent,
                    false
            );
        } else {
            row = convertView;
        }

        final File object = mObjects.get(position);

        ImageView imageView = (ImageView)row.findViewById(R.id.file_picker_image);
        TextView textView = (TextView)row.findViewById(R.id.file_picker_text);
        checkBox = (CheckBox)row.findViewById(R.id.file_picker_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ){
                    if (!checkedObjects.contains(object))
                        checkedObjects.add(object);
                }
                else {
                    checkedObjects.remove(object);
                }

            }
        });

        if (object.isFile()) {
            checkBox.setVisibility(View.GONE);
        } else {
            checkBox.setVisibility(View.VISIBLE);
        }

        // Set single line
        textView.setSingleLine(true);

        if (checkedObjects.contains(object)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        textView.setText(object.getName());

        // Show the folder icon
        imageView.setImageResource(
                DrawableMapper.getFolderDrawable(preferencesHelper.getUIType())
        );
        return row;
    }


}