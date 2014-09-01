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
import android.widget.RemoteViews;
import android.widget.Toast;

import org.json.JSONException;

public class SkipShuffleMediaPlayer extends Service implements PreferenceChangedCallback {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private static final int NOTIFICATION_ID = 9001;

    private PlaylistInterface playlist;

    private ClientCommandsBroadcastReceiver clientCommandsBroadcastReceiver;

    private AndroidPlayerWrapper playerWrapper;

    private PreferencesHelper preferencesHelper;

    private DbHandler dbHandler;

    private class ClientCommandsBroadcastReceiver extends BroadcastReceiver {
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
            } else if (Intent.ACTION_HEADSET_PLUG == actionStringId) {

                boolean isHeadphonesPlugged =
                        (intent.getIntExtra("state", 0) > 0) //Transform state to boolean
                                && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.
                if(!playerWrapper.isPlaying() && isHeadphonesPlugged) {
                    playerWrapper.doPause();
                }
            }
            broadcastCurrentState();
            setNotification();
            preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
        }
    };

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

        try {
            playlist = new RandomPlaylist(
                    preferencesHelper.getLastPlaylist(),
                    dbHandler
            );

            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());

            playerWrapper = new AndroidPlayerWrapper(getApplicationContext());

            playerWrapper.setPlaylist(playlist);

            broadcastCurrentState();
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
    public void onDestroy(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        removeNotification();
        unregisterMediaPlayerBroadcastReceiver();
        playerWrapper.doPause();
        preferencesHelper.setLastPlaylist(playlist.getPlaylistId());
        preferencesHelper.setLastPlaylistPosition(playlist.getPosition());
    }

    public void registerMediaPlayerBroadcastReceiver() {
        if (null == clientCommandsBroadcastReceiver) {
            clientCommandsBroadcastReceiver = new ClientCommandsBroadcastReceiver();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(clientCommandsBroadcastReceiver, intentFilter);
    }
    public void unregisterMediaPlayerBroadcastReceiver() {
        if(clientCommandsBroadcastReceiver != null){
            unregisterReceiver(clientCommandsBroadcastReceiver);
        }
    }

    private void setNotification(){
        RemoteViews remoteViews = new RemoteViews(
                getPackageName(),
                UIFactory.getNotificationLayout(2)
        );
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
                    UIFactory.getPauseDrawable(preferencesHelper.getUIType())
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
        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
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
        return PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    private void broadcastCurrentState(){
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
                        )
              .putExtra(
                            SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID,
                            playlist.getId()
                        )
              .putExtra(
                            SkipShuflleMediaPlayerCommandsContract.STATE_CURRENT_SONG_TITLE,
                            formattedTitle
                       )
              .putExtra(
                      SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION,
                      playlist.getPosition()
              );
        sendStickyBroadcast(intent);
    }

    @Override
    public void preferenceChangedCallback(String prefsKey) {
        if(prefsKey == getString(R.string.pref_current_playlist_id)){
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

    private String buildFormattedTitle(Track track){
        if (null == track){
            return getApplicationContext().getString(R.string.meta_data_unknown_current_song_title);
        }
        else if (null == track.getArtist() || null == track.getTitle()){
            if(null == track.getPath()){
                return getApplicationContext().getString(R.string.meta_data_unknown_current_song_title);
            } else {
                //Just the filename separated from path.
                return track.getPath().substring(track.getPath().lastIndexOf("/") + 1);
            }
        } else {
            return track.getArtist() + " - " + track.getTitle();
        }
    }
}
