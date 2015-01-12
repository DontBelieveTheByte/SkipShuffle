/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.AbstractAnimationListener;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;

public class PlayAnimationListener extends AbstractAnimationListener {

    public PlayAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        super(playerUI, context);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.buttons.play.setColorFilter(
                context.getResources().getColor(
                        ColorMapper.getPlayButton(playerUI.type)
                ),
                PorterDuff.Mode.SRC_IN
        );
        playerUI.buttons.play.setImageDrawable(playerUI.buttons.drawables.getPlay());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.play.startAnimation(playerUI.buttons.animations.playAnimation);
    }
}
