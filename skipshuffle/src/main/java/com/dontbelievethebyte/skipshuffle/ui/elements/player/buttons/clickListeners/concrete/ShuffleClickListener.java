/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.CustomAbstractClickListener;

public class ShuffleClickListener extends CustomAbstractClickListener {

    public ShuffleClickListener(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            mediaPlayer.shuffleToggle();
            activity.ui.player.doShuffle();
        } catch (NoMediaPlayerException n) {
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException playlistEmptyException) {
            activity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            mediaPlayer.doShuffle();
            activity.ui.player.doShuffle();
        } catch (NoMediaPlayerException n) {
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException playlistEmptyException) {
            activity.handlePlaylistEmptyException(playlistEmptyException);
        }
        return true;
    }
}
