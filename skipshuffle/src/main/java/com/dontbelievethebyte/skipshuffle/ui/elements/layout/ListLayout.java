/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.layout;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class ListLayout extends AbstractLayout {

    public ListLayout(Activity activity)
    {
        super(activity, R.layout.playlist_activity);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
