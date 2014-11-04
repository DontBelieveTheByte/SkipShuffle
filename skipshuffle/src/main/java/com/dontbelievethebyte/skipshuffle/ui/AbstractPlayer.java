package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtons;

public abstract class AbstractPlayer implements UIElement {

    public PlayerButtons buttons;
    public SongLabel songLabel;

    public abstract void doPlay();

    public abstract void doPause();

    public abstract void doSkip();

    public abstract void doPrev();

    public abstract void doShuffle();

    public abstract void reboot();

    public abstract void setTitle(String title);

}
