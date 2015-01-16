/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.widget;

import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;

public class PlayerState {

    private Integer uiType;
    private Boolean isPlaying = true;
    private Boolean isShuffle = false;
    private String title;
    private String artist;

    public PlayerState(Integer uiType, Boolean isPlaying, Boolean isShuffle, String title, String artist)
    {
        this.uiType = uiType;
        this.isPlaying = isPlaying;
        this.isShuffle = isShuffle;
        this.title = title;
        this.artist = artist;
    }

    public PlayerState(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        try {
            this.uiType = skipShuffleMediaPlayer.getPreferencesHelper().getUIType();
            this.isPlaying = skipShuffleMediaPlayer.isPlaying();
            this.isShuffle = skipShuffleMediaPlayer.getPlaylist().isShuffle();

            TrackPrinter trackPrinter = new TrackPrinter(skipShuffleMediaPlayer);
            Track track = skipShuffleMediaPlayer.getPlaylist().getCurrent();
            this.title = trackPrinter.printTitle(track);
            this.artist = trackPrinter.printArtist(track);
        } catch (PlaylistEmptyException e) {
            skipShuffleMediaPlayer.handlePlaylistEmptyException(e);
        }
    }

    public int getUiType()
    {
        return (null != uiType) ? uiType: UITypes.NEON;
    }

    public boolean isPlaying()
    {
        return (null != isPlaying) ? isPlaying : false;
    }

    public boolean isShuffle()
    {
        return (null != isShuffle) ? isShuffle : false;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }
}
