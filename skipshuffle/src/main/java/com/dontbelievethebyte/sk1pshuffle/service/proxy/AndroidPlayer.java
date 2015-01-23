/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service.proxy;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import com.dontbelievethebyte.sk1pshuffle.media.playlist.track.Track;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.AudioTrackLoadingException;

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
        mp.setWakeMode(
                skipShuffleMediaPlayer.getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK
        );
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

    public void seekTo(int position)
    {
        seekPosition = position;
        mp.seekTo(position);
    }

    public int getCurrentTrackDuration()
    {
        return mp.getDuration();
    }
    public int getCurrentTrackPosition()
    {
        return mp.getCurrentPosition();
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
