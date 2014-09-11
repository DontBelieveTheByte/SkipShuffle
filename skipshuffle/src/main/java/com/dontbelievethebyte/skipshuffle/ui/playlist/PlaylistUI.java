package com.dontbelievethebyte.skipshuffle.ui.playlist;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIInterface;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public abstract class PlaylistUI implements UIInterface {

    public ImageButton playlistPlayBtn;
    public ImageButton playlistPrevBtn;
    public ImageButton playlistSkipBtn;
    public ImageButton playlistShuffleBtn;

    protected PlaylistActivity playlistActivity;
    protected int uiType;

    public PlaylistUI(PlaylistActivity playlistActivity, int uiType)
    {
        this.playlistActivity = playlistActivity;
        this.uiType = uiType;
    }

    @Override
    public void doPlay()
    {
        playlistPlayBtn.setImageDrawable(
                playlistActivity.getResources().getDrawable(
                        UIFactory.getPlayDrawable(playlistActivity.getPreferencesHelper().getUIType())
                )
        );
    }

    @Override
    public void doPause()
    {
        playlistPlayBtn.setImageDrawable(
                playlistActivity.getResources().getDrawable(
                        UIFactory.getPauseDrawable(playlistActivity.getPreferencesHelper().getUIType())
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
