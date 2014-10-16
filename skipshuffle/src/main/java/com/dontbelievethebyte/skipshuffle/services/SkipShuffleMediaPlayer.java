package com.dontbelievethebyte.skipshuffle.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.callbacks.PlaylistChangedCallback;
import com.dontbelievethebyte.skipshuffle.notification.PlayerNotification;
import com.dontbelievethebyte.skipshuffle.persistance.DbHandler;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.utilities.ToastHelper;

import org.json.JSONException;

import java.io.IOException;

public class SkipShuffleMediaPlayer extends Service implements PlaylistChangedCallback {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private PlaylistInterface playlist;
    private ClientCommandsBroadcastReceiver clientCommandsBroadcastReceiver;
    private AndroidPlayerWrapper playerWrapper;
    private PreferencesHelper preferencesHelper;
    private DbHandler dbHandler;
    private ToastHelper toastHelper;
    private PlayerNotification notification;

    public class AndroidPlayerWrapper implements MediaPlayer.OnPreparedListener,
                                                  MediaPlayer.OnCompletionListener,
                                                  MediaPlayer.OnSeekCompleteListener {
        private int seekPosition = 0;
        private MediaPlayer mp;
        private PlaylistInterface playlist;

        private AndroidPlayerWrapper()
        {
            toastHelper = new ToastHelper(getApplicationContext());
            mp = new MediaPlayer();
            mp.setOnCompletionListener(this);
            mp.setOnPreparedListener(this);
            mp.setOnSeekCompleteListener(this);
        }

        @Override
        public void onPrepared(MediaPlayer mediaPlayer)
        {
            if (seekPosition > 0) {
                mp.seekTo(seekPosition);
            } else {
                mp.start();
            }
        }

        @Override
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            doSkip();
        }

        @Override
        public void onSeekComplete(MediaPlayer mediaPlayer)
        {
            mp.start();
        }

        public void doJump(Integer playlistPosition)
        {
            if (null != playlistPosition) {
                playlist.setPosition(playlistPosition);
                seekPosition = 0;
                doPlay();
            }
        }

        public void doPlay()
        {
            try {
                if (!mp.isPlaying() && seekPosition > 0) {
                    loadAudioFile(playlist.getCurrent());
                    mp.seekTo(seekPosition);

                } else {
                    loadAudioFile(playlist.getCurrent());
                }
            } catch (PlaylistEmptyException e){
                //Reload the first playlist in case of error.
                preferencesHelper.setLastPlaylist(1);
                preferencesHelper.setLastPlaylistPosition(0);
                toastHelper.showShortToast(
                        getString(R.string.playlist_error)
                );
            }
        }

        public void doPause()
        {
            if (mp.isPlaying()) {
                mp.pause();
                seekPosition = mp.getCurrentPosition();
            }
        }

        public void doSkip()
        {
            if (seekPosition > 0) {
                seekPosition = 0;
            }
            loadAudioFile(playlist.getNext());
        }

        public void doPrev()
        {
            if (seekPosition > 0) {
                seekPosition = 0;
            }
            loadAudioFile(playlist.getPrev());
        }

        public void doShuffle()
        {
            try {
                playlist.shuffle();
                playlist.setPosition(0);
                seekPosition = 0;
                loadAudioFile(playlist.getFirst());
            } catch (PlaylistEmptyException e){
                toastHelper.showShortToast(
                        getString(R.string.shuffle_empty_playlist)
                );
            }
            doPlay();
        }

        public boolean isPlaying()
        {
            return mp.isPlaying();
        }

        public void setPlaylist(PlaylistInterface playlist)
        {
            this.playlist = playlist;
        }

        private void loadAudioFile(Track track)
        {
            try {
                mp.reset();
                mp.setDataSource(track.getPath());
                mp.prepare();
            } catch (IOException e) {
                toastHelper.showShortToast(
                        getString(R.string.player_wrapper_song_error) + track.getPath()
                );
                doSkip();
            } catch (IllegalArgumentException e) {
                toastHelper.showShortToast(
                        getString(R.string.player_wrapper_song_error) + track.getPath()
                );
                doSkip();
            }
        }
    }

    public AndroidPlayerWrapper getPlayerWrapper()
    {
        return playerWrapper;
    }

    private class ClientCommandsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND)) {
                handleCommand(
                        intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND),
                        intent
                );
            } else if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                boolean isHeadphonesPlugged =
                        (intent.getIntExtra("state", 0) > 0) //Transform state to boolean
                                && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.
                if (!playerWrapper.isPlaying() && isHeadphonesPlugged) {
                    playerWrapper.doPause();
                }
            }
            notification.showNotification();
            preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
        }
    }

    public class MediaPlayerBinder extends Binder {
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
        registerMediaPlayerBroadcastReceiver();

        preferencesHelper = new PreferencesHelper(getApplicationContext());

        dbHandler = new DbHandler(getApplicationContext());

        notification = new PlayerNotification(this);

        try {
            playlist = new RandomPlaylist(
                    preferencesHelper.getLastPlaylist(),
                    dbHandler
            );

            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());

            playerWrapper = new AndroidPlayerWrapper();

            playerWrapper.setPlaylist(playlist);

        } catch (JSONException jsonException) {
            toastHelper.showShortToast(
                    String.format(
                            getString(R.string.playlist_load_error),
                            preferencesHelper.getLastPlaylist()
                    )
            );
            preferencesHelper.setLastPlaylist(1);
            preferencesHelper.setLastPlaylistPosition(0);
        }
    }

    @Override
    public void onDestroy()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(PlayerNotification.getNotificationId());
        notification.cancelNotification();
        unregisterMediaPlayerBroadcastReceiver();
        playerWrapper.doPause();
        preferencesHelper.setLastPlaylist(playlist.getPlaylistId());
        preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startForeground(PlayerNotification.getNotificationId(), notification.buildNotification());
        return START_STICKY;
    }

    @Override
    public void onPlaylistChange(long playlistId)
    {
        try {
            playlist = new RandomPlaylist(
                    playlistId,
                    dbHandler
            );
            playlist.setPosition(0);
            playerWrapper.setPlaylist(playlist);
        } catch (JSONException jsonException){
            handleJSONException(jsonException);
        }
    }

    private void handleJSONException(JSONException jSONException)
    {
        toastHelper.showLongToast(
                String.format(
                        getString(R.string.playlist_load_error),
                        preferencesHelper.getLastPlaylist()
                )
        );
        preferencesHelper.setLastPlaylist(1);
        preferencesHelper.setLastPlaylistPosition(0);
    }

    public void registerMediaPlayerBroadcastReceiver()
    {
        if (null == clientCommandsBroadcastReceiver) {
            clientCommandsBroadcastReceiver = new ClientCommandsBroadcastReceiver();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(clientCommandsBroadcastReceiver, intentFilter);
    }

    public void unregisterMediaPlayerBroadcastReceiver()
    {
        if (clientCommandsBroadcastReceiver != null) {
            unregisterReceiver(clientCommandsBroadcastReceiver);
        }
    }

    private void handleCommand(String command, Intent intent)
    {
        if (SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE.equals(command)){
            if (intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION)) {
                playerWrapper.doJump(
                        intent.getIntExtra(
                                SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION,
                                0
                        )
                );
            } else {
                if (playerWrapper.isPlaying()) {
                    playerWrapper.doPause();
                } else {
                    playerWrapper.doPlay();
                }
            }
        } else if (SkipShuflleMediaPlayerCommandsContract.CMD_SKIP.equals(command)) {
            playerWrapper.doSkip();
        } else if (SkipShuflleMediaPlayerCommandsContract.CMD_PREV.equals(command)) {
            playerWrapper.doPrev();
        } else if (SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST.equals(command)){
            playerWrapper.doShuffle();
        }
    }

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    public PlaylistInterface getPlaylist(){
        return playlist;
    }

}
