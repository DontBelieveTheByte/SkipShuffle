package com.dontbelievethebyte.skipshuffle;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

    private Playlist playlist;

    private MediaPlayer mp;

    private BroadcastReceiver mediaPlayerCommandReceiver;

    //private MediaCommandReceiver mediaCommandReceiver;

    public class MediaCommandReceiver extends BroadcastReceiver {
        public MediaCommandReceiver(){
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Intent detected.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPaused = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Media player service started", Toast.LENGTH_LONG).show();
        registerMediaPlayerBroadcastReceiver();
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "BOUND");
        //return mBinder;
        registerMediaPlayerBroadcastReceiver();
        return null;
    }
    /**
     * Initialize the media player
     */
    @Override
    public void onCreate(){
        registerMediaPlayerBroadcastReceiver();
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new OnCompletionListener(){
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
        this.playlist = playlist;
        doPlay();
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void doPlay() {
//        if(false != isPlaylistSet()) {
//            //If we're at the start of the playlist.
//            if(0 == playlist.getCursorPosition()){
//                loadAudioFile(playlist.getFirst());
//            } else if (true == isPaused) {
//                mp.start();
//            }
//            mp.start();
//            isPaused = false;
//        } else {
//            Log.v(TAG, "PPPLAYLIST EMPTY!");
//        }
        setNotification();
    }

    public void doPause() {
        mp.pause();
        isPaused = true;
    }

    public void doSkip() {
        loadAudioFile(playlist.getNext());
        doPlay();
    }

    public void doPrev() {
        loadAudioFile(playlist.getPrev());
        doPlay();
    }

    public void doShuffle() {
        playlist.shuffle();
        playlist.setCursorPosition(0);
        loadAudioFile(playlist.getFirst());
        doPlay();
    }

    public int getPlaylistCursorPosition(){
        return playlist.getCursorPosition();
    }

    public void setPlaylistCursorPosition(int position){
        playlist.setCursorPosition(position);
        doPlay();
    }
    public boolean isPaused() {
        return isPaused;
    }
    public boolean isPlaylistSet() {
        if (null == playlist) {
            return false;
        } else {
            return true;
        }
    }

    private void loadAudioFile(String mediaFile) {
        try {
            mp.reset();
            mp.setDataSource(mediaFile);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void registerMediaPlayerBroadcastReceiver() {
        if (null == mediaPlayerCommandReceiver) {
            Toast.makeText(getApplicationContext(), "Registerd...", Toast.LENGTH_SHORT).show();

            mediaPlayerCommandReceiver =  new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String command = intent.getStringExtra(SkipShuflleMediaPlayerCommands.COMMAND);
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
            };
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mediaPlayerCommandReceiver, new IntentFilter(BroadcastMessageInterface.CURRENT_FILE_PROCESSING));
    }
    public void unregisterMediaPlayerBroadcastReceiver() {
        if(mediaPlayerCommandReceiver != null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mediaPlayerCommandReceiver);
            mediaPlayerCommandReceiver = null;
        }
    }

    public void brodcastToUI() {

    }

    private void setNotification(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        Resources res = getResources();
        float height = res.getDimension(android.R.dimen.notification_large_icon_height);
        float width = res.getDimension(android.R.dimen.notification_large_icon_width);

        BitmapDrawable ssIconDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ss_icon);

        Bitmap largeIconBitmap = ssIconDrawable.getBitmap();

        largeIconBitmap = Bitmap.createScaledBitmap(largeIconBitmap, (int) width, (int) height, false);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ss_icon)
                           .setLargeIcon(largeIconBitmap)
                           .setContent(remoteViews);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(9001, notificationBuilder.build());
    }
}
