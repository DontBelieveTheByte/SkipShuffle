package com.dontbelievethebyte.skipshuffle.ui.playlistselector;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIInterface;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public abstract class PlaylistSelectorUI implements UIInterface {

    public ImageButton playlistPlayBtn;
    public ImageButton playlistPrevBtn;
    public ImageButton playlistSkipBtn;
    public ImageButton playlistShuffleBtn;

    protected PlaylistSelectorActivity playlistSelectorActivity;
    protected int uiType;

    public PlaylistSelectorUI(PlaylistSelectorActivity playlistActivity, int uiType)
    {
        this.playlistSelectorActivity = playlistActivity;
        this.uiType = uiType;
    }

    @Override
    public void doPlay()
    {
        playlistPlayBtn.setImageDrawable(
                playlistSelectorActivity.getResources().getDrawable(
                        UIFactory.getPlayDrawable(playlistSelectorActivity.getPreferencesHelper().getUIType())
                )
        );
    }

    @Override
    public void doPause()
    {
        playlistPlayBtn.setImageDrawable(
                playlistSelectorActivity.getResources().getDrawable(
                        UIFactory.getPauseDrawable(playlistSelectorActivity.getPreferencesHelper().getUIType())
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
