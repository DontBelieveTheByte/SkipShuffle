/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.structure;

import android.graphics.drawable.ColorDrawable;

import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;

public class Colors {

    public int background;
    public int emptyListText;
    public int listDivider;
    public int navDrawerBackground;
    public int navDrawerHeaderText;
    public int navDrawerText;
    public int songLabel;
    public int playlistTitle;
    public int playlistArtist;

    public static ColorDrawable toColorDrawable(Integer color)
    {
        return new ColorDrawable(color);
    }

    public Colors(int uiType)
    {
        background = ColorMapper.getBackground(uiType);
        emptyListText = ColorMapper.getEmptyListText(uiType);
        listDivider = ColorMapper.getListDivider(uiType);
        navDrawerBackground = ColorMapper.getNavDrawerBackground(uiType);
        navDrawerHeaderText = ColorMapper.getNavHeaderText(uiType);
        navDrawerText = ColorMapper.getNavDrawerText(uiType);
        songLabel = ColorMapper.getSongLabel(uiType);
        playlistTitle = ColorMapper.getPlaylistTitle(uiType);
        playlistArtist = ColorMapper.getPlaylistArtist(uiType);
    }
}
