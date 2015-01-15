/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.animations.listeners.concrete;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.animations.listeners.AbstractAnimationListener;
import com.dontbelievethebyte.sk1pshuffle.ui.mapper.ColorMapper;

public class ShuffleAnimationListener extends AbstractAnimationListener {

    public ShuffleAnimationListener(AbstractPlayerUI playerUI, Context context)
    {
        super(playerUI, context);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.play.setColorFilter(
                context.getResources().getColor(
                        ColorMapper.getPlayButton(playerUI.type)
                ),
                PorterDuff.Mode.SRC_ATOP
        );
        playerUI.buttons.shuffle.setImageDrawable(playerUI.buttons.drawables.getShufflePressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        Drawable shuffleDrawable = playerUI.getShuffleDrawable();
        playerUI.buttons.shuffle.setImageDrawable(shuffleDrawable);
    }
}
