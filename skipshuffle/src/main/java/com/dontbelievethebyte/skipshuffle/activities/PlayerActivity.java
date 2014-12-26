/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.activities;

import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.ui.elements.UICompositionFactory;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.seeklisteners.SeekListener;

public class PlayerActivity extends BaseActivity {

    private boolean viewMode;

    @Override
    protected void onPause()
    {
        preferencesHelper.setListViewMode(viewMode);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        viewMode = preferencesHelper.getListViewMode();
    }

    @Override
    protected void setUI(Integer type)
    {
        try {
            ui = (viewMode) ?
                    UICompositionFactory.makeListPlayer(this, type):
                    UICompositionFactory.makeMainPlayer(this, type);
            ui.player.reboot();
        } catch (NoMediaPlayerException e) {
            handleNoMediaPlayerException(e);
        }
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekListener(this));
    }

    @Override
    public void onViewModeChanged()
    {
        viewMode = !viewMode;
        setUI(preferencesHelper.getUIType());
    }
}
