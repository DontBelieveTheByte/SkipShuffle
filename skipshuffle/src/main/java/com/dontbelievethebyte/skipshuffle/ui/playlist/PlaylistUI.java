package com.dontbelievethebyte.skipshuffle.ui.playlist;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.ui.UI;

public class PlaylistUI implements UI {

    protected PlaylistActivity playlistActivity;

    public ImageButton playlistPlayBtn;
    public ImageButton playlistPrevBtn;
    public ImageButton playlistSkipBtn;
    public ImageButton playlistShuffleBtn;

    public PlaylistUI(PlaylistActivity playlistActivity){
        this.playlistActivity = playlistActivity;

    }

    @Override
    public void doPlay() {

    }

    @Override
    public void doPause() {

    }

    @Override
    public void doSkip() {

    }

    @Override
    public void doPrev() {

    }

    @Override
    public void doShuffle() {

    }

    @Override
    public void reboot() {

    }

    @Override
    public void setSongTitle(String title) {

    }

    @Override
    public Typeface getTypeFace() {
        return null;
    }


}
