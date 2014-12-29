/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.dialog.SongInfoDialog;
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
