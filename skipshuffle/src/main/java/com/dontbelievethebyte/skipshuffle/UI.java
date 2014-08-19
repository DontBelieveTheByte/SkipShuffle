package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.TextView;

public class UI {
    public static final int MONO_LIGHT = 0;
    public static final int MONO_DARK = 1;
    public static final int NEON = 3;
    public static final int MARIO = 4;

    public static UI createUI(MainActivity mainActivity, Integer uiType){
        switch (uiType){
            case 0 :
                return new MonoLightUI(mainActivity);
            case 1 :
                return new MonoDarkUI(mainActivity);
            case 2 :
                return new NeonUI(mainActivity);
            case 4 :
                return new MarioUI(mainActivity);
            default:
                return new NeonUI(mainActivity);
        }
    }

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
