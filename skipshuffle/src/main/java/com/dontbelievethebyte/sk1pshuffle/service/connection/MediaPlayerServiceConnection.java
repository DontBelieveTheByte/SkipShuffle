/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.dontbelievethebyte.sk1pshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.PlayerStateChangedCallback;

import java.util.HashSet;
import java.util.Set;

public class MediaPlayerServiceConnection implements ServiceConnection, PlayerStateChangedCallback {

    public static interface MediaPlayerConnectedCallback
    {
        public void onMediaPlayerAvailable();
    }

    private Set<MediaPlayerConnectedCallback> mediaPlayerConnectedCallbacks;
    private Set<PlayerStateChangedCallback> playerStateChangedCallbacks;
    private SkipShuffleMediaPlayer mediaPlayer;

    public MediaPlayerServiceConnection()
    {
        mediaPlayerConnectedCallbacks = new HashSet<MediaPlayerConnectedCallback>();
        playerStateChangedCallbacks = new HashSet<PlayerStateChangedCallback>();
    }

    public void registerConnectedCallback(MediaPlayerConnectedCallback mediaPlayerConnectedCallback)
    {
        mediaPlayerConnectedCallbacks.add(mediaPlayerConnectedCallback);
    }

    public SkipShuffleMediaPlayer getMediaPlayer() throws NoMediaPlayerException
    {
        if (null == mediaPlayer)
            throw new NoMediaPlayerException();
        return mediaPlayer;
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service)
    {
        SkipShuffleMediaPlayer.MediaPlayerBinder binder = (SkipShuffleMediaPlayer.MediaPlayerBinder) service;
        mediaPlayer = binder.getService();
        mediaPlayer.registerPlayerStateChanged(this);
        doConnectedCallbacks();
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0)
    {
        mediaPlayer = null;
    }

    private void doConnectedCallbacks()
    {
        for (MediaPlayerConnectedCallback member: mediaPlayerConnectedCallbacks) {
            member.onMediaPlayerAvailable();
        }
    }

    @Override
    public void onPlayerStateChanged()
    {
        for(PlayerStateChangedCallback playerStateChangedCallback : playerStateChangedCallbacks) {
            playerStateChangedCallback.onPlayerStateChanged();
        }
    }

    public void registerPlayerStateChanged(PlayerStateChangedCallback playerStateChangedCallback)
    {
        playerStateChangedCallbacks.add(playerStateChangedCallback);
    }

    public void unRegisterPlayerStateChanged(PlayerStateChangedCallback playerStateChangedCallback)
    {
        playerStateChangedCallbacks.remove(playerStateChangedCallback);
    }
}
