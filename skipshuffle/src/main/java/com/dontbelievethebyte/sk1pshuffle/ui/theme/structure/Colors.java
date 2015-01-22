/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.theme.structure;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.ColorMapper;

public class Colors {

    public static ColorDrawable toColorDrawable(Context context, Integer color)
    {
        return new ColorDrawable(context.getResources().getColor(color));
    }

    public int background;
    public int emptyListText;
    public int listDivider;
    public int navDrawerBackground;
    public int navDrawerText;
    public int songLabel;
    public int playlistTitle;
    public int playlistArtist;
    public int playButton;
    public int pauseButton;
    public int skipButton;
    public int prevButton;
    public int playlistButton;
    public int shuffleButton;
    public int seekBarProgress;
    public int seekBarThumb;

    public Colors(UITypes uiType)
    {
        background = ColorMapper.getBackground(uiType);
        emptyListText = ColorMapper.getEmptyListText(uiType);
        listDivider = ColorMapper.getListDivider(uiType);
        navDrawerBackground = ColorMapper.getNavDrawerBackground(uiType);
        navDrawerText = ColorMapper.getNavDrawerText(uiType);
        songLabel = ColorMapper.getSongLabel(uiType);
        playlistTitle = ColorMapper.getPlaylistTitle(uiType);
        playlistArtist = ColorMapper.getPlaylistArtist(uiType);
        playButton = ColorMapper.getPlayButton(uiType);
        pauseButton = ColorMapper.getPauseButton(uiType);
        skipButton = ColorMapper.getPrevButton(uiType);
        prevButton = ColorMapper.getPlaylistButton(uiType);
        playlistButton = ColorMapper.getPlaylistButton(uiType);
        shuffleButton = ColorMapper.getShuffleButton(uiType);
        seekBarProgress = ColorMapper.getSeekBarProgress(uiType);
        seekBarThumb = ColorMapper.getSeekBarThumb(uiType);
    }
}
