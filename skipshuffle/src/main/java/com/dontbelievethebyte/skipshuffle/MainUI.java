package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainUI implements UI {

    protected MainActivity mainActivity;

    public ImageButton playlistBtn;
    public ImageButton prevBtn;
    public ImageButton playBtn;
    public ImageButton shuffleBtn;
    public ImageButton skipBtn;

    protected TextView songTitle;
    protected Typeface typeface;

    public MainUI(MainActivity mainActivity){
        this.mainActivity = mainActivity;
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
        songTitle.setText(title);
        songTitle.setSelected(true);
    }

    @Override
    public Typeface getTypeFace() {
        return null;
    }

}
