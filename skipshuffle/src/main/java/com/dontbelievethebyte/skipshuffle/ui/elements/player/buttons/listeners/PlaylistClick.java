package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.util.Log;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

public class PlaylistClick extends CustomAbstractClick {

    public PlaylistClick(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        PreferencesHelper preferencesHelper = activity.getPreferencesHelper();
        preferencesHelper.setListViewMode(!preferencesHelper.getListViewMode());
    }
}
