package com.dontbelievethebyte.skipshuffle.service.wrapper;

import android.media.MediaPlayer;

import com.dontbelievethebyte.skipshuffle.exceptions.AudioTrackLoadingException;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

import java.io.IOException;

public class AndroidPlayerWrapper implements MediaPlayer.OnPreparedListener,
                                             MediaPlayer.OnCompletionListener,
                                             MediaPlayer.OnSeekCompleteListener {
    private MediaPlayer mp;
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public AndroidPlayerWrapper(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnSeekCompleteListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer)
    {
        int seekPosition = skipShuffleMediaPlayer.getLastSeekPosition();
        if (seekPosition > 0)
            mp.seekTo(seekPosition);
        else
            mp.start();
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
    }

    public int pausePlayingTrack()
    {
        int currentPosition = mp.getCurrentPosition();
        mp.pause();
        return currentPosition;
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
            mp.prepare();
        } catch (IOException e) {
            throw new AudioTrackLoadingException();
        } catch (IllegalArgumentException e) {
            throw new AudioTrackLoadingException();
        }
    }
}
