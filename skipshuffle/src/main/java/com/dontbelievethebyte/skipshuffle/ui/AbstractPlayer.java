package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtonsAnimations;

public abstract class AbstractPlayer {

    public PlayerButtons buttons;
    public PlayerButtonsAnimations animations;
    public Drawables drawables;
    public SongLabel songLabel;

    public abstract void doPlay();

    public abstract void doPause();

    public abstract void doSkip();

    public abstract void doPrev();

    public abstract void doShuffle();

    public abstract void reboot();

    public abstract void setSongLabel(String title);

}
