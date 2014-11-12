package com.dontbelievethebyte.skipshuffle.ui.elements.content;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class PlayerContentArea extends AbstractContentArea {

    public PlayerContentArea(Activity activity)
    {
        super(activity, R.layout.player_activity);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
