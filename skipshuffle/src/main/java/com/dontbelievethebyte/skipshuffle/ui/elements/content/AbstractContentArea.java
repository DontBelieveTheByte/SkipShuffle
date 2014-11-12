package com.dontbelievethebyte.skipshuffle.ui.elements.content;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;

public abstract class AbstractContentArea implements UIElementCompositeInterface {
    protected ViewGroup bottomLayout;

    public AbstractContentArea(Activity activity, int layoutId)
    {
        activity.setContentView(layoutId);
        bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
