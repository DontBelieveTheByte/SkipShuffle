/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.service;

import android.media.AudioManager;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;

public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener{

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    AudioFocusManager(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (null != skipShuffleMediaPlayer){
            try {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (skipShuffleMediaPlayer.isPaused())
                                skipShuffleMediaPlayer.doPlay();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        if (skipShuffleMediaPlayer.isPlaying())
                            skipShuffleMediaPlayer.doPause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if (skipShuffleMediaPlayer.isPlaying())
                            skipShuffleMediaPlayer.doPause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (skipShuffleMediaPlayer.isPlaying())
                            skipShuffleMediaPlayer.setVolume(0.1f, 0.1f);
                        break;
                }
            } catch (PlaylistEmptyException e) {
                skipShuffleMediaPlayer.handlePlaylistEmptyException(e);
            }
        }
    }
}
