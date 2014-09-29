package com.dontbelievethebyte.skipshuffle.ui;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public abstract class AbstractUI {
    protected BaseActivity baseActivity;
    protected int uiType;
    protected Typeface typeface;
    protected ListView drawerList;


    public AbstractUI(BaseActivity activity, int contentLayout)
    {
        this.baseActivity = activity;
        uiType = baseActivity.getPreferencesHelper().getUIType();
        baseActivity.setContentView(contentLayout);
        drawerList = (ListView) activity.findViewById(R.id.left_drawer1);

    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    baseActivity.getAssets(),
                    TypeFaceMapper.getTypeFace(uiType)
            );
        }
        return typeface;
    }

    protected void setUpDimensions()
    {
        int drawerWidth = (Configuration.ORIENTATION_LANDSCAPE == baseActivity.getResources().getConfiguration().orientation) ?
                baseActivity.getResources().getDisplayMetrics().widthPixels/ DimensionsMapper.drawerWidthDividerLandscape :
                baseActivity.getResources().getDisplayMetrics().widthPixels/ DimensionsMapper.drawerWidthDividerPortrait;

        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = drawerWidth;
        drawerList.setLayoutParams(params);

        int listDividerHeight = (int)baseActivity.getResources().getDimension(R.dimen.list_divider_height);
        drawerList.setDividerHeight(listDividerHeight);
    }

    protected void setUpColors()
    {
        RelativeLayout bottomLayout = (RelativeLayout) baseActivity.findViewById(R.id.bottom);
        bottomLayout.setBackgroundResource(
                ColorMapper.getBackground(uiType)
        );

        ColorDrawable navDrawerColorDrawable = new ColorDrawable(
                baseActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        drawerList.setBackgroundResource(
                ColorMapper.getNavDrawerBackground(uiType)
        );

        drawerList.setDivider(navDrawerColorDrawable);
    }
}
