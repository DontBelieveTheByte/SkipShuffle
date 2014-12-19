/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.layout;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;

public abstract class AbstractLayout implements UIElementCompositeInterface {
    protected ViewGroup bottomLayout;

    public AbstractLayout(Activity activity, int layoutId)
    {
        activity.setContentView(layoutId);
        bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
