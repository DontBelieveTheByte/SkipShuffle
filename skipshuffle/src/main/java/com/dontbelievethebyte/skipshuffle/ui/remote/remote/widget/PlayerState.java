/*
 * Copyright (c) 2015. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;

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
