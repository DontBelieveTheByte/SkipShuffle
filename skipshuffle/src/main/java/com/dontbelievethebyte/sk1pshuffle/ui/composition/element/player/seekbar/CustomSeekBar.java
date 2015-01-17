/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.seekbar;


import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;

public class CustomSeekBar implements UIElementCompositeInterface {
    private final static int MILLISECONDS_REFRESH_RATE = 500;

    private BaseActivity baseActivity;
    private android.widget.SeekBar seekBar;
    private android.os.Handler seekHandler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            try {
                SkipShuffleMediaPlayer mp = baseActivity.getMediaPlayer();
                if(mp.isPlaying()) {
                    seekBar.setMax(mp.getCurrentTrackDuration());
                    seekBar.setProgress(mp.getCurrentTrackPosition());
                }
            } catch (NoMediaPlayerException e) {
                baseActivity.handleNoMediaPlayerException(e);
            }
            seekHandler.postDelayed(runnable, MILLISECONDS_REFRESH_RATE);
        }
    };

    public CustomSeekBar(final BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        seekBar = (android.widget.SeekBar) baseActivity.findViewById(R.id.seekBar);
        runnable.run();
    }

    public android.widget.SeekBar getSeekBar()
    {
        return seekBar;
    }

    public void setSeekListener(android.widget.SeekBar.OnSeekBarChangeListener seekBarChangeListener)
    {
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void setEnabled(boolean state)
    {
        seekBar.setEnabled(state);
    }
}
