package com.dontbelievethebyte.skipshuffle.ui.animation.listener;

import android.view.animation.Animation;

import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;

public class FlipRight extends AbstractListener{

    public FlipRight(PlayerUI playerUI)
    {
        super(playerUI);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        playerUI.doPause();
        playerUI.buttons.skip.setImageDrawable(playerUI.drawables.getSkipPressed());
    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        playerUI.buttons.skip.setImageDrawable(playerUI.drawables.getSkip());
        playerUI.doPlay();
    }
}
