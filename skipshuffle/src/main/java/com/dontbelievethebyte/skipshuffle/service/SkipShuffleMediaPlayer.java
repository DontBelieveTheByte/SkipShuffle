package com.dontbelievethebyte.skipshuffle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.callbacks.HeadsetPluggedStateCallback;
import com.dontbelievethebyte.skipshuffle.service.callbacks.MediaPlayerCommandsCallback;
import com.dontbelievethebyte.skipshuffle.preferences.callbacks.PlaylistChangedCallback;
import com.dontbelievethebyte.skipshuffle.service.callbacks.TrackCompleteCallback;
import com.dontbelievethebyte.skipshuffle.exceptions.AudioTrackLoadingException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.ui.notification.PlayerNotification;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.service.broadcastreceiver.CommandsBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.service.proxy.AndroidPlayer;

public class SkipShuffleMediaPlayer extends Service implements PlaylistChangedCallback,
                                                               HeadsetPluggedStateCallback,
                                                               MediaPlayerCommandsCallback,
                                                               TrackCompleteCallback{

    private CommandsBroadcastReceiver clientCommandsBroadcastReceiver;
    private AndroidPlayer playerWrapper;
    private PreferencesHelper preferencesHelper;
    private PlayerNotification notification;
    private int lastSeekPosition = 0;
    private Track currentTrack;

    @Override
    public void onCommand(String command, Integer newCursorPosition) {
        java.lang.reflect.Method method;
        try {
            method = this.getClass().getMethod(command);
            if (null != newCursorPosition)
                method.invoke(this);
            else
                method.invoke(this);
        } catch (Exception e) {
            handleCommandException(e);
        }
    }

    private void handleCommandException(Exception exception)
    {
        Log.d(BaseActivity.TAG, exception.getMessage());
    }

    public class MediaPlayerBinder extends Binder
    {
        public SkipShuffleMediaPlayer getService()
        {
            return SkipShuffleMediaPlayer.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        clientCommandsBroadcastReceiver = new CommandsBroadcastReceiver(this);
        clientCommandsBroadcastReceiver.register();
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        notification = new PlayerNotification(this);
        playerWrapper = new AndroidPlayer(this);
    }

    @Override
    public void onDestroy()
    {
        clientCommandsBroadcastReceiver.unregister();
        notification.cancel();
        doPause();
        preferencesHelper.setLastPlaylist(0);
        preferencesHelper.setLastPlaylistPosition(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(PlayerNotification.getNotificationId(), notification.buildNotification());
        return START_STICKY;
    }

    @Override
    public void onHeadsetStateChanged(boolean isHeadsetPluggedIn) {
        if (!playerWrapper.isPlaying() && isHeadsetPluggedIn)
            doPause();
        notification.showNotification();
    }

    @Override
    public void onPlaylistChange(long playlistId)
    {
    }

    @Override
    public void onTrackComplete()
    {
        try {
            doSkip();
        } catch (PlaylistEmptyException playlistEmptyException) {
            handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    public void doPlay() throws PlaylistEmptyException
    {
        if (playerWrapper.isPlaying())
            doPause();
        else {
            try {
                resetSeekPosition();
                playerWrapper.loadAudioFile(currentTrack);
            } catch (AudioTrackLoadingException audioLoadingTrackException) {
                handleAudioLoadingTrackException(audioLoadingTrackException);
            }
        }
    }

    public void doPlay(int playlistPosition) throws PlaylistEmptyException
    {
        resetSeekPosition();
        doPlay();
    }

    private void handleAudioLoadingTrackException(AudioTrackLoadingException audioTrackLoadingException) throws PlaylistEmptyException
    {
        doSkip();
    }

    public void doPause()
    {
        lastSeekPosition = (playerWrapper.isPlaying()) ?
                playerWrapper.pausePlayingTrack() :
                0;
    }

    public void doSkip() throws PlaylistEmptyException
    {
        resetSeekPosition();
        doPlay();
    }

    public void doPrev() throws PlaylistEmptyException
    {
        resetSeekPosition();
        doPlay();
    }

    public void doShuffle() throws PlaylistEmptyException
    {
        resetSeekPosition();
        doPlay();
    }

    public boolean isPlaying()
    {
        return playerWrapper.isPlaying();
    }

    public int getLastSeekPosition()
    {
        return lastSeekPosition;
    }

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    public PlaylistInterface getPlaylist()
    {
        return null;
    }

    private void resetSeekPosition()
    {
        lastSeekPosition = 0;
    }

    public void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        preferencesHelper.setLastPlaylist(0);
        preferencesHelper.setLastPlaylistPosition(0);
    }
}
