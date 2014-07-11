package com.dontbelievethebyte.skipshuffle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.io.IOException;

public class SkipShuffleMediaPlayer extends Service {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private Playlist _playlist;

    private MediaPlayer _mp;

    private BroadcastReceiver mediaPlayerCommandReceiver;

    private SkipShuffleDbHandler _skipShuffleDbHandler;

    private boolean isPaused = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerMediaPlayerBroadcastReceiver();
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "BOUND");
        return null;
    }

    @Override
    public void onCreate(){
        registerMediaPlayerBroadcastReceiver();
        _mp = new MediaPlayer();
        _mp.setOnCompletionListener(new OnCompletionListener() {
            //WTF to do when the track is done playing? Implement next cursor.
            @Override
            public void onCompletion(MediaPlayer mp) {
                doSkip();
            }
        });
    }

    @Override
    public void onDestroy(){
        unregisterMediaPlayerBroadcastReceiver();
    }

    public void setPlaylist(Playlist playlist) {
        this._playlist = playlist;
        doPlay();
    }

    public Playlist getPlaylist() {
        return _playlist;
    }

    public void doPlay() {
//        if(false != isPlaylistSet()) {
//            //If we're at the start of the playlist.
//            if(0 == playlist.getCursorPosition()){
//                loadAudioFile(playlist.getFirst());
//            } else if (true == isPaused) {
//                _mp.start();
//            }
//            _mp.start();
//            isPaused = false;
//        } else {
//            Log.v(TAG, "PPPLAYLIST EMPTY!");
//        }
        setNotification();
    }

    public void doPause() {
        _mp.pause();
        isPaused = true;
    }

    public void doSkip() {
        loadAudioFile(_playlist.getNext());
        doPlay();
    }

    public void doPrev() {
        loadAudioFile(_playlist.getPrev());
        doPlay();
    }

    public void doShuffle() {
        _playlist.shuffle();
        _playlist.setCursorPosition(0);
        loadAudioFile(_playlist.getFirst());
        doPlay();
    }

    public int getPlaylistCursorPosition(){
        return _playlist.getCursorPosition();
    }

    public void setPlaylistCursorPosition(int position){
        _playlist.setCursorPosition(position);
        doPlay();
    }
    public boolean isPaused() {
        return isPaused;
    }
    public boolean isPlaylistSet() {
        if (null == _playlist) {
            return false;
        } else {
            return true;
        }
    }

    private void loadAudioFile(String mediaFile) {
        try {
            _mp.reset();
            _mp.setDataSource(mediaFile);
            _mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void registerMediaPlayerBroadcastReceiver() {
        if (null == mediaPlayerCommandReceiver) {
            mediaPlayerCommandReceiver =  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String actionStringId = intent.getAction();

                    if(SkipShuflleMediaPlayerCommandsContract.COMMAND == actionStringId) {
                        String command = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND);
                        if(command != null){
                            //                    if(SkipShuflleMediaPlayerCommands.CMD_PLAY == command){
//                        doPlay();
//                    } else if (SkipShuflleMediaPlayerCommands.CMD_PAUSE == command){
//                        doPause();
//                    } else if(SkipShuflleMediaPlayerCommands.CMD_SKIP == command){
//                        doSkip();
//                    } else if(SkipShuflleMediaPlayerCommands.CMD_PREV == command){
//                        doPrev();
//                    } else if(SkipShuflleMediaPlayerCommands.CMD_SHUFFLE_PLAYLIST == command){
//                        doShuffle();
//                    } else {
//                        Log.v(TAG, getString(R.string.media_player_unkown_command));
//                    }
                            setNotification();
                            Toast.makeText(getApplicationContext(), "Command received : " + command, Toast.LENGTH_SHORT).show();
                        }

                    } else if (Intent.ACTION_HEADSET_PLUG == actionStringId) {

                        //Filter out sticky broadcast on service start.
                        boolean headphonesSate =  (intent.getIntExtra("state", 0) > 0) && !isInitialStickyBroadcast();
                        Toast.makeText(getApplicationContext(), "Headphones plug Event received : " + headphonesSate, Toast.LENGTH_SHORT).show();
                    } else if(Intent.ACTION_MEDIA_BUTTON == actionStringId) {
                        String key = intent.getStringExtra(Intent.EXTRA_KEY_EVENT);
                        Toast.makeText(getApplicationContext(), "Key Event received : " + key, Toast.LENGTH_SHORT).show();
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
        remoteViews.setOnClickPendingIntent(R.id.notif_prev, buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_PREV, 0));
        remoteViews.setOnClickPendingIntent(R.id.notif_shuffle, buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST, 1));
        remoteViews.setOnClickPendingIntent(R.id.notif_skip, buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP, 3));

        if(isPaused){
            remoteViews.setImageViewResource(R.id.notif_play, R.drawable.pause_states);
            remoteViews.setOnClickPendingIntent(R.id.notif_play, buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_PAUSE, 2));
        } else {
            remoteViews.setOnClickPendingIntent(R.id.notif_play, buildNotificationButtonsPendingIntent(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY, 2));
        }

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(this, 4, mainActivityIntent, PendingIntent.FLAG_CANCEL_CURRENT);

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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }
}
