package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;

public class PlaylistSelectorUI implements PlayerUIInterface {

    public ImageButton playBtn;
    public ImageButton prevBtn;
    public ImageButton skipBtn;
    public ImageButton shuffleBtn;

    private Drawable playDrawable;
    private Drawable pauseDrawable;
    private Drawable prevDrawable;
    private Drawable skipDrawable;
    private Drawable shuffleDrawable;

    private Typeface typeface;
    private PlaylistSelectorActivity playlistSelectorActivity;
    private int uiType;

    public PlaylistSelectorUI(PlaylistSelectorActivity playlistActivity)
    {
        this.playlistSelectorActivity = playlistActivity;
        uiType = this.playlistSelectorActivity.getPreferencesHelper().getUIType();
        setUpDrawables();
        setUpColors();
    }

    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(
                playlistSelectorActivity.getResources().getDrawable(
                        DrawableMapper.getPlayDrawable(uiType)
                )
        );
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(
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
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    playlistSelectorActivity.getAssets(),
                    "fonts/UbuntuMono-B.ttf"
            );
        }
        return typeface;
    }

    private void setUpDrawables()
    {

    }

    private void setUpColors()
    {

    }
}
