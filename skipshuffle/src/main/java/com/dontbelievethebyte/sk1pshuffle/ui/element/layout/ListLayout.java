/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.layout;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.R;

public class ListLayout extends AbstractLayout {

    public ListLayout(Activity activity)
    {
        super(activity, R.layout.playlist_mode);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
