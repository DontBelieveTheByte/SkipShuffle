package com.dontbelievethebyte.skipshuffle.adapters;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.ParentDirectoryException;
import com.dontbelievethebyte.skipshuffle.exceptions.SubdirectoryException;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.utilities.DirectoryComparator;

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

    private List<File> listedFiles;
    private Drawable folderDrawable;
    private Drawable fileDrawable;
    private DirectoryComparator directoryComparator;
    private FilePickerActivity filePickerActivity;

    private int fileNameColor;
    private int checkBoxDrawable;
    private int checkBoxSubDirectorySelectedDrawable;
    private Typeface typeface;

    public FilePickerListAdapter(FilePickerActivity filePickerActivity, ArrayList<File> listedFiles)
    {
        super(
                filePickerActivity,
                R.layout.file_picker_list_item,
                android.R.id.text1,
                listedFiles
        );

        this.filePickerActivity = filePickerActivity;
        this.listedFiles = listedFiles;

        folderDrawable = filePickerActivity.getResources().getDrawable(
                    DrawableMapper.getFolder(filePickerActivity.getPreferencesHelper().getUIType()
                    )
        );

        fileDrawable = filePickerActivity.getResources().getDrawable(
                DrawableMapper.getFile(filePickerActivity.getPreferencesHelper().getUIType()
                )
        );

        checkBoxDrawable = DrawableMapper.getCheckbox(
                filePickerActivity.getPreferencesHelper().getUIType()
        );
        checkBoxSubDirectorySelectedDrawable = DrawableMapper.getCheckboxSubdirectorySelected(
                filePickerActivity.getPreferencesHelper().getUIType()
        );

        fileNameColor = filePickerActivity.getResources().getColor(
                ColorMapper.getSongLabel(filePickerActivity.getPreferencesHelper().getUIType())
        );
        directoryComparator = new DirectoryComparator();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = filePickerActivity.getLayoutInflater().inflate(
                    R.layout.file_picker_list_item,
                    parent,
                    false
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File aDirectory = listedFiles.get(position);

        viewHolder.image = setFolderImage(
                convertView,
                R.id.file_picker_image,
                aDirectory
        );

        viewHolder.fileName = setFileName(
                convertView,
                R.id.file_picker_text,
                aDirectory
        );

        viewHolder.checkBox = setCheckbox(
                convertView,
                R.id.file_picker_checkbox,
                aDirectory
        );
        return convertView;
    }

    public void refreshFilesList()
    {
        listedFiles.clear(); // Clear the listedFiles ArrayList
        listedFiles.addAll(
                Arrays.asList(filePickerActivity.getCurrentListedDirectory().listFiles())
        );
        Collections.sort(
                listedFiles,
                directoryComparator
        );
        filePickerActivity.notifyAdaptersDataSetChanged();
    }

    @Override
    public boolean isEmpty()
    {
        return listedFiles.isEmpty();
    }

    public void setTypeface(Typeface typeface)
    {
        this.typeface = typeface;
    }

    private ImageView setFolderImage(View view, int resourceId, final File file)
    {
        ImageView folderImage = (ImageView) view.findViewById(resourceId);
        folderImage.setImageDrawable(
                file.isDirectory() ?
                        folderDrawable:
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

    private CheckBox setCheckbox(View view, int resourceId, final File aDirectory)
    {
        CheckBox checkBox = (CheckBox) view.findViewById(resourceId);
        if (aDirectory.isDirectory()) {
            boolean subDirectorySelected = false;
            boolean parentDirectorySelected = false;
            try {
                subDirectorySelected = filePickerActivity.isSubdirectorySelected(aDirectory);
                parentDirectorySelected = filePickerActivity.isParentDirectorySelected(aDirectory);
            } catch (SubdirectoryException subDirectoryException) {
                subDirectorySelected = true;
            } catch (ParentDirectoryException parentDirectoryException) {
               parentDirectorySelected = true;
            }
            checkBox.setBackgroundResource(
                    !subDirectorySelected  && !parentDirectorySelected?
                            checkBoxDrawable :
                            checkBoxSubDirectorySelectedDrawable
            );

            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked) {
                        filePickerActivity.addSelectedDirectory(aDirectory);
                    } else {
                        filePickerActivity.removeSelectedDirectory(aDirectory);
                    }
                }
            });

            checkBox.setChecked(
                    filePickerActivity.getCurrentSelectedDirectories().contains(aDirectory) || subDirectorySelected
            );
        } else {
            checkBox.setVisibility(View.GONE);
        }
        return checkBox;
    }
}

