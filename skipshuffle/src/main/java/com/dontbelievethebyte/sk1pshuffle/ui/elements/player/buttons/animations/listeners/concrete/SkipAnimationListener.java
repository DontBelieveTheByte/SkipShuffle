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

public class SkipAnimationListener extends AbstractAnimationListener {

    public SkipAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        super(playerUI, context);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.skip.setColorFilter(
                context.getResources().getColor(
                        ColorMapper.getSkipButton(playerUI.type)
                ),
                PorterDuff.Mode.SRC_ATOP
        );
        playerUI.buttons.skip.setImageDrawable(playerUI.buttons.drawables.getSkipPressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.skip.setImageDrawable(playerUI.buttons.drawables.getSkip());
    }
}
