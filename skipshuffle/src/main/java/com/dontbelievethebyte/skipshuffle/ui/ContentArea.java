package com.dontbelievethebyte.skipshuffle.ui;

import android.app.Activity;

public class ContentArea {

    private int layoutId;

    public ContentArea(Activity activity, int layoutId)
    {
        this.layoutId = layoutId;
        activity.setContentView(layoutId);
    }

    public int getLayoutId()
    {
        return layoutId;
    }
}
