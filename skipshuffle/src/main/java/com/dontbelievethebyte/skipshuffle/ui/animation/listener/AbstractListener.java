package com.dontbelievethebyte.skipshuffle.ui.animation.listener;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.PlayerUI;

public abstract class AbstractListener implements Animation.AnimationListener{

    protected PlayerUI playerUI;

    public AbstractListener(PlayerUI playerUI)
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
