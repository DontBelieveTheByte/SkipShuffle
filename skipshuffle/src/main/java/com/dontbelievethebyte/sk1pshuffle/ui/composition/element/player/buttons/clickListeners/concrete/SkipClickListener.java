/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.clickListeners.concrete;

import android.view.View;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.clickListeners.CustomAbstractClickListener;

public class SkipClickListener extends CustomAbstractClickListener {

    public SkipClickListener(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
            mediaPlayer.doSkip();
            activity.ui.getPlayer().doSkip();
        } catch (NoMediaPlayerException n) {
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException playlistEmptyException) {
            activity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        return false;
    }
}
