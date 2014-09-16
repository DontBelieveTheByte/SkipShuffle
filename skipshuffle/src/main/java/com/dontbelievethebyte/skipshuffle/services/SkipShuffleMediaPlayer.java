package com.dontbelievethebyte.skipshuffle.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.callback.PreferenceChangedCallback;
import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import org.json.JSONException;

import java.io.IOException;

public class SkipShuffleMediaPlayer extends Service implements PreferenceChangedCallback {

    private static final String TAG = "SkipShuffleMediaPlayer";
    private static final int NOTIFICATION_ID = 9001;

    private PlaylistInterface playlist;
    private ClientCommandsBroadcastReceiver clientCommandsBroadcastReceiver;
    private AndroidPlayerWrapper playerWrapper;
    private PreferencesHelper preferencesHelper;
    private DbHandler dbHandler;

    private class AndroidPlayerWrapper implements MediaPlayer.OnPreparedListener,
                                                  MediaPlayer.OnCompletionListener,
                                                  MediaPlayer.OnSeekCompleteListener {
        private int seekPosition = 0;
        private MediaPlayer mp;
        private PlaylistInterface playlist;

        private AndroidPlayerWrapper()
        {
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
                broadcastCurrentState();
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
            broadcastCurrentState();
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
                Toast.makeText(
                        getApplicationContext(),
                        R.string.playlist_error,
                        Toast.LENGTH_SHORT
                ).show();
            }
        }

        public void doPause()
        {
            if (mp.isPlaying()) {
                mp.pause();
                seekPosition = mp.getCurrentPosition();
            }
            broadcastCurrentState();
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
                Toast.makeText(
                        getApplicationContext(),
                        R.string.shuffle_empty_playlist,
                        Toast.LENGTH_SHORT
                ).show();
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
            broadcastCurrentState();
        }

        private void loadAudioFile(Track track)
        {
            try {
                mp.reset();
                mp.setDataSource(track.getPath());
                mp.prepare();
            } catch (IOException e) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.player_wrapper_song_error) + track.getPath(),
                        Toast.LENGTH_SHORT
                ).show();
                doSkip();
            } catch (IllegalArgumentException e) {
                Toast.makeText(
                        getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.player_wrapper_song_error) + track.getPath(),
                        Toast.LENGTH_SHORT
                ).show();
                doSkip();
            }
        }
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
            setNotification();
            preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
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

        try {
            playlist = new RandomPlaylist(
                    preferencesHelper.getLastPlaylist(),
                    dbHandler
            );

            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());

            playerWrapper = new AndroidPlayerWrapper();

            playerWrapper.setPlaylist(playlist);

        } catch (JSONException jsonException){
            Toast.makeText(
                    getApplicationContext(),
                    String.format(getString(R.string.playlist_load_error), preferencesHelper.getLastPlaylist()),
                    Toast.LENGTH_LONG
            ).show();
            preferencesHelper.setLastPlaylist(1);
            preferencesHelper.setLastPlaylistPosition(0);
        }
    }

    @Override
    public void onDestroy()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        removeNotification();
        unregisterMediaPlayerBroadcastReceiver();
        playerWrapper.doPause();
        preferencesHelper.setLastPlaylist(playlist.getPlaylistId());
        preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    @Override
    public void preferenceChangedCallback(String prefsKey)
    {
        if (getString(R.string.pref_current_playlist_id).equals(prefsKey)) {
            try {
                playlist = new RandomPlaylist(
                        preferencesHelper.getLastPlaylist(),
                        dbHandler
                );
                playlist.setPosition(0);
                broadcastCurrentState();
                playerWrapper.setPlaylist(playlist);
            } catch (JSONException jsonException){
                Toast.makeText(
                        getApplicationContext(),
                        String.format(getString(R.string.playlist_load_error), preferencesHelper.getLastPlaylist()),
                        Toast.LENGTH_LONG
                ).show();
                preferencesHelper.setLastPlaylist(1);
                preferencesHelper.setLastPlaylistPosition(0);
            }
        }
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

    private void broadcastCurrentState()
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        String formattedTitle;
        try {
            formattedTitle = buildFormattedTitle(playlist.getCurrent());
        } catch (PlaylistEmptyException e){
            formattedTitle = buildFormattedTitle(null);
        }
        intent.putExtra(
                    SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE,
                    playerWrapper.isPlaying() ?
                        SkipShuflleMediaPlayerCommandsContract.STATE_PLAY :
                        SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE
              ).putExtra(
                    SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID,
                    playlist.getId()
              ).putExtra(
                    SkipShuflleMediaPlayerCommandsContract.STATE_CURRENT_SONG_TITLE,
                    formattedTitle
              ).putExtra(
                    SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION,
                    playlist.getPosition()
              );
        sendStickyBroadcast(intent);
    }

    private String buildFormattedTitle(Track track)
    {
        if (null == track) {
            return getApplicationContext().getString(R.string.meta_data_unknown_current_song_title);
        }
        else if (null == track.getArtist() || null == track.getTitle()) {
            return (null == track.getPath()) ?
                    getApplicationContext().getString(R.string.meta_data_unknown_current_song_title) :
                    track.getPath().substring(track.getPath().lastIndexOf("/") + 1);
        } else {
            return track.getArtist() + " - " + track.getTitle();
        }
    }

    private PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode)
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        return PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
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

    private void removeNotification()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void setNotification()
    {

        RemoteViews remoteViews = new RemoteViews(
                getPackageName(),
                R.layout.notification
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        0
                )
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        1
                )
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
                        3
                )
        );

        if (!playerWrapper.isPlaying()) {
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    DrawableMapper.getPauseDrawable(preferencesHelper.getUIType())
            );
        } else {
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    DrawableMapper.getPlayDrawable(preferencesHelper.getUIType())
            );
        }

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        2
                )
        );

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);


        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(
                this,
                4,
                mainActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_all,
                mainActivityPendingIntent
        );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
                           .setContent(remoteViews);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(
                NOTIFICATION_ID,
                notificationBuilder.build()
        );
    }
}
