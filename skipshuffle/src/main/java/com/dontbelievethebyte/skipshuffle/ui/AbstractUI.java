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

    protected boolean isLandScape;
    protected int computedScreenHeight;
    protected int computedScreenWidth;
    protected BaseActivity baseActivity;
    protected ListView drawerList;
    protected Typeface typeface;
    protected ViewGroup bottomLayout;

    public AbstractUI(BaseActivity activity, int contentLayout)
    {
        this.baseActivity = activity;
        uiType = baseActivity.getPreferencesHelper().getUIType();
        baseActivity.setContentView(contentLayout);
        drawerList = (ListView) activity.findViewById(R.id.drawer_list);
        bottomLayout = (ViewGroup) baseActivity.findViewById(R.id.bottom);
        isLandScape = Configuration.ORIENTATION_LANDSCAPE == baseActivity.getResources().getConfiguration().orientation;
        computedScreenHeight = baseActivity.getResources().getDisplayMetrics().heightPixels;
        computedScreenWidth = baseActivity.getResources().getDisplayMetrics().widthPixels;
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
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = (int) (computedScreenWidth * ( isLandScape ? DimensionsMapper.Drawer.Landscape.width : DimensionsMapper.Drawer.Portrait.width));
        drawerList.setLayoutParams(params);
        int listDividerHeight = (int)baseActivity.getResources().getDimension(R.dimen.list_divider_height);
        drawerList.setDividerHeight(33);
    }

    protected void setUpColors()
    {

        bottomLayout.setBackgroundResource(
                ColorMapper.getBackground(uiType)
        );

        drawerList.setBackgroundResource(
                ColorMapper.getNavDrawerBackground(uiType)
        );

        ColorDrawable navDrawerSeparatorColorDrawable = new ColorDrawable(
                baseActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        drawerList.setDivider(navDrawerSeparatorColorDrawable);
    }
}
