package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.PlayerButtons;

public abstract class AbstractPlayerUI implements UIElementCompositeInterface {

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
