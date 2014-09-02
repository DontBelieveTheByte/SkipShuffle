package com.dontbelievethebyte.skipshuffle.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlist.Track;

import java.io.IOException;

public class AndroidPlayerWrapper {

    private static final String TAG = "SkipShuffleAndroidPlayerWrapper";
    private int seekPosition = 0;
    private MediaPlayer mp;
    private PlaylistInterface playlist;
    private Context context;

    public AndroidPlayerWrapper(Context context)
    {
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

    public void doPlay()
    {
        if (null != playlist) {
            //If we're at the start of the playlist.
            if (0 == playlist.getPosition()) {
                try {
                    loadAudioFile(playlist.getFirst());
                } catch (PlaylistEmptyException e){
                    Log.d(TAG, "CANNOT PLAY FIRST");//@TODO handle this correctly.
                }
            }
            if (!mp.isPlaying()) {
                if (seekPosition > 0) {
                    mp.seekTo(seekPosition);
                    mp.start();
                } else {
                    mp.start();
                }
            }
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
        if (playlist.getPosition() == 0) {
            playlist.setPosition(1);
        }
        if (seekPosition > 0) {
            seekPosition = 0;
        }
        loadAudioFile(playlist.getNext());
        doPlay();
    }

    public void doPrev()
    {
        if (seekPosition > 0) {
            seekPosition = 0;
        }
        loadAudioFile(playlist.getPrev());
        doPlay();
    }

    public void doShuffle()
    {
        try {
            playlist.shuffle();
            playlist.setPosition(0);
            loadAudioFile(playlist.getFirst());
        } catch (PlaylistEmptyException e){
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.shuffle_empty_playlist),
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
    }

    private void loadAudioFile(Track track)
    {
        try {
            mp.reset();
            mp.setDataSource(track.getPath());
            mp.prepare();
        } catch (IOException e) {
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.player_wrapper_song_error) + track.getPath(),
                    Toast.LENGTH_SHORT
            ).show();
            doSkip();
        } catch (IllegalArgumentException e) {
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.player_wrapper_song_error) + track.getPath(),
                    Toast.LENGTH_SHORT
            ).show();
            doSkip();
        }
    }
}
