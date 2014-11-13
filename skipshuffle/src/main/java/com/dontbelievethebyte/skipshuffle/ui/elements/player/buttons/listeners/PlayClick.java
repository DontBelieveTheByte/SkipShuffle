package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
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
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.doPause();
                activity.ui.player.doPause();
            } else {
                mediaPlayer.doPlay();
                activity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException n){
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException e) {
            e.printStackTrace();
        }
    }
}
