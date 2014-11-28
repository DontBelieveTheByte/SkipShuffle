/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;

public class Jerk extends AbstractListener{

    public Jerk(AbstractPlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.play.startAnimation(playerUI.buttons.animations.jerkRightAnimation);
    }
}
