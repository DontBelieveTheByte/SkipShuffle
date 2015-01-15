/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service;

import android.media.AudioManager;

import com.dontbelievethebyte.sk1pshuffle.exceptions.PlaylistEmptyException;

public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener{

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private boolean wasPlaying = false;

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
                        if (skipShuffleMediaPlayer.isPaused() && wasPlaying)
                            skipShuffleMediaPlayer.doPlay();
                        skipShuffleMediaPlayer.setVolume(1.0f, 1.0f);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                            wasPlaying = skipShuffleMediaPlayer.isPlaying();
                            skipShuffleMediaPlayer.doPause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            wasPlaying = skipShuffleMediaPlayer.isPlaying();
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
