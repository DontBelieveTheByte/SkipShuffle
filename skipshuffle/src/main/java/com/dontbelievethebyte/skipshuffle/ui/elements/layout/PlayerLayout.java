package com.dontbelievethebyte.skipshuffle.ui.elements.layout;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class PlayerLayout extends AbstractLayout {

    public PlayerLayout(Activity activity)
    {
        super(activity, R.layout.player_activity);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
