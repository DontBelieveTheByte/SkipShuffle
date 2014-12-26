/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.seeklisteners;

import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class SeekListener implements SeekBar.OnSeekBarChangeListener {
    private BaseActivity baseActivity;

    public SeekListener(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        try {
            if (fromUser) {
                SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
                skipShuffleMediaPlayer.seekTo(progress);
            } else {

            }

        } catch (NoMediaPlayerException nm) {
            baseActivity.handleNoMediaPlayerException(nm);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }
}
