package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.TextView;

public class UI {

    public static UI createUI(MainActivity mainActivity){
        return new NeonUI(mainActivity);
    }

    private static final String TAG = "SkipShuffleUI";

    protected MainActivity mainActivity;

    public ImageButton playlistBtn;
    public ImageButton prevBtn;
    public ImageButton playBtn;
    public ImageButton shuffleBtn;
    public ImageButton skipBtn;
    protected TextView songTitle;
    protected Typeface typeface;

    public UI(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void doPlay() {}

    public void doPause() {}

    public void doSkip() {}

    public void doPrev() {}

    public void doShuffle() {}

    public void reboot(){}

    public void setSongTitle(String title){}

    public Typeface getTypeFace(){
        return typeface;
    }
}
