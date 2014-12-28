/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete;

import android.util.Log;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.CustomAbstractClickListener;

public class PlayClickListener extends CustomAbstractClickListener {

    public PlayClickListener(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            if (mediaPlayer.isPlaying()) {
//                baseActivity.ui.player.doPause();
                mediaPlayer.doPause();
            } else {
                mediaPlayer.doPlay();
//                baseActivity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException n){
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException e) {
            Log.d(BaseActivity.TAG, "CAUGHT UP!");
            activity.handlePlaylistEmptyException(e);
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        return false;
    }
}
