package com.dontbelievethebyte.skipshuffle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class SkipShuffleMediaPlayer extends Service implements PreferenceChangedCallback {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private static final int NOTIFICATION_ID = 9001;

    private PlaylistInterface playlist;

    private BroadcastReceiver mediaPlayerCommandReceiver;

    private AndroidPlayerWrapper playerWrapper;

    private PreferencesHelper preferencesHelper;

    private DbHandler dbHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        registerMediaPlayerBroadcastReceiver();

        preferencesHelper = new PreferencesHelper(getApplicationContext());

        dbHandler = new DbHandler(getApplicationContext());

        if(preferencesHelper.getLastPlaylist() > 0){
            playlist = new RandomPlaylist(
                    preferencesHelper.getLastPlaylist(),
                    dbHandler
            );

            //@TODO initialize playlist position from prefs helper.
            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());

            broadcastCurrentState(
                    SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE, //Issue paused state on start.
                    playlist.getPlaylistId(),
                    playlist.getPosition(),
                    (playlist.getSize() > 0) ?
                            playlist.getCurrent().getTitle() :
                            getApplicationContext().getString(R.string.unknown_current_song_title)
            );

            playerWrapper = new AndroidPlayerWrapper(getApplicationContext());

            playerWrapper.setPlaylist(playlist);
        }
    }

    @Override
    public void onDestroy(){
        unregisterMediaPlayerBroadcastReceiver();
        playerWrapper.doPause();
        preferencesHelper.setLastPlaylist(playlist.getPlaylistId());
        preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
        removeNotification();
    }

    public void registerMediaPlayerBroadcastReceiver() {
        if (null == mediaPlayerCommandReceiver) {
            mediaPlayerCommandReceiver =  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String actionStringId = intent.getAction();
                    if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND)) {
                        String command = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND);

                        if(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE == command.intern()){
                            if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION)){
                                playlist.setPosition(intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION, 0));
                                playerWrapper.doPlay();
                            } else {
                                if(playerWrapper.isPlaying()){
                                    playerWrapper.doPause();
                                } else {
                                    playerWrapper.doPlay();
                                }
                            }
                        } else if(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP == command.intern()){
                            playerWrapper.doSkip();
                        } else if(SkipShuflleMediaPlayerCommandsContract.CMD_PREV == command.intern()){
                            playerWrapper.doPrev();
                        } else if(command.intern() == SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST){
                            playerWrapper.doShuffle();
                        }

                        broadcastCurrentState(
                                playerWrapper.isPlaying() ?
                                        SkipShuflleMediaPlayerCommandsContract.STATE_PLAY :
                                        SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE, //State from known/received command.
                                playlist.getPlaylistId(),
                                playlist.getPosition(),
                                playlist.getCurrent().getTitle()
                        );
                        setNotification();

                    } else if (Intent.ACTION_HEADSET_PLUG == actionStringId) {

                        boolean isHeadphonesPlugged =
                                (intent.getIntExtra("state", 0) > 0) //Transform state to boolean
                                && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.

                        if(!playerWrapper.isPlaying() && isHeadphonesPlugged) {
                            playerWrapper.doPause();
                            broadcastCurrentState(
                                    playerWrapper.isPlaying() ?
                                            SkipShuflleMediaPlayerCommandsContract.STATE_PLAY :
                                            SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE,
                                    playlist.getPlaylistId(),
                                    playlist.getPosition(),
                                    playlist.getCurrent().getTitle()
                            );
                            setNotification();
                        }
                    }
                }
            };
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(mediaPlayerCommandReceiver, intentFilter);
    }
    public void unregisterMediaPlayerBroadcastReceiver() {
        if(mediaPlayerCommandReceiver != null){
            unregisterReceiver(mediaPlayerCommandReceiver);
        }
    }

    private void setNotification(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_PREV, 0)
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST, 1)
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP, 3)
        );

        if(playerWrapper.isPlaying()){
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    R.drawable.neon_pause_states
            );
        }

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, 2)
        );

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(
                this,
                4,
                mainActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        remoteViews.setOnClickPendingIntent(R.id.notif_all, mainActivityPendingIntent);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ic_neon_notif)
                           .setContent(remoteViews);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void removeNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode){
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        return pendingIntent;
    }

    private void broadcastCurrentState(String state, long playlistID, int position, String currentSongTitle){
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE, state)
              .putExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID, playlistID)
              .putExtra(SkipShuflleMediaPlayerCommandsContract.STATE_CURRENT_SONG_TITLE, currentSongTitle)
              .putExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION, position);
        sendStickyBroadcast(intent);
        Log.d("STATE : ", state);
//        Log.d("TITLE : ", currentSongTitle);
    }

    @Override
    public void preferenceChangedCallback(String prefsKey) {
        if(prefsKey == getString(R.string.pref_current_playlist_id)){
            playlist = new RandomPlaylist(
                preferencesHelper.getLastPlaylist(),
                dbHandler
            );
            playlist.setPosition(0);
            broadcastCurrentState(
               SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE,
               preferencesHelper.getLastPlaylist(),
               preferencesHelper.getLastPlaylistPosition(),
               playlist.getCurrent().getTitle()
            );
            playerWrapper.setPlaylist(playlist);
        }
    }
}
