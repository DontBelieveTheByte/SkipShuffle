package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;

public class SpinLeft extends AbstractListener{

    public SpinLeft(AbstractPlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.prev.setImageDrawable(playerUI.buttons.drawables.getPrevPressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.prev.setImageDrawable(playerUI.buttons.drawables.getPrev());
        playerUI.doPlay();
    }
}
