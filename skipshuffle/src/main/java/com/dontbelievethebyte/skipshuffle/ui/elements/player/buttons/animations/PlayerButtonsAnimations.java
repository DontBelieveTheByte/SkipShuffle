package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.FlipDown;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.FlipLeft;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.FlipRight;

public class PlayerButtonsAnimations {

    public Animation ltr;
    public Animation flipRightAnimation;
    public Animation flipDownAnimation;
    public Animation flipLeftAnimation;
    public Animation blinkAnimation;


    public PlayerButtonsAnimations(Activity activity)
    {
        loadAnimations(activity);
    }

    private void loadAnimations(Activity baseActivity)
    {
        ltr = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_ltr
        );
        flipRightAnimation  = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_right
        );
        flipDownAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_down
        );
        flipLeftAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_left
        );
        blinkAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_blink
        );
    }

    public void setPlayerUIListeners(AbstractPlayerUI playerUI)
    {
        flipRightAnimation.setAnimationListener(new FlipRight(playerUI));
        flipLeftAnimation.setAnimationListener(new FlipLeft(playerUI));
        flipDownAnimation.setAnimationListener(new FlipDown(playerUI));
    }
}
