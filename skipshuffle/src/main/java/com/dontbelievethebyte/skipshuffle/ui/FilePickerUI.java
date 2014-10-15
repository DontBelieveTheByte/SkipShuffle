package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

public class FilePickerUI extends AbstractUI {
    private TextView emptyView;
    private ListView listView;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        super(filePickerActivity, R.layout.file_picker_activity);

        bottomLayout = (LinearLayout) baseActivity.findViewById(R.id.background_layout);

        listView = (ListView) filePickerActivity.findViewById(R.id.current_list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setLongClickable(true);
        emptyView = (TextView) bottomLayout.findViewById(R.id.empty_directory);
        listView.setEmptyView(emptyView);
        setUpColors();
        setUpDrawables();
        setUpDimensions();
    }

    private void setUpDrawables()
    {
        ImageButton okButton = (ImageButton) baseActivity.findViewById(R.id.ok);
        okButton.setImageDrawable(
                baseActivity.getResources().getDrawable(
                    DrawableMapper.getOk(uiType)
                )
        );
        ImageButton cancelButton = (ImageButton) baseActivity.findViewById(R.id.cancel);
        cancelButton.setImageDrawable(
                baseActivity.getResources().getDrawable(
                        DrawableMapper.getCancel(uiType)
                )
        );
        ImageButton backButton = (ImageButton) baseActivity.findViewById(R.id.back);
        backButton.setImageDrawable(
                baseActivity.getResources().getDrawable(
                        DrawableMapper.getBack(uiType)
                )
        );
    }

    @Override
    protected void setUpDimensions()
    {
        super.setUpDimensions();
        listView.setDividerHeight(
                (int) baseActivity.getResources().getDimension(R.dimen.list_divider_height)
        );
    }

    @Override
    protected void setUpColors()
    {
        super.setUpColors();
        ColorDrawable colorDrawable = new ColorDrawable(
                baseActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        listView.setDivider(colorDrawable);
        emptyView.setTextColor(
                baseActivity.getResources().getColor(
                        ColorMapper.getEmptyListText(uiType)
                )
        );
    }
}
