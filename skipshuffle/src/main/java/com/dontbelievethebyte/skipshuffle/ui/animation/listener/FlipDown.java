package com.dontbelievethebyte.skipshuffle.ui.animation.listener;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;

public class FlipDown extends AbstractListener{

    public FlipDown(PlayerUI playerUI)
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
        playerUI.buttons.shuffle.setImageDrawable(playerUI.buttons.drawables.getShuffle());
        playerUI.doPlay();
    }
}
