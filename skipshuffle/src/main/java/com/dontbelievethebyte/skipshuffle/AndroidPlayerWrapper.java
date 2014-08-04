package com.dontbelievethebyte.skipshuffle;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class AndroidPlayerWrapper {

    private static final String TAG = "SkipShuffleAndroidPlayerWrapper";

    private boolean isPaused = true;

    private MediaPlayer mp;

    private PlaylistInterface playlist;

    Context context;

    public AndroidPlayerWrapper(Context context){
        this.context = context;
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //WTF to do when the track is done playing? Implement next cursor.
            @Override
            public void onCompletion(MediaPlayer mp) {
                doSkip();
            }
        });
    }
    public void setPlaylist(PlaylistInterface playlist) {
        this.playlist = playlist;
    }

    public void doPlay() {
        if(null != playlist) {
            //If we're at the start of the playlist.
            if (0 == playlist.getCursorPosition()) {
                loadAudioFile(playlist.getFirst());
            }
            mp.start();
            isPaused = false;
        }
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

    public long getPlaylistCursorPosition(){
        return playlist.getCursorPosition();
    }

    public void setPlaylistCursorPosition(int position){
        playlist.setCursorPosition(position);
        doPlay();
    }
    public boolean isPaused() {
        return isPaused;
    }

    private void loadAudioFile(Track track) {
        Log.d(TAG, "TRACK : " + track.getPath());
        try {
            mp.reset();
            mp.setDataSource(track.getPath());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
