/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.AbstractAnimationListener;

public class PlayAnimationListener extends AbstractAnimationListener {

    public PlayAnimationListener(AbstractPlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.play.startAnimation(playerUI.buttons.animations.playAnimation);
    }
}
