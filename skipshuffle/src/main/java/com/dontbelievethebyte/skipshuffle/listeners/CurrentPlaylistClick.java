/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;

public class CurrentPlaylistClick implements AdapterView.OnItemClickListener,
                                             AdapterView.OnItemLongClickListener{

    private PlayerActivity playerActivity;

    public CurrentPlaylistClick(PlayerActivity playerActivity)
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
            ListPlayer player = (ListPlayer) playerActivity.ui.player;
            player.listView.setSelection(track.getPosition());
        } catch (NoMediaPlayerException noMediaPlayerException) {
            playerActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            playerActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
        return true;
    }
}
