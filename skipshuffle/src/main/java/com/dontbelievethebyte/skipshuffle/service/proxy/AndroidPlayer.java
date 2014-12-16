/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.service.proxy;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.dontbelievethebyte.skipshuffle.exceptions.AudioTrackLoadingException;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

import java.io.IOException;

public class AndroidPlayer implements MediaPlayer.OnPreparedListener,
                                             MediaPlayer.OnCompletionListener,
                                             MediaPlayer.OnSeekCompleteListener {
    private MediaPlayer mp;
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private int seekPosition = 0;
    private boolean paused = false;

    public AndroidPlayer(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnSeekCompleteListener(this);
    }

    public void setVolume (float leftVolume, float rightVolume) {
        mp.setVolume(leftVolume, rightVolume);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        mp.seekTo(seekPosition);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        skipShuffleMediaPlayer.onTrackComplete();
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer)
    {
        mp.start();
        paused = false;
        skipShuffleMediaPlayer.onPlayerStateChanged();
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void pausePlayingTrack()
    {
        if (mp.isPlaying()) {
            seekPosition = mp.getCurrentPosition();
            mp.pause();
            paused = true;
            skipShuffleMediaPlayer.onPlayerStateChanged();
        }
    }

    public void resetSeekPosition()
    {
        seekPosition = 0;
    }

    public boolean isPlaying()
    {
        return mp.isPlaying();
    }

    public void loadAudioFile(Track track) throws AudioTrackLoadingException
    {
        try {
            mp.reset();
            mp.setDataSource(track.getPath());
            mp.prepareAsync();
        } catch (IOException e) {
            throw new AudioTrackLoadingException();
        } catch (IllegalArgumentException e) {
            throw new AudioTrackLoadingException();
        }
    }
}
