package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

public class FilePickerUI {
    private FilePickerActivity filePickerActivity;
    private ListView listView;
    private int uiType;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        this.filePickerActivity = filePickerActivity;
        uiType = this.filePickerActivity.getPreferencesHelper().getUIType();
        filePickerActivity.setContentView(R.layout.common_file_picker);

        listView = (ListView) filePickerActivity.findViewById(android.R.id.list);

        setDrawables();
        setColors();
    }

    private void setDrawables()
    {

    }

    private void setColors()
    {
        RelativeLayout backgroundLayout = (RelativeLayout) filePickerActivity.findViewById(R.id.file_picker_background_layout);

        backgroundLayout.setBackgroundResource(ColorMapper.getBackgroundColor(uiType));

        ColorDrawable colorDrawable = new ColorDrawable(
                filePickerActivity.getResources().getColor(
                        ColorMapper.getListDividerColor(uiType)
                )
        );
        listView.setDivider(colorDrawable);
        listView.setDividerHeight(1);
    }
}
