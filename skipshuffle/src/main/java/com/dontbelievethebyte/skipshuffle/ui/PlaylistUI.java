package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

public class PlaylistUI implements UIInterface {

    public ImageButton playBtn;
    public ImageButton prevBtn;
    public ImageButton skipBtn;
    public ImageButton shuffleBtn;

    protected PlaylistActivity playlistActivity;

    public PlaylistUI(PlaylistActivity playlistActivity)
    {
        this.playlistActivity = playlistActivity;

        playlistActivity.setContentView(R.layout.common_activity_playlist);

        playBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_play);
        shuffleBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_shuffle);
        skipBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_skip);
        prevBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_prev);
    }

    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(
                playlistActivity.getResources().getDrawable(
                        DrawableMapper.getPlayDrawable(playlistActivity.getPreferencesHelper().getUIType())
                )
        );
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(
                playlistActivity.getResources().getDrawable(
                        DrawableMapper.getPauseDrawable(playlistActivity.getPreferencesHelper().getUIType())
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
