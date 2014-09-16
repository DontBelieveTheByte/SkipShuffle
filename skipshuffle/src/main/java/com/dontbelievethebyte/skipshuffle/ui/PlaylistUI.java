package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

public class PlaylistUI implements PlayerUIInterface {

    public ImageButton playBtn;
    public ImageButton prevBtn;
    public ImageButton skipBtn;
    public ImageButton shuffleBtn;

    private int uiType;
    private Typeface typeface;
    private PlaylistActivity playlistActivity;
    private LinearLayout backgroundLayout;
    private Drawable playDrawable;
    private Drawable pauseDrawable;
    private Drawable prevDrawable;
    private Drawable skipDrawable;
    private Drawable shuffleDrawable;

    public PlaylistUI(PlaylistActivity playlistActivity)
    {
        this.playlistActivity = playlistActivity;

        playlistActivity.setContentView(R.layout.activity_list);

        uiType = playlistActivity.getPreferencesHelper().getUIType();

        playBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_play);
        shuffleBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_shuffle);
        skipBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_skip);
        prevBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_prev);

        backgroundLayout = (LinearLayout) playlistActivity.findViewById(R.id.playlist_background_layout);
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
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    playlistActivity.getAssets(),
                    "fonts/UbuntuMono-B.ttf"
            );
        }
        return typeface;
    }

    private void setDrawables()
    {

    }

    private void setColors()
    {

    }

}
