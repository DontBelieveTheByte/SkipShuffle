package com.dontbelievethebyte.skipshuffle;

import android.widget.ImageButton;

public class UI {

    public static UI createUI(MainActivity mainActivity){
        return new MiniUI(mainActivity);
    }

    private static final String TAG = "SkipShuffleUI";

    protected MainActivity mainActivity;

    public ImageButton playlistBtn;
    public ImageButton prevBtn;
    public ImageButton playBtn;
    public ImageButton shuffleBtn;
    public ImageButton skipBtn;

    public UI(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void doPlay() {}

    public void doPause() {}

    public void doSkip() {}

    public void doPrev() {}

    public void doShuffle() {}

    public void reboot(){
        if(MainActivity.PLAYING == mainActivity.getPlayState()) {
            doPlay();
        } else if (MainActivity.PAUSED == mainActivity.getPlayState()) {
            doPause();
        }
    }
}
