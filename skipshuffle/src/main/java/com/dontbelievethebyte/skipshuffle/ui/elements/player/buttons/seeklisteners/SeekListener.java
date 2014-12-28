/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.seeklisteners;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class SeekListener implements SeekBar.OnSeekBarChangeListener {
    private BaseActivity baseActivity;

    public SeekListener(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        SeekBar seekBar = (SeekBar) baseActivity.findViewById(R.id.seekBar);
//        seekBar.getProgressDrawable().setColorFilter(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getPlayButton(baseActivity.ui.player.type)
//                ),
//                PorterDuff.Mode.SRC_IN
//        );
        Drawable thumb = baseActivity.getResources().getDrawable(R.drawable.remove_pressed_neon);
        seekBar.setThumb(thumb);
        Drawable progress = baseActivity.getResources().getDrawable(R.drawable.notification_background_mono_light);
        seekBar.setProgressDrawable(progress);
        Drawable background = baseActivity.getResources().getDrawable(R.drawable.notification_background_mono_dark);
        seekBar.setBackgroundColor(
                baseActivity.getResources().getColor(android.R.color.transparent)
        );//        seekBar.setThumb(
//                baseActivity.ui.player.buttons.drawables.getRemove()
//        );

//        seekBar.getBackground().setColorFilter(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getPlayButton(baseActivity.ui.player.type)
//                ),
//                PorterDuff.Mode.SRC_IN
//        );
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        try {
            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
            if (fromUser) {
                if (!skipShuffleMediaPlayer.isPlaying())
                    skipShuffleMediaPlayer.doPlay();
                skipShuffleMediaPlayer.seekTo(progress);
                skipShuffleMediaPlayer.seekTo(progressPercentageToSeekPosition(skipShuffleMediaPlayer, progress));
            } else {
//                seekBar.setProgress(progress);
            }
            Log.d(BaseActivity.TAG, "POSITION: " + Integer.toString(skipShuffleMediaPlayer.getCurrentTrackPosition()));
            Log.d(BaseActivity.TAG, "DURATION: " + Integer.toString(skipShuffleMediaPlayer.getCurrentTrackDuration()));
            Log.d(BaseActivity.TAG, "PROGRESS: " + Integer.toString(progress));

        } catch (NoMediaPlayerException nm) {
            baseActivity.handleNoMediaPlayerException(nm);
        } catch (PlaylistEmptyException e) {
            baseActivity.handlePlaylistEmptyException(e);
        }

    }

    private int progressPercentageToSeekPosition(SkipShuffleMediaPlayer skipShuffleMediaPlayer, int progress)
    {
        return (progress * skipShuffleMediaPlayer.getCurrentTrackDuration()) / 100;
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
