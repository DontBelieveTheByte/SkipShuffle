package com.dontbelievethebyte.skipshuffle.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;

public class FilePickerUI extends AbstractUI {
    private int emptyViewTextColor;
    private ViewGroup backgroundLayout;
    private TextView emptyView;

    public FilePickerUI(FilePickerActivity filePickerActivity)
    {
        super(filePickerActivity, R.layout.list_activity);

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

    protected void setUpDrawer()
    {

        TextView headerView = (TextView) baseActivity.getLayoutInflater().inflate(
                R.layout.drawer_list_header,
                drawerList
        );

        headerView.setTextColor(
                baseActivity.getResources().getColor(
                        ColorMapper.getNavHeaderText(
                                baseActivity.getPreferencesHelper().getUIType()
                        )
                )
        );
        headerView.setText(
                baseActivity.getString(R.string.drawer_header_text)
        );
        headerView.setTypeface(getTypeFace());

        drawerList.addHeaderView(headerView);

    }
}
