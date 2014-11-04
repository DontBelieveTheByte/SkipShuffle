package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.Activity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class PlayClick extends CustomAbstractClick {

    public PlayClick(Activity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            if (mediaPlayer.isPlaying()) {
                activity.ui.player.doPause();
            } else {
                activity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException n){
            activity.handleNoMediaPlayerException(n);
        }
    }
}
