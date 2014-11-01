package com.dontbelievethebyte.skipshuffle.ui.animation.listener;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;

public class FlipLeft extends AbstractListener{

    public FlipLeft(PlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.prev.setImageDrawable(playerUI.drawables.getPrevPressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.prev.setImageDrawable(playerUI.drawables.getPrev());
        playerUI.doPlay();
    }
}
