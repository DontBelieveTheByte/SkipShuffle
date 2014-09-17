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

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePickerListAdapter extends ArrayAdapter<File>
{
    private static class ViewHolder {
        public ImageView image;
        public TextView fileName;
        public CheckBox checkBox;
    }

    private List<File> files;
    private ArrayList<File> checkedFiles = new ArrayList<File>();
    private Drawable folderDrawable;
    private Drawable fileDrawable;
    private int checkBoxDrawable;
    private int fileNameColor;
    private Typeface typeface;

    public FilePickerListAdapter(Context context, List<File> files, PreferencesHelper preferencesHelper, Typeface typeface)
    {
        super(
                context,
                R.layout.file_picker_list_item,
                android.R.id.text1, files
        );

        String[] prefsFiles = preferencesHelper.getMediaDirectories();
        this.typeface = typeface;
        this.files = files;

        folderDrawable = context.getResources().getDrawable(
                    DrawableMapper.getFolderDrawable(preferencesHelper.getUIType()
                )
        );

        fileDrawable = context.getResources().getDrawable(
                DrawableMapper.getFileDrawable(preferencesHelper.getUIType()
                )
        );

        checkBoxDrawable = DrawableMapper.getCheckboxDrawable(preferencesHelper.getUIType());

        fileNameColor = context.getResources().getColor(
                ColorMapper.getListDivider(preferencesHelper.getUIType())
        );
    }

    public void clearBoxes()
    {
        checkedFiles = new ArrayList<File>();
    }

    public String[] getFiles()
    {
        String[] mediaDirectoriesToScan = new String[checkedFiles.size()];
        int i = 0;
        //Save to a class instance array in case the activity needs to restart.
        for (File directory : checkedFiles){
            mediaDirectoriesToScan[i] = directory.getAbsolutePath();
            i++;
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
        if (file.isDirectory()) {
           convertView.setEnabled(false);
           convertView.setBackgroundColor(R.color.list_divider_neon);
        }

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
                    if (isChecked){
                        if (!checkedFiles.contains(file)) {
                            checkedFiles.add(file);
                        }
                    } else {
                        checkedFiles.remove(file);
                    }
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
}