/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.clickListeners.concrete;

import android.view.View;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.track.Track;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.dialog.SongInfoDialog;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.clickListeners.CustomAbstractClickListener;

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
            if (mediaPlayer.isPlaying())
                mediaPlayer.doPause();
            else
                mediaPlayer.doPlay();
        } catch (NoMediaPlayerException n){
            activity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException e) {
            activity.handlePlaylistEmptyException(e);
        }
    }

    @Override
    public boolean onLongClick(View view)
    {
        try {
            SkipShuffleMediaPlayer mp = activity.getMediaPlayer();
            Track track = mp.getPlaylist().getCurrent();
            SongInfoDialog songInfoDialog = new SongInfoDialog(activity);
            songInfoDialog.build(track);
            songInfoDialog.show();
        } catch (NoMediaPlayerException nm) {
            activity.handleNoMediaPlayerException(nm);
        } catch (PlaylistEmptyException pe) {
            activity.handlePlaylistEmptyException(pe);
        }
        return true;
    }
}
