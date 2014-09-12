package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;

public class PlaylistSelectorUI implements UIInterface {

    public ImageButton playlistPlayBtn;
    public ImageButton playlistPrevBtn;
    public ImageButton playlistSkipBtn;
    public ImageButton playlistShuffleBtn;

    protected PlaylistSelectorActivity playlistSelectorActivity;
    protected int uiType;

    public PlaylistSelectorUI(PlaylistSelectorActivity playlistActivity)
    {
        this.playlistSelectorActivity = playlistActivity;
    }

    @Override
    public void doPlay()
    {
        playlistPlayBtn.setImageDrawable(
                playlistSelectorActivity.getResources().getDrawable(
                        DrawableMapper.getPlayDrawable(uiType)
                )
        );
    }

    @Override
    public void doPause()
    {
        playlistPlayBtn.setImageDrawable(
                playlistSelectorActivity.getResources().getDrawable(
                        DrawableMapper.getPauseDrawable(uiType)
                )
        );
    }

    @Override
    public void doSkip()
    {

    }

    @Override
    public void doPrev()
    {

    }

    @Override
    public void doShuffle()
    {

    }

    @Override
    public void reboot()
    {

    }

    @Override
    public void setSongTitle(String title)
    {

    }

    @Override
    public Typeface getTypeFace()
    {
        return null;
    }
}
