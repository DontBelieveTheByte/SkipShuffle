package com.dontbelievethebyte.skipshuffle.ui.main;

import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.ui.UI;

public abstract class MainUI implements UI {

    public ImageButton playlistBtn;
    public ImageButton prevBtn;
    public ImageButton playBtn;
    public ImageButton shuffleBtn;
    public ImageButton skipBtn;

    protected MainActivity mainActivity;
    protected TextView songTitle;
    protected Typeface typeface;

    public MainUI(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    public void setSongTitle(String title) {
        songTitle.setText(title);
        songTitle.setSelected(true);
    }

    @Override
    public Typeface getTypeFace()
    {
       return null;
    }
}
