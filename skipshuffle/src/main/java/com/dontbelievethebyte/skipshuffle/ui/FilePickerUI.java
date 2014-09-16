package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

public class FilePickerUI {
    private FilePickerActivity filePickerActivity;
    private int uiType;
    private Typeface typeface;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        this.filePickerActivity = filePickerActivity;
        uiType = this.filePickerActivity.getPreferencesHelper().getUIType();
        filePickerActivity.setContentView(R.layout.file_picker);

        ListView listView = (ListView) filePickerActivity.findViewById(android.R.id.list);

        setDrawables();
        setColors(listView);
    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(filePickerActivity.getAssets(), "fonts/UbuntuMono-B.ttf" );
        }
        return typeface;
    }

    private void setColors(ListView listView)
    {
        RelativeLayout backgroundLayout = (RelativeLayout) filePickerActivity.findViewById(R.id.file_picker_background_layout);

        backgroundLayout.setBackgroundResource(ColorMapper.getBackground(uiType));

        ColorDrawable colorDrawable = new ColorDrawable(
                filePickerActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );
        listView.setDivider(colorDrawable);
        listView.setDividerHeight(1);
    }
    private void setDrawables()
    {
        ImageButton okButton = (ImageButton) filePickerActivity.findViewById(R.id.ok);
        okButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                    DrawableMapper.getOkDrawable(uiType)
                )
        );
        ImageButton cancelButton = (ImageButton) filePickerActivity.findViewById(R.id.cancel);
        cancelButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                        DrawableMapper.getCancelDrawable(uiType)
                )
        );
    }

}
