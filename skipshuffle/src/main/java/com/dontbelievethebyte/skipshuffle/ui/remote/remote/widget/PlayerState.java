/*
 * Copyright (c) 2015. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

public class PlayerState {

    private int uiType;
    private boolean isPlaying = true;
    private boolean isShuffle = false;
    private String title;
    private String artist;

    public PlayerState(int uiType, boolean isPlaying, boolean isShuffle, String artist, String title)
    {
        this.uiType = uiType;
        this.isPlaying = isPlaying;
        this.isShuffle = isShuffle;
        this.title = title;
        this.artist = artist;
    }

    public int getUiType()
    {
        return uiType;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public boolean isShuffle()
    {
        return isShuffle;
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
