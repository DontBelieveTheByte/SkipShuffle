package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.Jerk;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.SpinDown;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.SpinLeft;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.SpinRight;

public class PlayerButtonsAnimations {

    public Animation jerkRightAnimation;
    public Animation spinRightAnimation;
    public Animation spinDownAnimation;
    public Animation spinLeftAnimation;
    public Animation blinkAnimation;


    public PlayerButtonsAnimations(Activity activity)
    {
        loadAnimations(activity);
    }

    private void loadAnimations(Activity baseActivity)
    {
        jerkRightAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.jerk_right
        );
        spinRightAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.spin_right
        );
        spinDownAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.spin_down
        );
        spinLeftAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.spin_left
        );
        blinkAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.blink
        );
    }

    public void setPlayerUIListeners(AbstractPlayerUI playerUI)
    {
        jerkRightAnimation.setAnimationListener(new Jerk(playerUI));
        spinRightAnimation.setAnimationListener(new SpinRight(playerUI));
        spinLeftAnimation.setAnimationListener(new SpinLeft(playerUI));
        spinDownAnimation.setAnimationListener(new SpinDown(playerUI));
    }
}
