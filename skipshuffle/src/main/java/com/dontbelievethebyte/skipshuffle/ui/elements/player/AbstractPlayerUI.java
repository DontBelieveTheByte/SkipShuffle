package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;

public abstract class AbstractPlayerUI implements UIElementCompositeInterface {

    public MainPlayerButtons buttons;
    public SongLabel songLabel;

    public abstract void doPlay();

    public abstract void doPause();

    public abstract void doSkip();

    public abstract void doPrev();

    public abstract void doShuffle();

    public abstract void reboot();

    public abstract void setTitle(String title);

}
