/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.animations.listeners.concrete;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.animation.Animation;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.animations.listeners.AbstractAnimationListener;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.ColorMapper;

public class PauseAnimationListener extends AbstractAnimationListener {

    public PauseAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        super(playerUI, context);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.buttons.play.setColorFilter(
                context.getResources().getColor(
                        ColorMapper.getPauseButton(playerUI.type)
                ),
                PorterDuff.Mode.SRC_IN
        );
        playerUI.buttons.play.setImageDrawable(playerUI.buttons.drawables.getPause());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.play.startAnimation(playerUI.buttons.animations.playAnimation);
    }
}
