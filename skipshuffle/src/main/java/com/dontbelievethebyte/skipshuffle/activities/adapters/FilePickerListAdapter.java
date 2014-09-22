package com.dontbelievethebyte.skipshuffle.activities.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.activities.util.DirectoryComparator;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FilePickerListAdapter extends ArrayAdapter<File>
{
    private static class ViewHolder {
        public ImageView image;
        public TextView fileName;
        public CheckBox checkBox;
    }

    private ArrayList<File> checkedFiles = new ArrayList<File>();
    private List<File> files;
    private Drawable folderDrawable;
    private Drawable fileDrawable;
    private File currentDirectory;

    private int fileNameColor;
    private int checkBoxDrawable;
    private Typeface typeface;

    public FilePickerListAdapter(FilePickerActivity filePickerActivity, List<File> files, Typeface typeface)
    {
        super(
                filePickerActivity,
                R.layout.file_picker_list_item,
                android.R.id.text1,
                files
        );

        this.typeface = typeface;
        this.files = files;

        folderDrawable = filePickerActivity.getResources().getDrawable(
                    DrawableMapper.getFolder(filePickerActivity.getPreferencesHelper().getUIType()
                    )
        );

        fileDrawable = filePickerActivity.getResources().getDrawable(
                DrawableMapper.getFile(filePickerActivity.getPreferencesHelper().getUIType()
                )
        );

        checkBoxDrawable = DrawableMapper.getCheckbox(filePickerActivity.getPreferencesHelper().getUIType());

        fileNameColor = filePickerActivity.getResources().getColor(
                ColorMapper.getSongLabel(filePickerActivity.getPreferencesHelper().getUIType())
        );
    }

    public void clearBoxes()
    {
        checkedFiles = new ArrayList<File>();
    }

    public ArrayList<String> getFiles()
    {
        ArrayList<String> mediaDirectoriesToScan = new ArrayList<String>();
        //Save to a class instance array in case the activity needs to restart.
        for (File directory : checkedFiles){
            mediaDirectoriesToScan.add(directory.getAbsolutePath());
        }
        return mediaDirectoriesToScan;
    }

    public void toggleCheckBox(File file)
    {
        if (checkedFiles.contains(file)) {
            checkedFiles.remove(file);
        } else {
            checkedFiles.add(file);
        }
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
        refreshFilesList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    R.layout.file_picker_list_item,
                    parent,
                    false
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = files.get(position);

        viewHolder.image = setFolderImage(
                convertView,
                R.id.file_picker_image,
                file
        );

        viewHolder.fileName = setFileName(
                convertView,
                R.id.file_picker_text,
                file
        );

        viewHolder.checkBox = setCheckbox(
                convertView,
                R.id.file_picker_checkbox,
                file
        );
        return convertView;
    }

    private ImageView setFolderImage(View view, int resourceId, final File file)
    {
        ImageView folderImage = (ImageView) view.findViewById(resourceId);

        folderImage.setImageDrawable(
                file.isDirectory() ?
                        folderDrawable :
                        fileDrawable
        );

        return folderImage;
    }

    private TextView setFileName(View view, int resourceId, File file)
    {
        TextView fileName = (TextView) view.findViewById(resourceId);
        fileName.setText(file.getName());
        fileName.setTextColor(fileNameColor);
        if (null != typeface) {
            fileName.setTypeface(typeface);
        }
        return fileName;
    }

    private CheckBox setCheckbox(View view, int resourceId, final File file)
    {
        CheckBox checkBox = (CheckBox) view.findViewById(resourceId);

        if (file.isDirectory()) {
            checkBox.setBackgroundResource(checkBoxDrawable);
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    toggleCheckBox(file);
                }
            });

            checkBox.setChecked(
                    checkedFiles.contains(file)
            );
        } else {
            checkBox.setVisibility(View.GONE);
        }
        return checkBox;
    }

    public void refreshFilesList()
    {
        File newFiles[] = currentDirectory.listFiles();
        if (null != newFiles) {
            files.clear(); // Clear the files ArrayList
            files.addAll(Arrays.asList(newFiles));
            Collections.sort(files, new DirectoryComparator());
            clearBoxes(); //clear the checked item list
            notifyDataSetChanged();
        } else {
            currentDirectory = currentDirectory.getParentFile();
            Toast.makeText(
                    getContext(),
                    getContext().getString(R.string.no_access),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}