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
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;

public class SkipShuffleMediaPlayer extends Service {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private PlaylistInterface playlist;

    private BroadcastReceiver mediaPlayerCommandReceiver;

    private AndroidPlayerWrapper playerWrapper;

    private PreferencesHelper preferencesHelper;

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

        playlist = new RandomPlaylist(
                preferencesHelper.getCurrentPlaylist(),
                new DbHandler(getApplicationContext())
        );

        //@TODO initialize playlist position from prefs helper.
        playlist.setPosition(0);

        broadcastCurrentState(
                SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE, //Issue paused state on start.
                playlist.getPlaylistId(),
                playlist.getPosition()
        );

        playerWrapper = new AndroidPlayerWrapper(getApplicationContext());

        playerWrapper.setPlaylist(playlist);

    }

    @Override
    public void onDestroy(){
        unregisterMediaPlayerBroadcastReceiver();
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
                                playlist.getPosition()
                        );
                        setNotification();

                    } else if (Intent.ACTION_HEADSET_PLUG == actionStringId) {

                        boolean isHeadphonesPlugged =
                                (intent.getIntExtra("state", 0) > 0) //Transform state to boolean
                                && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.

                        if(!playerWrapper.isPaused() && isHeadphonesPlugged) {
                            playerWrapper.doPause();
                            broadcastCurrentState(
                                    playerWrapper.isPlaying() ?
                                            SkipShuflleMediaPlayerCommandsContract.STATE_PLAY :
                                            SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE,
                                    playlist.getPlaylistId(),
                                    playlist.getPosition()
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
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mediaPlayerCommandReceiver);
            mediaPlayerCommandReceiver = null;
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

        if(playerWrapper.isPaused()){
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    R.drawable.pause_states
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
        notificationBuilder.setSmallIcon(R.drawable.ss_icon_notif)
                           .setContent(remoteViews);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(9001, notificationBuilder.build());
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

    private void broadcastCurrentState(String state, long playlistID, int position){
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE, state)
              .putExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID, playlistID)
              .putExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION, position);
        sendStickyBroadcast(intent);
    }
}
