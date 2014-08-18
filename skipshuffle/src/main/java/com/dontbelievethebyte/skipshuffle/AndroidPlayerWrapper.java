package com.dontbelievethebyte.skipshuffle;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

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

    public boolean isPlaying(){
        return mp.isPlaying();
    }

    public void doPlay() {
        if(null != playlist) {
            //If we're at the start of the playlist.
            if (0 == playlist.getPosition()) {
                loadAudioFile(playlist.getFirst());
            }
            if(!mp.isPlaying()){
                mp.start();
            }
            isPaused = false;
        }
    }

    public void doPause() {
        if(mp.isPlaying()){
            mp.pause();
        }
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
        playlist.setPosition(0);
        loadAudioFile(playlist.getFirst());
        doPlay();
    }

    public long getPlaylistCursorPosition(){
        return playlist.getPosition();
    }

    public void setPlaylistCursorPosition(int position){
        playlist.setPosition(position);
        doPlay();
    }
    public boolean isPaused() {
        return isPaused;
    }

    private void loadAudioFile(Track track) {
        try {
            mp.reset();
            mp.setDataSource(track.getPath());
            mp.prepare();
        } catch (IOException e) {
            Track song = playlist.getCurrent();
            Toast.makeText(context, context.getResources().getString(R.string.player_wrapper_song_error) + song.getPath(), Toast.LENGTH_SHORT).show();
            doSkip();
        } catch (IllegalArgumentException e) {
            Track song = playlist.getCurrent();
            Toast.makeText(context, context.getResources().getString(R.string.player_wrapper_song_error) + song.getPath(), Toast.LENGTH_SHORT).show();
            doSkip();
        }
    }
}
