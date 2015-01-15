/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.seeklisteners;

import android.widget.SeekBar;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;

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
            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
            if (fromUser) {
                if (skipShuffleMediaPlayer.isPaused() || skipShuffleMediaPlayer.isPlaying()) {
                    skipShuffleMediaPlayer.seekToPosition(progress);
                }
            }
        } catch (NoMediaPlayerException nm) {
            baseActivity.handleNoMediaPlayerException(nm);
        } catch (PlaylistEmptyException e) {
            baseActivity.handlePlaylistEmptyException(e);
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
