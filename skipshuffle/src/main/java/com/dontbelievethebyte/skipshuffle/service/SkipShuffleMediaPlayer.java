/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;

import com.dontbelievethebyte.skipshuffle.exceptions.AudioTrackLoadingException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistData;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.service.broadcastreceiver.CommandsBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.service.broadcastreceiver.OrientationBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.service.callbacks.HeadsetPluggedStateCallback;
import com.dontbelievethebyte.skipshuffle.service.callbacks.OrientationChangeCallback;
import com.dontbelievethebyte.skipshuffle.service.callbacks.PlayerStateChangedCallback;
import com.dontbelievethebyte.skipshuffle.service.callbacks.TrackCompleteCallback;
import com.dontbelievethebyte.skipshuffle.service.proxy.AndroidPlayer;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.notification.PlayerNotification;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.callbacks.PrefsCallbacksManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SkipShuffleMediaPlayer extends Service implements PrefsCallbacksManager.PlaylistChangedCallback,
                                                               PrefsCallbacksManager.ThemeChangedCallback,
                                                               HeadsetPluggedStateCallback,
                                                               OrientationChangeCallback,
                                                               TrackCompleteCallback {

    private CommandsBroadcastReceiver clientCommandsBroadcastReceiver;
    private AndroidPlayer playerWrapper;
    private PreferencesHelper preferencesHelper;
    private PlayerNotification notification;
    private RandomPlaylist playlist;
    private MediaPlayerBinder mediaPlayerBinder = new MediaPlayerBinder();
    private Set<PlayerStateChangedCallback> playerStateChangedCallbacks;
    private OrientationBroadcastReceiver orientationBroadcastReceiver;
    private AudioManager audioManager;

    @Override
    public void onThemeChanged()
    {
        initPrefsHelper();
        notification.showNotification();
    }

    @Override
    public void onOrientationChanged()
    {
        notification.showNotification();
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
        return mediaPlayerBinder;
    }

    @Override
    public void onCreate()
    {
        initAudioFocus();
        playerStateChangedCallbacks = new HashSet<PlayerStateChangedCallback>();
        clientCommandsBroadcastReceiver = new CommandsBroadcastReceiver(this);
        clientCommandsBroadcastReceiver.register();
        orientationBroadcastReceiver = new OrientationBroadcastReceiver(this);
        orientationBroadcastReceiver.register();
        initPrefsHelper();
        notification = new PlayerNotification(this);
        playerWrapper = new AndroidPlayer(this);
        initPlaylist();
    }

    private void initAudioFocus()
    {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(
                new AudioFocusManager(this),
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
        );
    }

    private void initPrefsHelper()
    {
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        PrefsCallbacksManager prefsCallbacksManager = new PrefsCallbacksManager(this);
        prefsCallbacksManager.registerThemeChanged(this);
        preferencesHelper.setCallbacksManager(prefsCallbacksManager);
        preferencesHelper.registerPrefsChangedListener();
    }

    private void initPlaylist()
    {
        PlaylistData playlistData = preferencesHelper.getLastPlaylist();
        if (null == playlistData) {
            playlistData = new PlaylistData();
            MediaStoreBridge mediaStoreBridge = new MediaStoreBridge(getApplicationContext());
            playlistData.trackIds = new ArrayList<String>();
            Cursor cursor = mediaStoreBridge.getAllSongs();
            while (cursor.moveToNext()) {
                playlistData.trackIds.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            }
        }
        playlist = new RandomPlaylist(playlistData, new MediaStoreBridge(getApplicationContext()));
    }

    @Override
    public void onDestroy()
    {
        clientCommandsBroadcastReceiver.unregister();
        orientationBroadcastReceiver.unregister();
        notification.cancel();
        doPause();
        preferencesHelper.setLastPlaylist(playlist.getPlaylistData());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(PlayerNotification.getNotificationId(), notification.buildNotification());
        return START_STICKY;
    }

    @Override
    public void onHeadsetStateChanged(boolean isHeadsetPluggedIn)
    {
        if (isPlaying() && !isHeadsetPluggedIn)
            doPause();
    }

    @Override
    public void onPlaylistChange()
    {
        playlist = new RandomPlaylist(
                preferencesHelper.getLastPlaylist(),
                new MediaStoreBridge(getApplicationContext())
        );
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

    public void onPlayerStateChanged()
    {
        notification.showNotification();
        for(PlayerStateChangedCallback playerStateChangedCallback : playerStateChangedCallbacks) {
            playerStateChangedCallback.onPlayerStateChanged();
        }
    }

    public void doPlay() throws PlaylistEmptyException
    {
        try {
            playerWrapper.loadAudioFile(playlist.getCurrent());
        } catch (AudioTrackLoadingException audioLoadingTrackException) {
            handleAudioLoadingTrackException(audioLoadingTrackException);
        }
    }

    public void doPlay(int playlistPosition) throws PlaylistEmptyException
    {
        playerWrapper.resetSeekPosition();
        playlist.setPosition(playlistPosition);
        doPlay();
    }

    public void doPause()
    {
        if (playerWrapper.isPlaying()) {
            playerWrapper.pausePlayingTrack();
            notification.showNotification();
        }
    }

    public void doSkip() throws PlaylistEmptyException
    {
        playlist.setPosition(playlist.getCurrentPosition() + 1);
        doPlay(playlist.getCurrentPosition());
    }

    public void doPrev() throws PlaylistEmptyException
    {
        playlist.setPosition(playlist.getCurrentPosition() - 1);
        doPlay(playlist.getCurrentPosition());
    }

    public void shuffleToggle() throws PlaylistEmptyException
    {
        if (!playlist.isShuffle()) {
            playlist.shuffle();
        }
        playlist.setShuffle(!playlist.isShuffle());
        doPlay(0);
    }

    public void doShuffle() throws PlaylistEmptyException
    {
        playlist.shuffle();
        playlist.setShuffle(true);
        doPlay(0);
    }

    public boolean isPlaying()
    {
        return playerWrapper.isPlaying();
    }

    public boolean isPaused()
    {
        return playerWrapper.isPaused();
    }

    public void setVolume (float leftVolume, float rightVolume) {
        playerWrapper.setVolume(leftVolume, rightVolume);
    }

    public void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        initPlaylist();
    }

    public RandomPlaylist getPlaylist()
    {
        return playlist;
    }

    public void setPlaylist(List<String> trackIds)
    {
        playlist = new RandomPlaylist(
          trackIds,
          new MediaStoreBridge(this)
        );
    }

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    public void registerPlayerStateChanged(PlayerStateChangedCallback playerStateChangedCallback)
    {
        playerStateChangedCallbacks.add(playerStateChangedCallback);
    }

    private void handleAudioLoadingTrackException(AudioTrackLoadingException audioTrackLoadingException) throws PlaylistEmptyException
    {
        doSkip();
    }
}
