/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete.PauseAnimationListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete.PlayAnimationListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete.PrevAnimationListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete.ShuffleAnimationListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.listeners.concrete.SkipAnimationListener;

public class PlayerButtonsAnimations {

    public Animation playAnimation;
    public Animation skipAnimation;
    public Animation shuffleAnimation;
    public Animation prevAnimation;
    public Animation pauseAnimation;

    public PlayerButtonsAnimations(Activity activity)
    {
        loadAnimations(activity);
    }

    private void loadAnimations(Activity baseActivity)
    {
        playAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.play_animation
        );
        skipAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.skip_animation
        );
        shuffleAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.shuffle_animation
        );
        prevAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.prev_animation
        );
        pauseAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.pause_animation
        );
    }

    public void setPlayerUIListeners(AbstractPlayerUI playerUI, Context context)
    {
        playAnimation.setAnimationListener(new PlayAnimationListener(playerUI, context));
        pauseAnimation.setAnimationListener(new PauseAnimationListener(playerUI, context));
        skipAnimation.setAnimationListener(new SkipAnimationListener(playerUI, context));
        prevAnimation.setAnimationListener(new PrevAnimationListener(playerUI, context));
        shuffleAnimation.setAnimationListener(new ShuffleAnimationListener(playerUI, context));
    }
}
