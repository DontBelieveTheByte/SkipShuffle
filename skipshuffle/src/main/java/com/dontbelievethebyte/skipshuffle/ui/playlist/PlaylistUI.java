package com.dontbelievethebyte.skipshuffle.ui.playlist;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.ui.UI;

public abstract class PlaylistUI implements UI {

    public ImageButton playlistPlayBtn;
    public ImageButton playlistPrevBtn;
    public ImageButton playlistSkipBtn;
    public ImageButton playlistShuffleBtn;

    protected PlaylistActivity playlistActivity;

    public PlaylistUI(PlaylistActivity playlistActivity)
    {
        this.playlistActivity = playlistActivity;
    }

    @Override
    public void setSongTitle(String title) {}

    @Override
    public Typeface getTypeFace()
    {
        return null;
    }
}
