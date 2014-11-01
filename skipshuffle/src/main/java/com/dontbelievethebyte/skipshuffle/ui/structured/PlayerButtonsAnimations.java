package com.dontbelievethebyte.skipshuffle.ui.structured;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.animation.listener.FlipRight;

public class PlayerButtonsAnimations {

    public Animation ltr;
    public Animation flipRightAnimation;
    public Animation flipDownAnimation;
    public Animation flipLeftAnimation;
    public Animation blinkAnimation;

    public PlayerButtonsAnimations(Activity activity)
    {
        ltr = AnimationUtils.loadAnimation(
                activity.getApplicationContext(),
                R.anim.common_ltr
        );
        flipRightAnimation  = AnimationUtils.loadAnimation(
                activity.getApplicationContext(),
                R.anim.common_flip_right
        );
        flipDownAnimation = AnimationUtils.loadAnimation(
                activity.getApplicationContext(),
                R.anim.common_flip_down
        );
        flipLeftAnimation = AnimationUtils.loadAnimation(
                activity.getApplicationContext(),
                R.anim.common_flip_left
        );
        blinkAnimation = AnimationUtils.loadAnimation(
                activity.getApplicationContext(),
                R.anim.common_blink
        );
    }
}
