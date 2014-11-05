package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;

public abstract class AbstractListener implements Animation.AnimationListener{

    protected AbstractPlayerUI playerUI;

    public AbstractListener(AbstractPlayerUI playerUI)
    {
        this.playerUI = playerUI;
    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {

    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }
}
