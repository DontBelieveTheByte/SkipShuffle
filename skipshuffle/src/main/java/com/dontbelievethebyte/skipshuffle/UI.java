package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;

public interface UI {

    public void doPlay();

    public void doPause();

    public void doSkip();

    public void doPrev();

    public void doShuffle();

    public void reboot();

    public void setSongTitle(String title);

    public Typeface getTypeFace();
}
