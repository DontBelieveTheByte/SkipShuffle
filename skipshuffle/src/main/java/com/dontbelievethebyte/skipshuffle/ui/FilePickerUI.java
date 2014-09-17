package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

public class FilePickerUI {
    private int uiType;
    private FilePickerActivity filePickerActivity;
    private Typeface typeface;
    private ViewGroup backgroundLayout;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        this.filePickerActivity = filePickerActivity;
        uiType = this.filePickerActivity.getPreferencesHelper().getUIType();
        filePickerActivity.setContentView(R.layout.activity_list);

        backgroundLayout = (ViewGroup) filePickerActivity.findViewById(R.id.background_layout);
        LayoutInflater layoutInflater = filePickerActivity.getLayoutInflater();
        layoutInflater.inflate(
                R.layout.file_picker_footer,
                backgroundLayout
        );

        ListView listView = (ListView) filePickerActivity.findViewById(R.id.current_list);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setLongClickable(true);

        layoutInflater.inflate(
                R.layout.file_picker_empty_view,
                backgroundLayout
        );

        int emptyTextColor = filePickerActivity.getResources().getColor(
                ColorMapper.getEmptyListText(filePickerActivity.getPreferencesHelper().getUIType())
        );

        TextView emptyView = (TextView) backgroundLayout.findViewById(R.id.empty_directory);

        emptyView.setTextColor(emptyTextColor);

//        (TextView) emptyView.setTextColor(emptyTextColor);

        //Change this @TODO
//        ((ViewGroup)listView.getParent()).addView(emptyView);
//
        listView.setEmptyView(emptyView);

        setUpDrawables();
        setUpColors(listView);
    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(filePickerActivity.getAssets(), "fonts/UbuntuMono-B.ttf" );
        }
        return typeface;
    }

    private void setUpColors(ListView listView)
    {
        backgroundLayout.setBackgroundResource(ColorMapper.getBackground(uiType));

        ColorDrawable colorDrawable = new ColorDrawable(
                filePickerActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );
        listView.setDivider(colorDrawable);
        listView.setDividerHeight((int)filePickerActivity.getResources().getDimension(R.dimen.list_divider_height));
    }
    private void setUpDrawables()
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
        ImageButton backButton = (ImageButton) filePickerActivity.findViewById(R.id.back);
        backButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                        DrawableMapper.getBackDrawable(uiType)
                )
        );
    }

}
