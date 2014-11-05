package com.dontbelievethebyte.skipshuffle.service.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

import java.util.HashSet;
import java.util.Set;

public class MediaPlayerServiceConnection implements ServiceConnection {

    public static interface MediaPlayerConnectedCallback
    {
        public void onMediaPlayerAvailable();
    }

    private Set<MediaPlayerConnectedCallback> mediaPlayerConnectedCallbacks;
    private SkipShuffleMediaPlayer mediaPlayer;

    public MediaPlayerServiceConnection()
    {
        mediaPlayerConnectedCallbacks = new HashSet<MediaPlayerConnectedCallback>();
    }

    public void registerCallback(MediaPlayerConnectedCallback mediaPlayerConnectedCallback)
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
        doCallbacks();
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0)
    {
        mediaPlayer = null;
    }

    private void doCallbacks()
    {
        for (MediaPlayerConnectedCallback member: mediaPlayerConnectedCallbacks) {
            member.onMediaPlayerAvailable();
        }
    }
}
