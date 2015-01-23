/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.broadcastreceiver.CommandsBroadcastReceiver;
import com.dontbelievethebyte.sk1pshuffle.service.broadcastreceiver.OrientationBroadcastReceiver;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.HeadsetPluggedStateCallback;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.OrientationChangeCallback;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.PlayerStateChangedCallback;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.TrackCompleteCallback;
import com.dontbelievethebyte.sk1pshuffle.service.exception.AudioTrackLoadingException;
import com.dontbelievethebyte.sk1pshuffle.service.proxy.AndroidPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.notification.PlayerNotification;
import com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.widget.WidgetUpdater;
import com.dontbelievethebyte.sk1pshuffle.utilities.LogUtil;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PrefsCallbacksManager;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.callbacks.PlaylistChangedCallback;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.callbacks.ThemeChangedCallback;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SkipShuffleMediaPlayer extends Service implements PlaylistChangedCallback,
                                                               ThemeChangedCallback,
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
        clientCommandsBroadcastReceiver = new CommandsBroadcastReceiver(this);
        clientCommandsBroadcastReceiver.register();
        orientationBroadcastReceiver = new OrientationBroadcastReceiver(this);
        orientationBroadcastReceiver.register();
        initPrefsHelper();
        notification = new PlayerNotification(this);
        WidgetUpdater widgetUpdater = new WidgetUpdater(this);
        playerStateChangedCallbacks = new HashSet<>();
        playerStateChangedCallbacks.add(notification);
        playerStateChangedCallbacks.add(widgetUpdater);
        playerWrapper = new AndroidPlayer(this);
        initPlaylist();
        widgetUpdater.onPlayerStateChanged();
    }

    private void initAudioFocus()
    {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
        prefsCallbacksManager.registerPlaylistChanged(this);
        preferencesHelper.setCallbacksManager(prefsCallbacksManager);
        preferencesHelper.registerPrefsChangedListener();
    }

    private void initPlaylist()
    {
        PlaylistData playlistData = preferencesHelper.getLastPlaylist();
        if (null == playlistData) {
            playlistData = createPlaylistDataFromNothing();
        }
        playlist = new RandomPlaylist(
                playlistData,
                new MediaStoreBridge(getApplicationContext())
        );
    }

    private PlaylistData createPlaylistDataFromNothing()
    {
        PlaylistData playlistData = new PlaylistData();
        MediaStoreBridge mediaStoreBridge = new MediaStoreBridge(getApplicationContext());
        playlistData.trackIds = new ArrayList<>();
        Cursor cursor = mediaStoreBridge.getAllSongs();
        while (cursor.moveToNext()) {
            playlistData.trackIds.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        }
        return playlistData;
    }

    @Override
    public void onDestroy()
    {
        clientCommandsBroadcastReceiver.unregister();
        orientationBroadcastReceiver.unregister();
        notification.cancel();
        doPause();
        preferencesHelper.setPlaylist(playlist.getPlaylistData());
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
    public void onPlaylistChanged()
    {
        Log.d(LogUtil.TAG, "55555 PLAYLIST CHANGEDSDADAD!!!! 55555");
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

    public void seekToPosition(int position) throws PlaylistEmptyException
    {
        if (playerWrapper.isPlaying() || playerWrapper.isPaused())
            playerWrapper.seekTo(position);
    }

    public RandomPlaylist getPlaylist()
    {
        return playlist;
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

    public int getCurrentTrackDuration()
    {
        return playerWrapper.getCurrentTrackDuration();
    }
    public int getCurrentTrackPosition()
    {
        return playerWrapper.getCurrentTrackPosition();
    }
}
