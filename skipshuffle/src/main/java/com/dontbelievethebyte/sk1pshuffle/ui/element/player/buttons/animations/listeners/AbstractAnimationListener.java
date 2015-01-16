/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.animations.listeners;

import android.content.Context;
import android.view.animation.Animation;

import com.dontbelievethebyte.sk1pshuffle.ui.element.player.AbstractPlayerUI;

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
