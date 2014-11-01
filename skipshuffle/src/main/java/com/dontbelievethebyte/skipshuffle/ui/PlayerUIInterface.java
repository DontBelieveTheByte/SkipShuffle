package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;

public interface PlayerUIInterface {

    public void doPlay();

    public void doPause();

    public void doSkip();

    public void doPrev();

    public void doShuffle();

    public void reboot();

    public void setSongLabel(String title);

}
