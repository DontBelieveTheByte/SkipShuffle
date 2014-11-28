/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;

public class PlaylistClickListener extends CustomAbstractClickListener {

    public PlaylistClickListener(PlayerActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        PlayerActivity playerActivity = (PlayerActivity) activity;
        playerActivity.onViewModeChanged();
    }
}
