package com.dontbelievethebyte.skipshuffle.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;

public class MediaPlayerServiceConnection implements ServiceConnection {

    private SkipShuffleMediaPlayer mediaPlayer;

    public SkipShuffleMediaPlayer getMediaPlayer() throws NoMediaPlayerException
    {
        if (null == mediaPlayer)
            throw new NoMediaPlayerException();
        return mediaPlayer;
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service)
    {
        // We've bound to LocalService, cast the IBinder and get LocalService instance
        SkipShuffleMediaPlayer.MediaPlayerBinder binder = (SkipShuffleMediaPlayer.MediaPlayerBinder) service;
        mediaPlayer = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
        mediaPlayer = null;
    }
}
