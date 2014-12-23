/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners;

import android.content.Context;
import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;

public abstract class AbstractAnimationListener implements Animation.AnimationListener{

    protected AbstractPlayerUI playerUI;
    protected Context context;

    public AbstractAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        this.playerUI = playerUI;
        this.context = context;
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
