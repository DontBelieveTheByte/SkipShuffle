package com.dontbelievethebyte.skipshuffle.ui.elements;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class ContentArea implements UIElementCompositeInterface {
    private ViewGroup bottomLayout;

    public ContentArea(Activity activity, int layoutId)
    {
        activity.setContentView(layoutId);
        bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
