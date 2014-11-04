package com.dontbelievethebyte.skipshuffle.ui.click.listener;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class PlayClick extends CustomAbstractClick {

    public PlayClick(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            if (mediaPlayer.isPlaying()) {
                baseActivity.ui.player.doPause();
            } else {
                baseActivity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException n){
            baseActivity.handleNoMediaPlayerException(n);
        }
    }
}
