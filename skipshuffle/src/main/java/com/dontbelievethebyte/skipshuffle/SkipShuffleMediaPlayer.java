package com.dontbelievethebyte.skipshuffle;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class SkipShuffleMediaPlayer extends Service {

    private static final String TAG = "SkipShuffleMediaPlayer";

    private Playlist playlist;

    private MediaPlayer mp;

    private boolean isPaused = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I'M A FUCKING SERVICE!!!", Toast.LENGTH_LONG).show();
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "BOUND");
        //return mBinder;
        return null;
    }
    /**
     * Initialize the media player
     */
    @Override
    public void onCreate(){
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new OnCompletionListener(){
            //WTF to do when the track is done playing? Implement next cursor.
            @Override
            public void onCompletion(MediaPlayer mp) {
                doNext();
            }
        });
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        doPlay();
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void doPlay() {
        if(false != isPlaylistSet()) {
            //If we're at the start of the playlist.
            if(0 == playlist.getCursorPosition()){
                loadAudioFile(playlist.getFirst());
            } else if (true == isPaused) {
                mp.start();
            }
            mp.start();
            isPaused = false;
        } else {
            Log.v(TAG, "PPPLAYLIST EMPTY!");
        }
    }

    public void doPause() {
        mp.pause();
        isPaused = true;
    }

    public void doNext() {
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
}
