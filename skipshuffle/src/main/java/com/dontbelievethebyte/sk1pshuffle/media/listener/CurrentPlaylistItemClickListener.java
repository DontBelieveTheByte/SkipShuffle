/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.listener;

import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.activity.PlayerActivity;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;

public class CurrentPlaylistItemClickListener implements AdapterView.OnItemClickListener,
                                                         AdapterView.OnItemLongClickListener{

    private PlayerActivity playerActivity;

    public CurrentPlaylistItemClickListener(PlayerActivity playerActivity)
    {
        this.playerActivity = playerActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = playerActivity.getMediaPlayer();
            RandomPlaylist randomPlaylist = mediaPlayer.getPlaylist();

            if (position == randomPlaylist.getCurrentPosition()) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.doPause();
                } else
                    mediaPlayer.doPlay();
            } else
                mediaPlayer.doPlay(position);
        } catch (NoMediaPlayerException noMediaPlayerException) {
            playerActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            playerActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    private void handleHapticFeedback(View view)
    {
        PreferencesHelper preferencesHelper = playerActivity.getPreferencesHelper();
        if (preferencesHelper.isHapticFeedback())
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        handleHapticFeedback(view);
        try {
            SkipShuffleMediaPlayer mediaPlayer = playerActivity.getMediaPlayer();
            RandomPlaylist randomPlaylist = mediaPlayer.getPlaylist();
            Track track = randomPlaylist.getCurrent();
            ListPlayer player = (ListPlayer) playerActivity.ui.getPlayer();
            player.listView.setSelection(track.getPosition());
        } catch (NoMediaPlayerException noMediaPlayerException) {
            playerActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            playerActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
        return true;
    }
}
