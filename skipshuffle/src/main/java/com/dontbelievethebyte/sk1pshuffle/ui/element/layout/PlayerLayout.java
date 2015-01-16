/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.layout;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.R;

public class PlayerLayout extends AbstractLayout {

    public PlayerLayout(Activity activity)
    {
        super(activity, R.layout.player_mode);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
