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
    private int emptyViewTextColor;
    private FilePickerActivity filePickerActivity;
    private Typeface typeface;
    private ViewGroup backgroundLayout;
    private ListView drawerList;
    private TextView emptyView;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        filePickerActivity.setContentView(R.layout.list_activity);

        this.filePickerActivity = filePickerActivity;

        uiType = this.filePickerActivity.getPreferencesHelper().getUIType();

        emptyViewTextColor = filePickerActivity.getResources().getColor(
                ColorMapper.getEmptyListText(filePickerActivity.getPreferencesHelper().getUIType())
        );

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

        emptyView = (TextView) backgroundLayout.findViewById(R.id.empty_directory);
        listView.setEmptyView(emptyView);

        drawerList = (ListView) filePickerActivity.findViewById(R.id.left_drawer1);

        setUpDrawables();
        setUpColors(listView);
    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    filePickerActivity.getAssets(),
                    TypeFaceMapper.getTypeFace(uiType)

            );
        }
        return typeface;
    }

    private void setUpColors(ListView listView)
    {
        //@TODO should probably be in future setUpDimension method.
        int listDividerHeight = (int)filePickerActivity.getResources().getDimension(R.dimen.list_divider_height);

        backgroundLayout.setBackgroundResource(ColorMapper.getBackground(uiType));

        emptyView.setTextColor(emptyViewTextColor);

        ColorDrawable colorDrawable = new ColorDrawable(
                filePickerActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );
        listView.setDivider(colorDrawable);
        listView.setDividerHeight(listDividerHeight);

        ColorDrawable navDrawerColorDrawable = new ColorDrawable(
                    filePickerActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        drawerList.setBackgroundResource(
                ColorMapper.getNavDrawerBackground(uiType)
        );

        drawerList.setDivider(navDrawerColorDrawable);
        drawerList.setDividerHeight(listDividerHeight);
    }

    private void setUpDrawables()
    {
        ImageButton okButton = (ImageButton) filePickerActivity.findViewById(R.id.ok);
        okButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                    DrawableMapper.getOk(uiType)
                )
        );
        ImageButton cancelButton = (ImageButton) filePickerActivity.findViewById(R.id.cancel);
        cancelButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                        DrawableMapper.getCancel(uiType)
                )
        );
        ImageButton backButton = (ImageButton) filePickerActivity.findViewById(R.id.back);
        backButton.setImageDrawable(
                filePickerActivity.getResources().getDrawable(
                        DrawableMapper.getBack(uiType)
                )
        );
    }

}
