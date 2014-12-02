/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete;

import android.graphics.drawable.Drawable;
import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.AbstractAnimationListener;

public class ShuffleAnimationListener extends AbstractAnimationListener {

    public ShuffleAnimationListener(AbstractPlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.shuffle.setImageDrawable(playerUI.buttons.drawables.getShufflePressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        Drawable shuffleDrawable = playerUI.getShuffleDrawable();
        playerUI.buttons.shuffle.setImageDrawable(shuffleDrawable);
        playerUI.doPlay();
    }
}
