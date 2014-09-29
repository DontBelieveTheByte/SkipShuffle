package com.dontbelievethebyte.skipshuffle.ui;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public abstract class AbstractUI {
    protected int uiType;

    protected BaseActivity baseActivity;
    protected ListView drawerList;
    protected Typeface typeface;
    protected ViewGroup bottomLayout;

    public AbstractUI(BaseActivity activity, int contentLayout)
    {
        this.baseActivity = activity;
        uiType = baseActivity.getPreferencesHelper().getUIType();
        baseActivity.setContentView(contentLayout);
        drawerList = (ListView) activity.findViewById(R.id.left_drawer1);
        bottomLayout = (ViewGroup) baseActivity.findViewById(R.id.bottom);
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
        ColorDrawable navDrawerColorDrawable = new ColorDrawable(
                baseActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        bottomLayout.setBackgroundResource(
                ColorMapper.getBackground(uiType)
        );

        drawerList.setBackgroundResource(
                ColorMapper.getNavDrawerBackground(uiType)
        );

        drawerList.setDivider(navDrawerColorDrawable);
    }
}
