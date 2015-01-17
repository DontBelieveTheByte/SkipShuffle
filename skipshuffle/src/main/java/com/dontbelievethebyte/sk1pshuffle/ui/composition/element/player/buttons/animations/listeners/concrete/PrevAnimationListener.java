/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.animations.listeners.concrete;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.animation.Animation;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.animations.listeners.AbstractAnimationListener;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.ColorMapper;

public class PrevAnimationListener extends AbstractAnimationListener {

    public PrevAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        super(playerUI, context);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.prev.setColorFilter(
                context.getResources().getColor(
                        ColorMapper.getPrevButton(playerUI.type)
                ),
                PorterDuff.Mode.SRC_ATOP
        );
        playerUI.buttons.prev.setImageDrawable(playerUI.buttons.drawables.getPrevPressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.prev.setImageDrawable(playerUI.buttons.drawables.getPrev());
    }
}
