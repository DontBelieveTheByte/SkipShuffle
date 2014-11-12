package com.dontbelievethebyte.skipshuffle.ui.elements.content;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class ListContentArea extends AbstractContentArea {

    public ListContentArea(Activity activity)
    {
        super(activity, R.layout.playlist_activity);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
